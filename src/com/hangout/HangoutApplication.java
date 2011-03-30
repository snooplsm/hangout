package com.hangout;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.apache.http.client.methods.HttpRequestBase;

import android.app.Application;

import com.hangout.api.MeetupApi;
import com.hangout.api.Member;
import com.hangout.service.CheckinDao;
import com.hangout.service.EventDao;
import com.hangout.service.GroupDao;
import com.hangout.service.MemberDao;
import com.hangout.service.VenueDao;

public class HangoutApplication extends Application {

	private static CommonsHttpOAuthProvider provider;

	private static CommonsHttpOAuthConsumer consumer;

	private Storage storage;
	
	private DatabaseHelper helper;

	private MeetupApi api;
	
	private Member member;
	
	private MemberDao memberDao;
	
	private GroupDao groupDao;
	
	private VenueDao venueDao;
	
	private EventDao eventDao;
	
	private CheckinDao checkinDao;

	@Override
	public void onCreate() {
		super.onCreate();

		storage = new Storage(this);		
		String request = getString(R.string.meetup_request);
		String access = getString(R.string.meetup_access);
		String authorize = getString(R.string.meetup_authorize);
		String key = getString(R.string.meetup_key);
		String secret = getString(R.string.meetup_secret);
		if (provider == null) {
			provider = new CommonsHttpOAuthProvider(request, access, authorize);
		}
		if (consumer == null) {
			consumer = new CommonsHttpOAuthConsumer(key, secret);
			consumer.setTokenWithSecret(storage.getMeetupToken(), storage
					.getMeetupTokenSecret());
		}
		api = new MeetupApi() {

			@Override
			protected <T extends HttpRequestBase> T sign(T method) {
				try {
					consumer.sign(method);
				}catch (Exception e) {
					throw new RuntimeException(e);
				}
				return method;
			}
			
		};
	}

	public CommonsHttpOAuthProvider getProvider() {
		return provider;
	}

	
	
	public Storage getStorage() {
		return storage;
	}

	public CommonsHttpOAuthConsumer getConsumer() {
		return consumer;
	}

	public MeetupApi getApi() {
		return api;
	}

	public void setApi(MeetupApi api) {
		this.api = api;
	}

	public Member getMember() {
		return member;
	}
	
	public void initializeMember(long memberId) {
		if(helper!=null && memberId!=helper.getMemberId()) { 
			helper.close();
			helper = new DatabaseHelper(this,memberId);
		} else {
			helper = new DatabaseHelper(this,memberId);
		}
		if(memberDao==null) {
			memberDao = new MemberDao();
		}
		memberDao.setHelper(helper);
	}
	
	public void setMember(Member member) {
		this.member = member;
		if(helper!=null && Long.valueOf(member.getId()).equals(helper.getMemberId())) {
			helper.close();
			helper = new DatabaseHelper(this, member.getId());
		} else {
			helper = new DatabaseHelper(this, member.getId());
		}
		
		if(memberDao==null) {
			memberDao = new MemberDao();
		}
		if(groupDao==null) {
			groupDao = new GroupDao();
		}
		if(venueDao==null) {
			venueDao = new VenueDao();
		}
		if(eventDao==null) {
			eventDao = new EventDao(groupDao,venueDao);
		}		
		if(checkinDao==null) {
			checkinDao = new CheckinDao();
		}
		memberDao.setHelper(helper);
		groupDao.setHelper(helper);
		venueDao.setHelper(helper);
		eventDao.setHelper(helper);
		checkinDao.setHelper(helper);
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public VenueDao getVenueDao() {
		return venueDao;
	}

	public EventDao getEventDao() {
		return eventDao;
	}

	public CheckinDao getCheckinDao() {
		return checkinDao;
	}
	
	
}
