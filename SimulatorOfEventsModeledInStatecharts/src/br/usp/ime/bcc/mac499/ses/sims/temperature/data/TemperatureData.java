package br.usp.ime.bcc.mac499.ses.sims.temperature.data;

import br.usp.ime.bcc.mac499.ses.fsm.data.FiniteStateMachineData;

public abstract class TemperatureData implements FiniteStateMachineData {
	public abstract boolean isIntervalOfTemperatures();
}
