package com.hangout.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class Photo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long updated,created;
	
	@JsonProperty("highres_link")
	private String highresLink;
	
	@JsonProperty("thumb_link")
	private String thumbLink;
	
	@JsonProperty("photo_link")
	private String photoLink;
	
	private String caption;
	
	@JsonProperty("photo_album")
	private PhotoAlbum photoAlbum;
	
	private MemberL member;
	
	@JsonProperty("photo_id")
	private long id;

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getHighresLink() {
		return highresLink;
	}

	public void setHighresLink(String highresLink) {
		this.highresLink = highresLink;
	}

	public String getThumbLink() {
		return thumbLink;
	}

	public void setThumbLink(String thumbLink) {
		this.thumbLink = thumbLink;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public PhotoAlbum getPhotoAlbum() {
		return photoAlbum;
	}

	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
	}

	public MemberL getMember() {
		return member;
	}

	public void setMember(MemberL member) {
		this.member = member;
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
		Photo other = (Photo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
