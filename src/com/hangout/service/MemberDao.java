package com.hangout.service;

import android.content.ContentValues;
import android.database.Cursor;

import com.hangout.api.Member;
import com.hangout.dao.Dao;
import com.hangout.dao.Dao.Parser;

public class MemberDao extends Dao {
	
	//id integer primary key, bio text, city varchar(255), state varchar(255), country varchar(255), zip varchar(15), lat real, lon real
	private static final String SELECT_MEMBER = "select id, bio, name, city, state, country, zip, lat, lon from member where id = %s";
	private static final String INSERT_MEMBER = "insert into member(id,bio,name,city,state,country,zip,lat,lon) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)";
	private static final String WHERE = "id=%s";
	private static final String COUNT_MEMBER = "select count(*) from member where id=%s";
	private static final String TABLE = "member";
	
	private Parser<Member> memberParser = new Parser<Member>() {

		@Override
		public Member parse(Cursor cursor) {
			long id = cursor.getLong(0);
			String bio = cursor.getString(1);
			String name = cursor.getString(2);
			String city = cursor.getString(3);
			String state = cursor.getString(4);
			String country = cursor.getString(5);
			String zip = cursor.getString(6);
			double lat = cursor.getDouble(7);
			double lon = cursor.getDouble(8);
			Member member = new Member();
			member.setId(id);
			member.setBio(bio);
			member.setName(name);
			member.setCity(city);
			member.setState(state);
			member.setCountry(country);
			member.setZip(zip);
			member.setLat(lat);
			member.setLon(lon);
			return member;
		}
		
		@Override
		public ContentValues args(Member member) {
			ContentValues cv = new ContentValues(9);
			cv.put("id",member.getId());
			cv.put("bio",member.getBio());
			cv.put("name", member.getName());
			cv.put("city",member.getCity());
			cv.put("state", member.getState());
			cv.put("country", member.getCountry());
			cv.put("zip", member.getZip());
			cv.put("lat", member.getLat());
			cv.put("lon", member.getLon());			
			return cv;
		}
		
	};
	
	
	
	public Member get(long memberId) {
		Cursor cursor = cursor(SELECT_MEMBER,memberId);
		return only(cursor, memberParser);
	}
	
	public void save(Member member) {
		ContentValues cv = memberParser.args(member);
		if(count(COUNT_MEMBER,member.getId())==0) {
			insert(TABLE,cv);
		} else {
			update(TABLE,cv,WHERE,member.getId());
		}
	}
	

	
}
