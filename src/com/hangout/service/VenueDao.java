package com.hangout.service;

import android.content.ContentValues;
import android.database.Cursor;

import com.hangout.api.Venue;
import com.hangout.dao.Dao;

public class VenueDao extends Dao {

	private static final String SELECT_VENUE = "select id, address_1, address_2, name, city, state, country, zip, lat, lon from venue where id=%s";
	private static final String COUNT_VENUE = "select count(*) from venue where id=%s";
	private static final String TABLE = "venue";
	private static final String WHERE = "id=%s";
	
	private Parser<Venue> venueParser = new Parser<Venue>() {
		
		@Override
		public ContentValues args(Venue t) {
			ContentValues c = new ContentValues();
			c.put("id", t.getId());
			c.put("address_1", t.getAddress1());
			c.put("address_2", t.getAddress2());
			c.put("name", t.getName());
			c.put("city", t.getCity());
			c.put("state", t.getState());
			c.put("country",t.getCountry());
			c.put("zip", t.getZip());
			c.put("lat", t.getLat());
			c.put("lon", t.getLon());
			return c;
		}

		@Override
		public Venue parse(Cursor c) {
			Venue v = new Venue();
			v.setId(c.getInt(0));
			v.setAddress1(c.getString(1));
			v.setAddress2(c.getString(2));
			v.setName(c.getString(3));
			v.setCity(c.getString(4));
			v.setState(c.getString(5));
			v.setCountry(c.getString(6));
			v.setZip(c.getString(7));
			v.setLat(c.getDouble(8));
			v.setLon(c.getDouble(9));
			return v;
		}
		
		
	};
	
	public Venue get(long venueId) {
		Cursor cursor = cursor(SELECT_VENUE,venueId);
		return only(cursor, venueParser);
	}
	
	public void save(Venue venue) {
		ContentValues cv = venueParser.args(venue);
		if(count(COUNT_VENUE,venue.getId())==0) {
			insert(TABLE,cv);
		} else {
			update(TABLE,cv,WHERE,venue.getId());
		}
	}
	
}
