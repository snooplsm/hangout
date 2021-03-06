package com.hangout.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hangout.api.Group;
import com.hangout.api.Groups;
import com.hangout.dao.Dao;

public class GroupDao extends Dao {

	//                               organizer_id integer, organizer_name varchar(255), rating real, short_link varchar(255), city varchar(255), state varchar(255), country varchar(255), zip varchar(15), visibility varchar(50), who varchar(100))",
	private static final String SELECT_GROUP = "select id, description, group_photo_count, group_urlname, join_mode, lat, lon, members, name, organizer_id, organizer_name, rating, short_link, city, state, country, zip, visibility, who from mgroup where id = %s";
	private static final String WHERE = "id=%s";
	private static final String COUNT_GROUP = "select count(*) from mgroup where id=%s";
	private static final String TABLE = "mgroup";
	
	private Parser<Group> groupParser = new Parser<Group>() {
		
		@Override
		public ContentValues args(Group t) {
			ContentValues c = new ContentValues(19);
			c.put("id", t.getId());
			c.put("group_urlname", t.getGroupUrlName());
			c.put("name",t.getName());
			c.put("join_mode", t.getJoinMode());
			c.put("lat", t.getLat());
			c.put("lon", t.getLon());
		
			if(t.getDescription()!=null) {
				c.put("description", t.getDescription());
			}
			if(t.getGroupPhotoCount()!=null) {
				c.put("group_photo_count", t.getGroupPhotoCount());
			}
			if(t.getMembers()!=null) {
				c.put("members", t.getMembers());
			}
			
			if(t.getOrganizerId()!=null) {
				c.put("organizer_id", t.getOrganizerId());
				c.put("organizer_name", t.getOrganizerName());
			}
				
			if(t.getRating()!=null) {
				c.put("rating", t.getRating());
			}
			if(t.getShortLink()!=null) {
				c.put("short_link",t.getShortLink());
			}
			if(t.getCity()!=null) {
				c.put("city", t.getCity());
				c.put("state", t.getState());
				c.put("country", t.getCountry());
				c.put("zip", t.getZip());
			}
			if(t.getVisibility()!=null) {
				c.put("visibility", t.getVisibility());
			}
			if(t.getWho()!=null) {
				c.put("who", t.getWho());
			}
			return c;
		}

		@Override
		public Group parse(Cursor cursor) {
			Group group = new Group();
			group.setId(cursor.getLong(0));
			group.setDescription(cursor.getString(1));
			group.setGroupPhotoCount(cursor.getInt(2));
			group.setGroupUrlName(cursor.getString(3));
			group.setJoinMode(cursor.getString(4));
			group.setLat(cursor.getDouble(5));
			group.setLon(cursor.getDouble(6));
			group.setMembers(cursor.getInt(7));
			group.setName(cursor.getString(8));
			group.setOrganizerId(cursor.getLong(9));
			group.setOrganizerName(cursor.getString(10));
			group.setRating(cursor.getDouble(11));
			group.setShortLink(cursor.getString(12));
			group.setCity(cursor.getString(13));
			group.setState(cursor.getString(14));
			group.setCountry(cursor.getString(15));
			group.setZip(cursor.getString(16));
			group.setVisibility(cursor.getString(17));
			group.setWho(cursor.getString(18));
			return group;
		}
		
	};
	
	public Group get(long groupId) {
		Cursor cursor = cursor(SELECT_GROUP,groupId);
		return only(cursor, groupParser);
	}
	
	public void save(Group group) {
		ContentValues cv = groupParser.args(group);
		if(count(COUNT_GROUP,group.getId())==0) {
			insert(TABLE,cv);
		} else {
			update(TABLE,cv,WHERE,group.getId());
		}
	}
	
	public void save(Groups groups) {
		SQLiteDatabase db = db();
		db.beginTransaction();
		try {
			for(Group group : groups.getGroups()) {
				save(group);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
}
