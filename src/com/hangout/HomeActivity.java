package com.hangout;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;

import com.hangout.service.ApiService;

public class HomeActivity extends HangoutActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
				
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
