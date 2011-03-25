package com.hangout.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class MemberL implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_id")
	private long id;
	
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		MemberL other = (MemberL) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
