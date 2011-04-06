package com.hangout;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.widget.TextView;

import com.hangout.api.Checkin;
import com.hangout.api.Checkins;
import com.hangout.api.Event;
import com.hangout.api.Events;
import com.hangout.api.Member;
import com.hangout.service.ApiService;

public class HomeActivity extends HangoutActivity {
	

	private List<Event> events;
	
	private Event currentEvent;
	
	private Checkin myCheckin;
	
	private Long lastCheckinsRefresh;
	
	private Long lastEventsRefresh;
	
	private static final int THREE_HOURS_AS_MILLIS = 10800000;
	
	private TextView groupName;
	private TextView eventName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);				 		
		
		setContentView(R.layout.home);
		
		groupName = (TextView)findViewById(R.id.group_name);
		eventName = (TextView)findViewById(R.id.event_name);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if(currentEvent==null) {
			if(events==null) {
				events = getHangoutApplication().getEventDao().getEventsWithin24Hours(true);
			}
			if(events!=null) {
				if(findCurrentEventAndSet()) {
					onCurrentEvent();
				}
			}
		} else {
			onCurrentEvent();
		}		
		
	}
	
	private void fetchEventsIfNecessary() {
		if(lastEventsRefresh==null) {
			Message msg = Message.obtain();
			msg.what = ApiService.MSG_API_GET_EVENTS;
			msg.obj = getHangoutApplication().getMember().getId();
			try {
				mService.send(msg);
			} catch (RemoteException e) {
			}
		}
	}

	private void fetchCheckins() {
		if(currentEvent==null) return;
		if(lastCheckinsRefresh!=null) {
			if(THREE_HOURS_AS_MILLIS+currentEvent.getTime()>lastCheckinsRefresh) {
				//OFFLINE CHECKINS ONLY
			} else {
				Message m = Message.obtain(null,ApiService.MSG_API_GET_CHECKINS_FOR_EVENT,currentEvent.getId());
				try {
					mService.send(m);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			Message m = Message.obtain(null,ApiService.MSG_API_GET_CHECKINS_FOR_EVENT,currentEvent.getId());
			try {
				mService.send(m);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		}
		
	}

	private void onCurrentEvent() {
		groupName.setText(currentEvent.getGroup().getName());
		eventName.setText(currentEvent.getName());
	}
	
	private boolean findCurrentEventAndSet() {
		for(Event event : events) {
			long difference = event.getTime()-System.currentTimeMillis();
			if(difference>-THREE_HOURS_AS_MILLIS && difference<THREE_HOURS_AS_MILLIS) {
				currentEvent = event;
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(currentEvent!=null) {
			outState.putSerializable("currentEvent", currentEvent);
		}
		if(myCheckin!=null) {
			outState.putSerializable("myCheckin", myCheckin);
		}
		if(lastCheckinsRefresh!=null) {
			outState.putLong("lastCheckinsRefresh", lastCheckinsRefresh);
		}
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle s) {
		super.onRestoreInstanceState(s);
		currentEvent = (Event)s.getSerializable("currentEvent");
		myCheckin = (Checkin)s.getSerializable("myCheckin");
		if(currentEvent!=null) {
			Long lastCheckinsRefresh = getHangoutApplication().getCheckinDao().getLastCheckinTimeForEvent(currentEvent.getId());
			Long actualRefresh = s.getLong("lastCheckinsRefresh");
			if(lastCheckinsRefresh!=null) {
				if(actualRefresh!=null) {
					this.lastCheckinsRefresh = Math.max(lastCheckinsRefresh, actualRefresh);
				}
			}
		}
	}

	@Override
	protected void onServiceConnected() {
		if(getHangoutApplication().getMember()==null) {
			Message message = Message.obtain(null,ApiService.MSG_API_GET_SELF);			
			try {
				mService.send(message);
			} catch (RemoteException e) {
				//TODO: handle or ignore
				throw new RuntimeException(e);
			}					
		} else {
			fetchEventsIfNecessary();
			fetchCheckins();
		}
		
	}

	@Override
	protected void onGetCheckins(Checkins checkins) {
		super.onGetCheckins(checkins);
		lastCheckinsRefresh = System.currentTimeMillis();
	}
	
	@Override
	protected void onGetSelf(Member member) {	
		super.onGetSelf(member);
		fetchEventsIfNecessary();
	}
	
	@Override
	protected void onGetEvents(Events events) {	
		super.onGetEvents(events);
		this.events = getHangoutApplication().getEventDao().getEventsWithin24Hours(true);			
		if(this.events!=null) {
			findCurrentEventAndSet();
		}
	}
	
	

}
