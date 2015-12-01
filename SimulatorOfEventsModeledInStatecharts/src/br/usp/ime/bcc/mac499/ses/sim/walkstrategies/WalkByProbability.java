package br.usp.ime.bcc.mac499.ses.sim.walkstrategies;

import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachineTransition;
import br.usp.ime.bcc.mac499.ses.fsm.State;

public class WalkByProbability implements WalkStrategy {
	
	private double probabilityOfError;
	private FiniteStateMachineTransition lastTransition;
	
	public WalkByProbability(double probabilityOfError) {
		this.probabilityOfError = probabilityOfError;
	}
	
	private int getProbabilityInterval(double validTransitionsProbability, double prob, int sz) {
		double fDelimiter, lDelimiter;
		int i = 1;
		fDelimiter = 0;
		lDelimiter = validTransitionsProbability;
		while (!(fDelimiter <= prob && prob < lDelimiter)) {
			fDelimiter = lDelimiter;
			lDelimiter = (++i)*validTransitionsProbability;
		}
		return i-1;
	}
	
	@Override
	public FiniteStateMachineTransition path(State currentState) {
		int index, numExitTransitions;
		double validTransitionsProbability, randomValue;
		
		if (currentState.getExitTransitions() != null) {
			if (currentState.getExitTransitions().size() > 0) {
				numExitTransitions = currentState.getExitTransitions().size();
				validTransitionsProbability = (1.0 - probabilityOfError) / numExitTransitions;
				randomValue = Math.random();
				if (randomValue >= 0 && randomValue < (1.0 - probabilityOfError)) {
					index = getProbabilityInterval(validTransitionsProbability, randomValue, currentState.getExitTransitions().size());
					lastTransition = currentState.getExitTransitions().get(index);
				} else {
					return null;
				}
			}
		}
		
		return lastTransition;
	}

}
