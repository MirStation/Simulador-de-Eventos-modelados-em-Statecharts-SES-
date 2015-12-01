package br.usp.ime.bcc.mac499.ses.sims.temperature.util;

import java.util.StringTokenizer;

import br.usp.ime.bcc.mac499.ses.exceptions.TemperatureValueBuilderException;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureData;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureInterval;
import br.usp.ime.bcc.mac499.ses.sims.temperature.data.TemperatureValue;

public class TemperatureValueBuilder {
	
	private static TemperatureValueBuilder instance;
	
	private TemperatureValueBuilder() {}
	
	public static TemperatureValueBuilder getInstance() {
		if (instance == null) {
			instance = new TemperatureValueBuilder();
		}
		return instance;
	}
	
	public TemperatureData buildFromString(String string) {
		TemperatureData temperatureValue;
		TemperatureValue t1, t2;
		float value;
		StringTokenizer tok;
		try {
			value = Float.parseFloat(string);
			temperatureValue = new TemperatureValue(value);
		} catch (NumberFormatException e) {
			if(string.matches("\\[\\s*(-)?\\d+(\\.\\d+)?\\s*,\\s*(-)?\\d+(\\.\\d+)?\\s*\\]")){
				tok = new StringTokenizer(string, "[,]");
				t1 = new TemperatureValue(Float.parseFloat(tok.nextToken()));
				t2 = new TemperatureValue(Float.parseFloat(tok.nextToken()));
			} else {
				throw new TemperatureValueBuilderException("Bad string format for a temperature interval!");
			}
			temperatureValue = new TemperatureInterval(t1, t2);
		}
		return temperatureValue;
	}
	
}
