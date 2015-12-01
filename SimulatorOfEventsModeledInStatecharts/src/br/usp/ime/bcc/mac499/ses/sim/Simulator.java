package br.usp.ime.bcc.mac499.ses.sim;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachine;
import br.usp.ime.bcc.mac499.ses.fsm.data.FiniteStateMachineData;
import br.usp.ime.bcc.mac499.ses.sim.walkstrategies.WalkStrategy;

public abstract class Simulator {
	
	protected String targetName;
	protected File targetFile;
	
	protected int numOfSamples;
	protected int sizeOfSample;
	
	protected String eventAbstraction;
	
	protected String strategyName;
	protected WalkStrategy walkStrategy;
	
	protected boolean stopAtFinalStates;
	protected ArrayList<String> stopFinalStatesNames;
	
	protected FiniteStateMachine finiteStateMachine;
	
	protected double initialValue;
	
	protected abstract FiniteStateMachineData nextFSMData();
	
	protected void initTargetInfo(Document doc) {
		Node node = doc.getElementsByTagName("target").item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			targetName = element.getAttribute("name");
		}
	}
	
	protected void initStrategyInfo(Document doc) {
		Node node = doc.getElementsByTagName("strategy").item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			strategyName = element.getAttribute("name");
		}
	}
	
	public void runSimulation() {
		int atualSize;
		FiniteStateMachineData currentFSMData;
		for (int samplesMade = 0; samplesMade < numOfSamples; samplesMade++) {
			atualSize = 1;
			System.out.println("Sample " + (samplesMade + 1) + ":");
			System.out.print(" " + initialValue);
			while (true) {
				currentFSMData = nextFSMData();
				System.out.print(" " + currentFSMData.printData());
				if (stopAtFinalStates) {
					if (finiteStateMachine.isCurrentStateAFinalState()) {
						if (stopFinalStatesNames.contains(finiteStateMachine.getCurrentState().getData().printData())) {
							break;
						}
					}
				} else if (atualSize == (sizeOfSample - 1)) {
					break;
				} else {
					atualSize++;
				}
			}
			System.out.println();
			finiteStateMachine.resetFiniteStateMachine();
		}
	}
	
	public String getTargetName() {
		return targetName;
	}
	
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public String getTargetFile() {
		return targetName;
	}
	
	public void setTargetFile(File targetFile) {
		this.targetFile = targetFile;
	}
}
