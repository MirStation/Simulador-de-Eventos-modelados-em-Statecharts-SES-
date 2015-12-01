package br.usp.ime.bcc.mac499.ses.fsm;

public class FiniteStateMachineTransition {
	
	private State origin;
	private Event trigger;
	private State destin;
	
	public FiniteStateMachineTransition(State origin, State destin, Event trigger) {
		this.origin = origin;
		this.trigger = trigger;
		this.destin = destin;
	}

	public State getOrigin() {
		return origin;
	}

	public Event getTrigger() {
		return trigger;
	}

	public State getDestin() {
		return destin;
	}
	
}
