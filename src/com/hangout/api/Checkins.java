package com.hangout.api;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Checkins implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("results")
	private List<Checkin> checkins;

	public List<Checkin> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<Checkin> checkins) {
		this.checkins = checkins;
	}

}
