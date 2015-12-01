package br.usp.ime.bcc.mac499.ses.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.w3c.dom.Document;

import br.usp.ime.bcc.mac499.ses.fsm.Event;
import br.usp.ime.bcc.mac499.ses.fsm.FiniteStateMachineTransition;
import br.usp.ime.bcc.mac499.ses.fsm.State;
import br.usp.ime.bcc.mac499.ses.util.XMLReader;

public abstract class FiniteStateMachineParser {

	protected Document doc;
	protected Map<String, State> statesMap;
	protected Map<String, Event> eventsMap;
	
	protected void initParser(File xmlFile) {
		XMLReader xmlReader = new XMLReader(xmlFile);
		doc = xmlReader.getDocument();
	}
	
	public ArrayList<State> getAllStates() {
		ArrayList<State> arrayListOfStates = new ArrayList<State>(statesMap.values());
		return arrayListOfStates;
	}
	
	public ArrayList<Event> getAllEvents() {
		ArrayList<Event> arrayListOfEvents = new ArrayList<Event>(eventsMap.values());
		return arrayListOfEvents;
	}
	
	public abstract ArrayList<FiniteStateMachineTransition> getAllTransitions();
	
	protected abstract Map<String, State> findAllStates();
	
	protected abstract Map<String, Event> findAllEvents();
	
}
