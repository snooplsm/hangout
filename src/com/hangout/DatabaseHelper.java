package com.hangout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	
	private long memberId;
	
	public DatabaseHelper(Context context, long memberId) {		
		super(context, "member_"+memberId+"_database.sqlite", null, VERSION);
		this.memberId = memberId;
	}	
		
	public long getMemberId() {
		return memberId;
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		db.setLockingEnabled(true);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String[] create = new String[] {
			"create table member(id integer primary key, bio text, name varchar(255), city varchar(255), state varchar(255), country varchar(255), zip varchar(15), lat real, lon real)",
			"create table mgroup(id integer primary key, description text, group_photo_count integer, group_urlname varchar(255), join_mode varchar(15), lat real, lon real, members integer, name varchar(255), organizer_id integer, organizer_name varchar(255), rating real, short_link varchar(255), city varchar(255), state varchar(255), country varchar(255), zip varchar(15), visibility varchar(50), who varchar(100))",
			"create table group_topic(id integer primary key, name varchar(255), urlkey varchar(255))",
			"create table event(id varchar(50) primary key, description text, event_url varchar(255), name varchar(255), rsvp_limit integer, status varchar(100), time integer, utc_offset integer, visibility varchar(50), yes_rsvp_count integer, venue_id integer, group_id integer, CONSTRAINT fk_venue_id FOREIGN KEY (venue_id) REFERENCES venue(id), CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES mgroup(id))",
			"create table venue(id integer primary key, address_1 varchar(255), address_2 varchar(255), name varchar(255), city varchar(255), state varchar(255), country varchar(255), zip varchar(15), lat real, lon real)",
			"create table photo(id integer primary key, highres_link varchar(255), thumb_link varchar(255), photo_link varchar(255), caption varchar(255), album_id integer, CONSTRAINT fk_album_id FOREIGN KEY (album_id) REFERENCES photo_album(id))",
			"create table photo_album(id integer primary key, album_photo integer, group_id integer, event_id varchar(50), created integer, updated integer, CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES mgroup(id), CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES event(id), CONSTRAINT fk_album_photo_id FOREIGN KEY (album_photo) REFERENCES photo(id))"
		};
		
		for(String str : create) {
			db.execSQL(str);
		}
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int currentVersion, int newVersion) {		
	}
}
