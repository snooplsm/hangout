package com.hangout.service;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hangout.api.Checkin;
import com.hangout.api.Checkins;
import com.hangout.api.Event;
import com.hangout.api.Events;
import com.hangout.dao.Dao;

public class CheckinDao extends Dao {

	private static final String TABLE = "checkin";
	private static final String SELECT = "select id, comment, lat, lon, event_id, group_id, member_id, time from " + TABLE + " where ";
	
	private static final String COUNT_CHECKIN = "select count(*) from checkin where ";
	private static final String WHERE_ID = "id=%s";
	private static final String COUNT_CHECKIN_BY_EVENT = COUNT_CHECKIN + "event_id='%s'";
	private static final String WHERE_EVENT = "event_id='%s' order by ";

	
	private Parser<Checkin> checkinParser = new Parser<Checkin>() {

		@Override
		public ContentValues args(Checkin t) {
			ContentValues c = new ContentValues();
			c.put("id", t.getId());
			c.put("comment", t.getComment());
			c.put("lat", t.getLat());
			c.put("lon", t.getLon());
			c.put("event_id", t.getEventId());
			c.put("group_id", t.getGroupId());
			c.put("member_id", t.getMemberId());
			c.put("time", t.getTime());
			return c;
		}

		@Override
		public Checkin parse(Cursor r) {
			Checkin c = new Checkin();
			c.setId(r.getLong(0));
			c.setComment(r.getString(1));
			c.setLat(r.getDouble(2));
			c.setLon(r.getDouble(3));
			c.setEventId(r.getLong(4));			
			c.setGroupId(r.getLong(5));
			c.setMemberId(r.getLong(6));
			c.setTime(r.getLong(7));
			return c;
		}
		
	};
	
	public void save(Checkin checkin) {
		ContentValues cv = checkinParser.args(checkin);
		if(count(COUNT_CHECKIN,checkin.getId())==0) {
			insert(TABLE,cv);
		} else {
			update(TABLE,cv,WHERE_ID,checkin.getId());
		}				
	}
	
	public void save(Checkins events) {
		SQLiteDatabase db = db();
		db.beginTransaction();
		try {
			for(Checkin checkin : events.getCheckins()) {
				save(checkin);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	public int countCheckinsForEvent(String eventId) {
		return count(COUNT_CHECKIN_BY_EVENT,eventId);
	}
	
	public Long getLastCheckinTimeForEvent(String eventId) {
		Cursor c = cursor("select time from "+ TABLE + " where event_id='%s' order by time desc limit 1",eventId);
		try {
			if(c.getCount()==0) {
				return null;
			}
			c.moveToFirst();
			return c.getLong(0);
		}finally {
			c.close();
		}
	}
	
	public List<Checkin> getCheckinsForEvent(String eventId) {
		return all(cursor(SELECT+WHERE_EVENT,eventId),checkinParser);
	}
	
}
