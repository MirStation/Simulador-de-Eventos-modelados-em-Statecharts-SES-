package br.usp.ime.bcc.mac499.ses.sims.temperature.walkstrategies;

import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachineTransition;
import br.usp.ime.bcc.mac499.ses.fsm.State;
import br.usp.ime.bcc.mac499.ses.sim.walkstrategies.WalkStrategy;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureData;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureInterval;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureValue;

public class WalkByHighTemperature implements WalkStrategy {
	
	private double probabilityOfError;
	private String temperatureAbstraction;
	private FiniteStateMachineTransition lastTransition;
	
	public WalkByHighTemperature(double probabilityOfError, String temperatureAbstraction) {
		this.probabilityOfError = probabilityOfError;
		this.temperatureAbstraction = temperatureAbstraction;
	}
	
	private TemperatureData getTemperatureValueFromTransition(FiniteStateMachineTransition transition) {
		TemperatureData temperatureValue;
		if (temperatureAbstraction.equals("event")) {
			temperatureValue = (TemperatureData) transition.getTrigger().getData();
		} else {
			temperatureValue = (TemperatureData) transition.getDestin().getData();
		}
		return temperatureValue;
	}
	
	@Override
	public FiniteStateMachineTransition path(State currentState) {
		double randomValue = Math.random();
		TemperatureData highTemperatureValue;
		TemperatureData temperatureValue;
		FiniteStateMachineTransition maxTransition;
		if (currentState.getExitTransitions() != null) {
			if (currentState.getExitTransitions().size() > 0) {
				if (randomValue >= 0 && randomValue < (1.0 - probabilityOfError)) {
					highTemperatureValue = getTemperatureValueFromTransition(currentState.getExitTransitions().get(0));
					maxTransition = currentState.getExitTransitions().get(0);
					for (int i = 1; i < currentState.getExitTransitions().size(); i++) {
						temperatureValue = getTemperatureValueFromTransition(currentState.getExitTransitions().get(i));
						if (highTemperatureValue.isIntervalOfTemperatures()) {
							TemperatureInterval tv1 = (TemperatureInterval) highTemperatureValue;
							if (temperatureValue.isIntervalOfTemperatures()) {
								TemperatureInterval tv2 = (TemperatureInterval) temperatureValue;
								if (tv1.getFirstTemperature().isLesserThan(tv2.getFirstTemperature())) {
									highTemperatureValue = temperatureValue;
									maxTransition = currentState.getExitTransitions().get(i);
								}
							} else {
								TemperatureValue tv2 = (TemperatureValue) temperatureValue;
								if (tv1.getFirstTemperature().isLesserThan(tv2)) {
									highTemperatureValue = temperatureValue;
									maxTransition = currentState.getExitTransitions().get(i);
								}
							}
						} else {
							TemperatureValue tv1 = (TemperatureValue) highTemperatureValue;
							if (temperatureValue.isIntervalOfTemperatures()) {
								TemperatureInterval tv2 = (TemperatureInterval) temperatureValue;
								if (tv1.isLesserThan(tv2.getFirstTemperature())) {
									highTemperatureValue = temperatureValue;
									maxTransition = currentState.getExitTransitions().get(i);
								}
							} else {
								TemperatureValue tv2 = (TemperatureValue) temperatureValue;
								if (tv1.isLesserThan(tv2)) {
									highTemperatureValue = temperatureValue;
									maxTransition = currentState.getExitTransitions().get(i);
								}
							}
						}
					}
					lastTransition = maxTransition;
				} else {
					return null;
				}
			}
		}
			
		return lastTransition;
		
	}

}
