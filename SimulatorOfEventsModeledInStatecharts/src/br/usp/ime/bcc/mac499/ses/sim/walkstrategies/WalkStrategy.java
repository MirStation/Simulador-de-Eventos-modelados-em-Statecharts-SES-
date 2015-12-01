package br.usp.ime.bcc.mac499.ses.sim.walkstrategies;

import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachineTransition;
import br.usp.ime.bcc.mac499.ses.fsm.State;

public interface WalkStrategy {
	FiniteStateMachineTransition path(State currentState);
}
