package com.hangout.api;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Events implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("results")
	private List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	
	
}
