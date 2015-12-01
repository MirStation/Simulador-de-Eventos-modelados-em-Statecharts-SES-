package br.usp.ime.bcc.mac499.ses.sims.temperature;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import br.usp.ime.bcc.mac499.ses.exceptions.PickTemperatureException;
import br.usp.ime.bcc.mac499.ses.exceptions.WalkStrategyException;
import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachine;
import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachineTransition;
import br.usp.ime.bcc.mac499.ses.fsm.data.FiniteStateMachineData;
import br.usp.ime.bcc.mac499.ses.parser.FiniteStateMachineParser;
import br.usp.ime.bcc.mac499.ses.sim.Simulator;
import br.usp.ime.bcc.mac499.ses.sim.walkstrategies.WalkByProbability;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureData;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureInterval;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureValue;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.pick.ConstantAscending;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.pick.PickTemperature;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.pick.RandomAscending;
import br.usp.ime.bcc.mac499.ses.sims.temperature.parser.TemperatureParser;
import br.usp.ime.bcc.mac499.ses.sims.temperature.walkstrategies.WalkByHighTemperature;
import br.usp.ime.bcc.mac499.ses.util.XMLReader;

public class TemperatureSim extends Simulator {
	
	private double maxTemperature;
	private double minTemperature;
	
	private double probabilityOfError;
	
	private String pickTemperatureStrategy;
	
	Stack<FiniteStateMachineTransition> processedTransitions;
	Stack<String> processedValues;
	
	static PickTemperature pickTemperature; // temporary
	
	public TemperatureSim(int numOfSamples, int sizeOfSample, ArrayList<String> stopFinalStates) {
		super.numOfSamples = numOfSamples;
		super.sizeOfSample = sizeOfSample;
		super.stopFinalStatesNames = stopFinalStates;
		super.stopAtFinalStates = (stopFinalStates == null) ? false : true;
		
		XMLReader xmlReader = new XMLReader("src/br/usp/ime/bcc/mac499/ses/sims/temperature/config.xml");
		Document doc = xmlReader.getDocument();
		initTargetInfo(doc);
		initTemperatureInfo(doc);
		initErrorInfo(doc);
		initStrategyInfo(doc);
		initPickTemperatureWithinRangeInfo(doc);
		
		super.targetFile = new File("targets/" + targetName);
		
		initFiniteStateMachine();
		initWalkStrategy();
		
		processedTransitions = new Stack<FiniteStateMachineTransition>();
		processedValues = new Stack<String>();
		processedValues.push((new Double(super.initialValue)).toString());
	}
	
	private void initPickTemperatureWithinRangeInfo(Document doc) {
		Node node = doc.getElementsByTagName("temperature_within_range").item(0);
		if (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String attributeValue = element.getAttribute("pick");
				switch (attributeValue) {
					case "random_ascending":
					case "constant_ascending":
						pickTemperatureStrategy = attributeValue;
						break;
					default:
						throw new PickTemperatureException("Unknown pick temperature strategy.");
				}
			}
		}
	}
	
	protected void initTemperatureInfo(Document doc) {
		Node node = doc.getElementsByTagName("temperature").item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			maxTemperature = Double.parseDouble(element.getAttribute("max"));
			minTemperature = Double.parseDouble(element.getAttribute("min"));
			super.eventAbstraction = element.getAttribute("abs");
			super.initialValue = Double.parseDouble(element.getAttribute("ini")); 
		}
	}
	
	protected void initErrorInfo(Document doc) {
		Node node = doc.getElementsByTagName("error").item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			probabilityOfError = Float.parseFloat(element.getAttribute("prob"));
		}
	}
	
	private TemperatureData generateWrongTemperature() {
		double lowestTemperature = -143.00, highestTemperature = 56.70; 
		double value, range, min, max;
		double random = Math.random();
		if (random > 0.5) {
			max = highestTemperature;
			min = maxTemperature;
		} else {
			max = minTemperature;
			min = lowestTemperature;
		}
		range = Math.abs(max - min);
		value = max - (Math.random() * range);
		return new TemperatureValue(value);
	}
	
	private void initFiniteStateMachine() {
		FiniteStateMachineParser fsmParser = new TemperatureParser(targetFile, super.eventAbstraction);
		finiteStateMachine = new FiniteStateMachine(fsmParser.getAllStates(), fsmParser.getAllEvents(), fsmParser.getAllTransitions());
	}
	
	private void initWalkStrategy() {
		switch (strategyName) {
			case "WalkByHighTemperature":
				walkStrategy = new WalkByHighTemperature(probabilityOfError, super.eventAbstraction);
				break;
			case "WalkByProbability":
				walkStrategy = new WalkByProbability(probabilityOfError);
				break;
			default:
				throw new WalkStrategyException("Unknown " + strategyName + " walk strategy.");
		}
	}

	@Override
	protected FiniteStateMachineData nextFSMData() {
		// PickTemperature pickTemperature;
		TemperatureData temperature;
		FiniteStateMachineTransition pickedTransition = walkStrategy.path(finiteStateMachine.getCurrentState());
		if (pickedTransition == null) {
			System.out.print(" w:");
			return generateWrongTemperature();
		} else {
			if (super.eventAbstraction.equals("state")) {
				temperature = (TemperatureData) pickedTransition.getDestin().getData();
			} else {
				temperature = (TemperatureData) pickedTransition.getTrigger().getData();
			}
			if (temperature.isIntervalOfTemperatures()) {
				if (pickTemperatureStrategy != null) {
					if (!pickedTransition.equals(processedTransitions.peek())) {
						switch (pickTemperatureStrategy) {
							case "random_ascending":
								pickTemperature = new RandomAscending((TemperatureInterval)temperature);
								break;
							default:
								pickTemperature = new ConstantAscending((TemperatureInterval)temperature,1);
								break;
						}
					}
					temperature = pickTemperature.pick();
				} else {
					temperature = ((TemperatureInterval)temperature).pickRandomTemperature();
				}
			}
			finiteStateMachine.applyTransition(pickedTransition);
			processedTransitions.push(pickedTransition);
			processedValues.push(temperature.printData());
			return temperature;
		}
	}
	
}
