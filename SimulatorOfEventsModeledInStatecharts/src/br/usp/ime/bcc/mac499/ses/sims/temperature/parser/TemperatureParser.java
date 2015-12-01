package br.usp.ime.bcc.mac499.ses.sims.temperature.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.usp.ime.bcc.mac499.ses.fsm.Event;
import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachineTransition;
import br.usp.ime.bcc.mac499.ses.fsm.State;
import br.usp.ime.bcc.mac499.ses.fsm.data.FiniteStateMachineDefaultData;
import br.usp.ime.bcc.mac499.ses.parser.FiniteStateMachineParser;
import br.usp.ime.bcc.mac499.ses.sims.temperature.util.TemperatureValueBuilder;

public class TemperatureParser extends FiniteStateMachineParser {
	
	private final String TAG_STATES = "STATES";
	private final String TAG_STATE = "STATE";
	private final String TAG_STATE_ATTRIBUTE_NAME = "NAME";
	private final String TAG_STATE_ATTRIBUTE_TYPE = "TYPE";
	
	private final String TAG_INPUTS = "INPUTS";
	private final String TAG_INPUT = "INPUT";
	private final String TAG_INPUT_ATTRIBUTE_EVENT = "EVENT";
	
	private final String TAG_TRANSITIONS = "TRANSITIONS";
	private final String TAG_TRANSITION = "TRANSITION";
	private final String TAG_TRANSITION_ATTRIBUTE_SOURCE = "SOURCE";
	private final String TAG_TRANSITION_ATTRIBUTE_DESTINATION = "DESTINATION";
	
	private TemperatureValueBuilder temperatureValueBuilder;
	private String temperatureAbstraction;
	
	public TemperatureParser(File xmlFile, String temperatureAbstraction) {
		initParser(xmlFile);
		this.temperatureAbstraction = temperatureAbstraction;
		temperatureValueBuilder = TemperatureValueBuilder.getInstance();
		statesMap = findAllStates();
		eventsMap = findAllEvents();
	}
	
	@Override
	public ArrayList<FiniteStateMachineTransition> getAllTransitions() {

		FiniteStateMachineTransition auxTransition;
		
		ArrayList<FiniteStateMachineTransition> transitionsList = new ArrayList<FiniteStateMachineTransition>();
		
		Node node = doc.getElementsByTagName(TAG_TRANSITIONS).item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			NodeList list = element.getElementsByTagName(TAG_TRANSITION);
			for (int i = 0; i < list.getLength(); i++) {
				Node nodeAux = list.item(i);
				if (nodeAux.getNodeType() == Node.ELEMENT_NODE) {
					Element elementAux = (Element) nodeAux;
					String originStateName = elementAux.getAttribute(TAG_TRANSITION_ATTRIBUTE_SOURCE);
					String destinStateName = elementAux.getAttribute(TAG_TRANSITION_ATTRIBUTE_DESTINATION);
					String inputEventName = elementAux.getElementsByTagName(TAG_INPUT).item(0).getTextContent();
					auxTransition = new FiniteStateMachineTransition((State)statesMap.get(originStateName), (State)statesMap.get(destinStateName), (Event)eventsMap.get(inputEventName));
					transitionsList.add(auxTransition);
				}
			}
		}
		
		return transitionsList;
	}

	@Override
	protected Map<String, State> findAllStates() {
		State auxState;
		
		Map<String, State> statesMap = new HashMap<>();
		
		Node node = doc.getElementsByTagName(TAG_STATES).item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			NodeList list = element.getElementsByTagName(TAG_STATE);
			for (int i = 0; i < list.getLength(); i++) {
				Node nodeAux = list.item(i);
				if (nodeAux.getNodeType() == Node.ELEMENT_NODE) {
					Element elementAux = (Element) nodeAux;
					if (!statesMap.containsKey(elementAux.getAttribute(TAG_STATE_ATTRIBUTE_NAME))) {
						if (temperatureAbstraction.equals("state")) {
							auxState = new State(temperatureValueBuilder.buildFromString(elementAux.getAttribute(TAG_STATE_ATTRIBUTE_NAME)));
						} else {
							auxState = new State(new FiniteStateMachineDefaultData(elementAux.getAttribute(TAG_STATE_ATTRIBUTE_NAME)));
						}
						statesMap.put(elementAux.getAttribute(TAG_STATE_ATTRIBUTE_NAME), auxState);
					} else {
						auxState = (State) statesMap.get(elementAux.getAttribute(TAG_STATE_ATTRIBUTE_NAME));
					}
					if (elementAux.getAttribute(TAG_STATE_ATTRIBUTE_TYPE).equals("inicial")) {
						auxState.setInitialState(true);
					} else if (elementAux.getAttribute(TAG_STATE_ATTRIBUTE_TYPE).equals("final")) {
						auxState.setFinalState(true);
					}
				}
			}
		}
		
		return statesMap;
	}

	@Override
	protected Map<String, Event> findAllEvents() {
		Event auxEvent;
		
		Map<String, Event> eventsMap = new HashMap<>();
		
		Node node = doc.getElementsByTagName(TAG_INPUTS).item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			NodeList list = element.getElementsByTagName(TAG_INPUT);
			for (int i = 0; i < list.getLength(); i++) {
				Node auxNode = list.item(i);
				if (auxNode.getNodeType() == Node.ELEMENT_NODE) {
					Element auxElement = (Element) auxNode;
					if (temperatureAbstraction.equals("event")) {
						auxEvent = new Event(temperatureValueBuilder.buildFromString(auxElement.getAttribute(TAG_INPUT_ATTRIBUTE_EVENT)));
					} else {
						auxEvent = new Event(new FiniteStateMachineDefaultData(auxElement.getAttribute(TAG_INPUT_ATTRIBUTE_EVENT)));
					}
					eventsMap.put(auxElement.getAttribute(TAG_INPUT_ATTRIBUTE_EVENT), auxEvent);
				}
			}
		}
		
		return eventsMap;

	}
	
}
