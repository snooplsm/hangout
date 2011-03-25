package com.hangout.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class CheckinL implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String comment;
	
	@JsonProperty("event_id")
	private long eventId;
	
	private double lat,lon;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	
	
}
