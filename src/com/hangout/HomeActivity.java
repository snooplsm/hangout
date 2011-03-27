package com.hangout;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;

import com.hangout.api.Event;
import com.hangout.service.ApiService;

public class HomeActivity extends HangoutActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		
		try {
			List<Event> events1 = getHangoutApplication().getEventDao().getEventsWithin24Hours(true);
			List<Event> events2 = getHangoutApplication().getEventDao().getEventsWithin24Hours(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

	@Override
	protected void onResume() {
		super.onResume();		
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
		}
	}

	
}
