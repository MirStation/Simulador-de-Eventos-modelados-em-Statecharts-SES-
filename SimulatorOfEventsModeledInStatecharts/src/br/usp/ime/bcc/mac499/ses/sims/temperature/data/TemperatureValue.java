package br.usp.ime.bcc.mac499.ses.sims.temperature.data;

import java.math.BigDecimal;

public class TemperatureValue extends TemperatureData {
	
	private double value;
	
	public TemperatureValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double newValue) {
		value = newValue;
	}
	
	public boolean isEqualTo(TemperatureValue t) {
		return this.getValue() == t.getValue();
	}
	
	public boolean isGreaterThan(TemperatureValue t) {
		return this.getValue() > t.getValue();
	}
	
	public boolean isLesserThan(TemperatureValue t) {
		return this.getValue() < t.getValue();
	}
	
	public boolean isGreaterOrEqualThan(TemperatureValue t) {
		return this.getValue() >= t.getValue();
	}
	
	public boolean isLesserOrEqualThan(TemperatureValue t) {
		return this.getValue() <= t.getValue();
	}
	
	@Override
	public boolean isIntervalOfTemperatures() {
		return false;
	}

	@Override
	public String printData() {
		return (new BigDecimal(value)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}
	
}
