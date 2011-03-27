package com.hangout.service;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hangout.api.Event;
import com.hangout.api.EventGroup;
import com.hangout.api.Events;
import com.hangout.api.Group;
import com.hangout.dao.Dao;

public class EventDao extends Dao {

	//"create table event(id integer primary key, description text, event_url varchar(255), name varchar(255), rsvp_limit integer, status varchar(100), time integer, utc_offset integer, visibility varchar(50), yes_rsvp_count integer, venue_id integer, group_id integer, CONSTRAINT fk_venue_id FOREIGN KEY (venue_id) REFERENCES venue(id), CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES mgroup(id))",
	private static final String SELECT_EVENT = "select id, description, event_url, name, rsvp_limit, status, time, utc_offset, visibility, yes_rsvp_count, venue_id, group_id from event where";
	private static final String SELECT_24_EVENT = SELECT_EVENT + " datetime(time/1000,'unixepoch')<= datetime('now','+24 hours')";
	private static final String SELECT_PAST_3_HOURS_TO_24 = SELECT_24_EVENT +" and datetime(time/1000,'unixepoch')>= datetime('now','-3 hours')";
	private static final String WHERE = "id='%s'";
	private static final String COUNT_EVENT = "select count(*) from event where id='%s'";
	private static final String TABLE = "event";
	
	private GroupDao groupDao;
	
	private VenueDao venueDao;
	
	public EventDao(GroupDao groupDao, VenueDao venueDao) {
		this.groupDao = groupDao;
		this.venueDao = venueDao;
	}
	
	private Parser<Event> eventParser = new Parser<Event>() {

		@Override
		public ContentValues args(Event t) {
			ContentValues c = new ContentValues();
			c.put("id", t.getId());
			c.put("description",t.getDescription());
			c.put("event_url", t.getEventUrl());
			c.put("name", t.getName());
			c.put("rsvp_limit", t.getRsvpLimit());
			c.put("status", t.getStatus());
			c.put("time", t.getTime());
			c.put("utc_offset", t.getUtcOffset());
			c.put("visibility", t.getVisibility());
			c.put("yes_rsvp_count", t.getYesRSVPCount());
			if(t.getVenue()!=null) {
				c.put("venue_id", t.getVenue().getId());
			}
			c.put("group_id", t.getGroup().getId());
			return c;
		}

		@Override
		public Event parse(Cursor c) {
			Event e = new Event();
			e.setId(c.getString(0));
			e.setDescription(c.getString(1));
			e.setEventUrl(c.getString(2));
			e.setName(c.getString(3));
			e.setRsvpLimit(c.getInt(4));
			e.setStatus(c.getString(5));
			e.setTime(c.getLong(6));
			e.setUtcOffset(c.getInt(7));
			e.setVisibility(c.getString(8));
			e.setYesRSVPCount(c.getInt(9));
			e.setVenue(venueDao.get(c.getLong(10)));
			e.setGroup(groupDao.get(c.getLong(11)));			
			return e;
		}
		
	};
	
	public void save(Event event) {
		ContentValues cv = eventParser.args(event);
		if(count(COUNT_EVENT,event.getId())==0) {
			insert(TABLE,cv);
		} else {
			update(TABLE,cv,WHERE,event.getId());
		}
		if(event.getVenue()!=null) {
			venueDao.save(event.getVenue());
		}
		Group g = new Group();
		EventGroup eg = event.getGroup();
		g.setId(eg.getId());
		g.setJoinMode(eg.getJoinMode());
		g.setLat(eg.getLat());
		g.setLon(eg.getLon());
		g.setName(eg.getName());
		groupDao.save(g);
	}
	
	public void save(Events events) {
		SQLiteDatabase db = db();
		db.beginTransaction();
		try {
			for(Event group : events.getEvents()) {
				save(group);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	public List<Event> getEventsWithin24Hours(boolean previousThreeHours) {
		List<Event> events = all(cursor(previousThreeHours ? SELECT_PAST_3_HOURS_TO_24 : SELECT_24_EVENT),eventParser);
		return events;
	}
	
}
