package com.hangout.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class PhotoAlbum implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("group_id")
	private long groupId;
	@JsonProperty("event_id")
	private long eventId;
	@JsonProperty("photo_album_id")
	private long id;
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
		PhotoAlbum other = (PhotoAlbum) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
