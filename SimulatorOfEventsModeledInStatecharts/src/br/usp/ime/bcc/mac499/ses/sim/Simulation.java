package br.usp.ime.bcc.mac499.ses.sim;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.usp.ime.bcc.mac499.ses.exceptions.SimulationException;
import br.usp.ime.bcc.mac499.ses.sim.handlers.TargetsHandler;
import br.usp.ime.bcc.mac499.ses.sims.temperature.TemperatureSim;
import br.usp.ime.bcc.mac499.ses.util.XMLReader;

public class Simulation {
	
	private String simulatorName;
	
	private int numOfSamples;
	private int sizeOfSample;
	
	private boolean stopAtFinalStates;
	private ArrayList<String> stopFinalStatesNames;
	
	public Simulation() {
		XMLReader xmlReader = new XMLReader("src/br/usp/ime/bcc/mac499/ses/sim/config.xml");
		Document doc = xmlReader.getDocument();
		initSimulatorInfo(doc);
		initSampleInfo(doc);
		initStopAtFinalStatesInfo(doc);
	}
	
	private void initSimulatorInfo(Document doc) {
		Node node = doc.getElementsByTagName("simulator").item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			simulatorName = element.getAttribute("name");
		}
	}
	
	private void initSampleInfo(Document doc) {
		Node node = doc.getElementsByTagName("sample").item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			numOfSamples = Integer.parseInt(element.getAttribute("num"));
			if (element.hasAttribute("size")) {
				sizeOfSample = Integer.parseInt(element.getAttribute("size"));
			}
		}
	}
	
	private void initStopAtFinalStatesInfo(Document doc) {
		Node node = doc.getElementsByTagName("stop").item(0);
		stopAtFinalStates = false;
		if (node != null) {
			Element element = (Element) node;
			if (element.getNodeType() == Node.ELEMENT_NODE) {
				NodeList nodeList = element.getElementsByTagName("final");
				if (nodeList.getLength() > 0) {
					stopAtFinalStates = true;
					stopFinalStatesNames = new ArrayList<String>();
					for (int i = 0; i < nodeList.getLength(); i++) {
						element = (Element) nodeList.item(i);
						if (element.getNodeType() == Node.ELEMENT_NODE) {
							stopFinalStatesNames.add(element.getAttribute("name"));
						}
					}
				} 
			}
		}
	}
	
	public String getSimulationName() {
		return simulatorName;
	}
	
	public int getNumOfSamples() {
		return numOfSamples;
	}
	
	public int getSizeOfSample() {
		return sizeOfSample;
	}
	
	public ArrayList<String> getStopFinalStatesNames() {
		return stopAtFinalStates ? stopFinalStatesNames : null;
	}
		
	public static void main(String[] args) {
		System.out.println(":]");
		System.out.println("Welcome to Scalable Simulator of Events!");
		
		Simulation simulation = new Simulation();
		
		System.out.println("Simulator: " + simulation.getSimulationName() + "\n");
		
		Simulator simulator;
		switch (simulation.getSimulationName()) {
			case "TemperatureSim":
				simulator = new TemperatureSim(simulation.getNumOfSamples(), simulation.getSizeOfSample(), simulation.getStopFinalStatesNames());
				break;
			default:
				throw new SimulationException("Unknown " + simulation.getSimulationName() + "simulator!");
		}
		
		TargetsHandler targetsHandler = new TargetsHandler();
		targetsHandler.processTargets(simulator);
		
		simulator.runSimulation();
		
		System.out.println("\nEnd of simulation! Bye!");
		System.out.println(":]");
	}

}
