package br.usp.ime.bcc.mac499.ses.fsm;

import java.util.ArrayList;

import br.usp.ime.bcc.mac499.ses.fsm.data.FiniteStateMachineData;

public class State extends FiniteStateMachineElement {
	
	private boolean initialState;
	private boolean finalState;
	private ArrayList<FiniteStateMachineTransition> exitTransitions;
	private ArrayList<FiniteStateMachineTransition> enterTransitions;
	
	public State(FiniteStateMachineData value) {
		this.data = value;
		this.setInitialState(false);
		this.setFinalState(false);
	}
	
	protected void addTransitionToExitTransitionsArrayList(FiniteStateMachineTransition transition) {
		if (exitTransitions == null) {
			exitTransitions = new ArrayList<FiniteStateMachineTransition>();
		}
		exitTransitions.add(transition);
	}
	
	protected void addTransitionToEnterTransitionsArrayList(FiniteStateMachineTransition transition) {
		if (enterTransitions == null) {
			enterTransitions = new ArrayList<FiniteStateMachineTransition>();
		}
		enterTransitions.add(transition);
	}
	
	public ArrayList<FiniteStateMachineTransition> getExitTransitions() {
		return ((exitTransitions == null) ? null : new ArrayList<FiniteStateMachineTransition>(exitTransitions));
	}
	
	public ArrayList<FiniteStateMachineTransition> getEnterTransitions() {
		return  ((enterTransitions == null) ? null : new ArrayList<FiniteStateMachineTransition>(enterTransitions));
	}
	
	public boolean isInitialState() {
		return initialState;
	}

	public void setInitialState(boolean isInitialState) {
		this.initialState = isInitialState;
	}

	public boolean isFinalState() {
		return finalState;
	}

	public void setFinalState(boolean isFinalState) {
		this.finalState = isFinalState;
	}

}
