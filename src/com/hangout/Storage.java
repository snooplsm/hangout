package com.hangout;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {

	private Context ctx;
	
	private static final String STORAGE="s";
	private static final String MEETUP_TOKEN="meetup-token";
	private static final String MEETUP_TOKEN_SECRET="meetup-token-secret";
	private static final String MEETUP_VERIFIER="meetup-verifier";
	private static final String MEETUP_CURRENT_MEMBER_ID = "meetup-current-memeber-id";
	private static final String MEETUP_EVENT_SYNC = "meetup-event-sync-%s";
	
	public Storage(Context ctx) {
		super();
		this.ctx = ctx;
	}
	
	public void saveMeetupToken(String token) {
		save(MEETUP_TOKEN,token);
	}
	
	public void saveMeetupTokenSecret(String tokenSecret) {
		save(MEETUP_TOKEN_SECRET,tokenSecret);
	}
	
	public String getMeetupTokenSecret() {
		return get(MEETUP_TOKEN_SECRET);
	}
	
	public String getMeetupToken() {
		return get(MEETUP_TOKEN);
	}
	
	public void saveMeetupVerifier(String verifier) {
		save(MEETUP_VERIFIER,verifier);
	}
	
	public String getMeetupVerifier() {
		return get(MEETUP_VERIFIER);
	}
	
	public long getMeetupCurrentMemberId() {
		return getLong(MEETUP_CURRENT_MEMBER_ID);
	}
	
	public void setMeetupCurrentMemberId(long memberId) {
		save(MEETUP_CURRENT_MEMBER_ID,memberId);
	}
	
	public void saveMeetupEventSync(long memberId, long time) {
		save(String.format(MEETUP_EVENT_SYNC,memberId),time);
	}
	
	public long getMeetupEventSync(long memberId) {
		return getLong(String.format(MEETUP_EVENT_SYNC,memberId));
	}
	
	private void save(String key, String value) {
		SharedPreferences p = ctx.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
		p.edit().putString(key, value).commit();
	}
	
	private String get(String key) {
		SharedPreferences p = ctx.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
		return p.getString(key, null);
	}
	
	private void save(String key, long value) {
		SharedPreferences p = ctx.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
		p.edit().putLong(key, value).commit();
	}
	
	private long getLong(String key) {
		SharedPreferences p = ctx.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
		return p.getLong(key, 0);
	}
	
	
	
}
