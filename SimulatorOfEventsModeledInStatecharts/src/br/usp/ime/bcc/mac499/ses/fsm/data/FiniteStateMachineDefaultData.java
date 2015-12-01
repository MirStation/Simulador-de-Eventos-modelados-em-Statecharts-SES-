package br.usp.ime.bcc.mac499.ses.fsm.data;

public class FiniteStateMachineDefaultData implements FiniteStateMachineData {

	private String value;
	
	public FiniteStateMachineDefaultData(String value) {
		this.value = value;
	}
	
	@Override
	public String printData() {
		return value;
	}

}
