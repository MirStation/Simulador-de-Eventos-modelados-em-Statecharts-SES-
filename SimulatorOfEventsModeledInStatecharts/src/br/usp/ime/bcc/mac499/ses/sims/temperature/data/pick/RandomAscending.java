package br.usp.ime.bcc.mac499.ses.sims.temperature.data.pick;

import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureInterval;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureValue;

public class RandomAscending implements PickTemperature {
	
	private TemperatureInterval interval;
	private TemperatureValue lastTemperaturePicked;
	
	public RandomAscending(TemperatureInterval interval) {
		this.interval = interval;
	}
	
	@Override
	public TemperatureValue pick() {
		double range, value, min, max;
		TemperatureValue aux;
		if (lastTemperaturePicked == null) {
			value = interval.pickRandomTemperature().getValue();
		} else {
			max = interval.getLastTemperature().getValue();
			aux = new TemperatureValue(lastTemperaturePicked.getValue() + 0.01);
			if (!interval.contains(aux)) {
				min = interval.getLastTemperature().getValue();
			} else {
				min = aux.getValue();
			}
			range = Math.abs(max - min);
			value = (Math.random() * range) + min;
		}
		lastTemperaturePicked = new TemperatureValue(value);
		return lastTemperaturePicked;
	}
	
}
