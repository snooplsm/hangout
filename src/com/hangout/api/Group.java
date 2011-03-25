package com.hangout.api;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Group extends EventGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private String city;
	
	private String country;

	private String created,updated;

	private Integer daysleft;
	private String description;

	@JsonProperty("group_photo_count")
	private int groupPhotoCount;

	@JsonProperty("group_urlname")
	private String groupUrlName;

	private long id;

	@JsonProperty("join_mode")
	private Integer joinMode;
	
	private double lat,lon;
	
	private String link;
	
	private int members;

	private String name;

	@JsonProperty("organizer_id")
	private long organizerId;

	@JsonProperty("organizer_name")
	private String organizerName;

	private String organizerProfileURL;
	
	private double rating;
	
	@JsonProperty("short_link")
	private String shortLink;
	
	private String state;
	
	private List<Topic> topics;
	
	private String visibility;
	
	private String who;
	
	private String zip;
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}
	public String getCreated() {
		return created;
	}
	
	public Integer getDaysleft() {
		return daysleft;
	}
	
	public String getDescription() {
		return description;
	}

	public int getGroupPhotoCount() {
		return groupPhotoCount;
	}
	
	public String getGroupUrlName() {
		return groupUrlName;
	}
	
	public long getId() {
		return id;
	}

	public Integer getJoinMode() {
		return joinMode;
	}

	public double getLat() {
		return lat;
	}

	public String getLink() {
		return link;
	}

	public double getLon() {
		return lon;
	}

	public int getMembers() {
		return members;
	}

	public String getName() {
		return name;
	}

	public long getOrganizerId() {
		return organizerId;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public String getOrganizerProfileURL() {
		return organizerProfileURL;
	}

	public double getRating() {
		return rating;
	}

	public String getShortLink() {
		return shortLink;
	}

	public String getState() {
		return state;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public String getUpdated() {
		return updated;
	}

	public String getVisibility() {
		return visibility;
	}

	public String getWho() {
		return who;
	}

	public String getZip() {
		return zip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setDaysleft(Integer daysleft) {
		this.daysleft = daysleft;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGroupPhotoCount(int groupPhotoCount) {
		this.groupPhotoCount = groupPhotoCount;
	}

	public void setGroupUrlName(String groupUrlName) {
		this.groupUrlName = groupUrlName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setJoinMode(int joinMode) {
		this.joinMode = joinMode;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setMembers(int members) {
		this.members = members;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrganizerId(long organizerId) {
		this.organizerId = organizerId;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public void setOrganizerProfileURL(String organizerProfileURL) {
		this.organizerProfileURL = organizerProfileURL;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
