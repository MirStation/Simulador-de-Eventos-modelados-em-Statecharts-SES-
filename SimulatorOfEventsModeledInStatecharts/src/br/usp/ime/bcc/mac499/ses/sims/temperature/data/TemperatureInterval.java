package br.usp.ime.bcc.mac499.ses.sims.temperature.data;

public class TemperatureInterval extends TemperatureData {

	private TemperatureValue firstTemperature;
	private TemperatureValue lastTemperature;
	
	public TemperatureInterval(TemperatureValue firstTemperature, TemperatureValue lastTemperature) {
		this.setFirstTemperature(firstTemperature);
		this.setLastTemperature(lastTemperature);
	}

	public TemperatureValue getFirstTemperature() {
		return firstTemperature;
	}

	public void setFirstTemperature(TemperatureValue firstTemperature) {
		this.firstTemperature = firstTemperature;
	}

	public TemperatureValue getLastTemperature() {
		return lastTemperature;
	}

	public void setLastTemperature(TemperatureValue lastTemperature) {
		this.lastTemperature = lastTemperature;
	}
	
	public boolean contains(TemperatureValue t) {
		return firstTemperature.isLesserOrEqualThan(t) && lastTemperature.isGreaterOrEqualThan(t);
	}
	
	public TemperatureValue pickRandomTemperature() {
		double range, value;
		range = Math.abs(lastTemperature.getValue() - firstTemperature.getValue());
		value = (Math.random() * range) + firstTemperature.getValue();
		return (new TemperatureValue(value));
	}
	
	public double getIntervalSize() {
		return Math.abs(lastTemperature.getValue() - firstTemperature.getValue());
	}
	
	@Override
	public boolean isIntervalOfTemperatures() {
		return true;
	}

	@Override
	public String printData() {
		return "[" + firstTemperature.printData() + "," + lastTemperature.printData() + "]";
	}
	
}
