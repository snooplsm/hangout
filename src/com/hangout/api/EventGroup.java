package com.hangout.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	@JsonProperty("join_mode")
	private String joinMode;
	@JsonProperty("group_lat")
	private double lat;
	@JsonProperty("group_lon")
	private double lon;
	private String name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJoinMode() {
		return joinMode;
	}
	public void setJoinMode(String joinMode) {
		this.joinMode = joinMode;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventGroup other = (EventGroup) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
