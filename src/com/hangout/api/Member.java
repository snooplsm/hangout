package com.hangout.api;

import java.io.Serializable;

public class Member extends MemberL implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bio;
	private String city;
	private String country;
	private long id;
	private String joined;
	private double lat,lon;
	private String link;
	private String name;
	private String state;
	private String visited;
	private String zip;
	
	public String getBio() {
		return bio;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public long getId() {
		return id;
	}
	public String getJoined() {
		return joined;
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
	public String getName() {
		return name;
	}
	public String getState() {
		return state;
	}
	public String getVisited() {
		return visited;
	}
	public String getZip() {
		return zip;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setId(long id) {
		this.id = id;
	}

	public void setJoined(String joined) {
		this.joined = joined;
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
	public void setName(String name) {
		this.name = name;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setVisited(String visited) {
		this.visited = visited;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
