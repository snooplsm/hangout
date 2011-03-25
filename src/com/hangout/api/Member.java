package com.hangout.api;

import java.io.Serializable;

public class Member implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bio;
	private String city;
	private String country;
	private Long id;
	private String joined;
	private double lat,lon;
	private String link;
	private String name;
	private String state;
	private String visited;
	private String zip;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getBio() {
		return bio;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public Long getId() {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
	public void setId(Long id) {
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
