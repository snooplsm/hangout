package com.hangout.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String description;
	@JsonProperty("event_url")
	private String eventUrl;
	private EventGroup group;
	private String id;
	private String name;
	@JsonProperty("rsvp_limit")
	private int rsvpLimit;
	private String status;
	private long time;
	@JsonProperty("utc_offset")
	private long utcOffset;
	private Venue venue;
	
	private String visibility;
	@JsonProperty("yes_rsvp_count")
	private int yesRSVPCount;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getDescription() {
		return description;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public EventGroup getGroup() {
		return group;
	}
	public String getName() {
		return name;
	}
	public int getRsvpLimit() {
		return rsvpLimit;
	}
	public String getStatus() {
		return status;
	}
	public long getTime() {
		return time;
	}
	public long getUtcOffset() {
		return utcOffset;
	}
	public Venue getVenue() {
		return venue;
	}
	public String getVisibility() {
		return visibility;
	}
	public int getYesRSVPCount() {
		return yesRSVPCount;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public void setGroup(EventGroup group) {
		this.group = group;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRsvpLimit(int rsvpLimit) {
		this.rsvpLimit = rsvpLimit;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public void setUtcOffset(long utcOffset) {
		this.utcOffset = utcOffset;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public void setYesRSVPCount(int yesRSVPCount) {
		this.yesRSVPCount = yesRSVPCount;
	}
	
	
}
