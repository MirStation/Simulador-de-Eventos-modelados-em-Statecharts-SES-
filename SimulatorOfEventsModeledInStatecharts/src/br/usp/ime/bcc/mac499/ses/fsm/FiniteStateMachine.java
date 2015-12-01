package br.usp.ime.bcc.mac499.ses.fsm;

import java.util.ArrayList;

public class FiniteStateMachine {
	
	private State initialState;
	private State currentState;
	private State pastState;
	private ArrayList<State> finalStates;
	private ArrayList<State> allStates;
	private int numberOfStates;
	
	private Event lastEvent;
	private ArrayList<Event> allEvents;
	private int numberOfEvents;
	
	public FiniteStateMachine(ArrayList<State> allStates, ArrayList<Event> allEvents, ArrayList<FiniteStateMachineTransition> allTransitions) {
		this.allStates = allStates;
		numberOfStates = allStates.size();
		setInitialAndFinalsStates(allStates);
		this.allEvents = allEvents;
		numberOfEvents = allEvents.size();
		connectTransitions(allTransitions);
		pastState = initialState;
		currentState = initialState;
	}
	
	private void connectTransitions(ArrayList<FiniteStateMachineTransition> allTransitions) {
		for (FiniteStateMachineTransition transition : allTransitions) {
			transition.getOrigin().addTransitionToExitTransitionsArrayList(transition);
			transition.getDestin().addTransitionToEnterTransitionsArrayList(transition);
		}
	}
	
	private void setInitialAndFinalsStates(ArrayList<State> allStates) {
		finalStates = new ArrayList<State>();
		for (State state: allStates) {
			if (state.isInitialState()) initialState = state;
			if (state.isFinalState()) finalStates.add(state);
		}
	}
	
	public State getInitialState() {
		return initialState;
	}
	
	public State getCurrentState() { 
		return currentState;
	}
	
	public State getPastState() {
		return pastState;
	}
	
	public Event getLastEvent() {
		return lastEvent;
	}
	
	public ArrayList<FiniteStateMachineTransition> getCurrentStatePossibleTransitions() {
		return currentState.getExitTransitions();
	}
	
	public void applyTransition(FiniteStateMachineTransition transition) {
		currentState = transition.getDestin();
		pastState = transition.getOrigin();
		lastEvent = transition.getTrigger();
	}
	
	public boolean isCurrentStateAFinalState() {
		return finalStates.contains(currentState);
	}
	
	public void resetFiniteStateMachine() { 
		currentState = initialState;
		pastState = initialState;
		lastEvent = null;
	}

	public ArrayList<State> getAllStates() {
		return allStates;
	}
	
	public int getNumberOfStates() {
		return numberOfStates;
	}
	
	public ArrayList<Event> getAllEvents() {
		return allEvents;
	}
	
	public int getNumberOfEvents() {
		return numberOfEvents;
	}
}
