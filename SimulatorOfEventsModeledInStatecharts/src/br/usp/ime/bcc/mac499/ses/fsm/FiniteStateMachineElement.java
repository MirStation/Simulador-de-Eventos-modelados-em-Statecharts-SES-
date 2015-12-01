package br.usp.ime.bcc.mac499.ses.fsm;

import br.usp.ime.bcc.mac499.ses.fsm.data.FiniteStateMachineData;

public abstract class FiniteStateMachineElement {

	protected FiniteStateMachineData data;
	
	public FiniteStateMachineData getData() {
		return data;
	}
}