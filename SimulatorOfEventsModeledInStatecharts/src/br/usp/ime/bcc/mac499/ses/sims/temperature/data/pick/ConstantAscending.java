package br.usp.ime.bcc.mac499.ses.sims.temperature.data.pick;

import br.usp.ime.bcc.mac499.ses.exceptions.PickTemperatureException;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureInterval;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureValue;

public class ConstantAscending implements PickTemperature {
	
	private double distBetweenTwoTemperatures;
	private TemperatureInterval temperatureInterval;
	private TemperatureValue lastTemperaturePicked;
	
	public ConstantAscending(TemperatureInterval temperatureInterval, double distBetweenTwoTemperatures) {
		if (temperatureInterval.getIntervalSize() >= distBetweenTwoTemperatures) {
			this.distBetweenTwoTemperatures = distBetweenTwoTemperatures;
			this.temperatureInterval = temperatureInterval;
		} else {
			throw new PickTemperatureException("The distance between two temperatures must be lesser or equal to the distante of the temperature interval.");
		}
		
	}

	@Override
	public TemperatureValue pick() {
		double value;
		TemperatureValue aux;
		if (lastTemperaturePicked == null) {
			value = temperatureInterval.pickRandomTemperature().getValue();
		} else {
			aux = new TemperatureValue(lastTemperaturePicked.getValue() + distBetweenTwoTemperatures);
			if (temperatureInterval.contains(aux)) {
				value = aux.getValue();
			} else {
				value = temperatureInterval.getLastTemperature().getValue();
			} 
		}
		lastTemperaturePicked = new TemperatureValue(value);
		return lastTemperaturePicked;
	}

}
