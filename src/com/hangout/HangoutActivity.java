package com.hangout;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.hangout.api.Checkins;
import com.hangout.api.Events;
import com.hangout.api.Groups;
import com.hangout.api.Member;
import com.hangout.service.ApiService;

public class HangoutActivity extends Activity {
	
	private ConnectivityManager mConnectivityManager;

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {

			mService = new Messenger(service);

			try {
				Message msg = Message.obtain(null,
						ApiService.MSG_REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);

				HangoutActivity.this.onServiceConnected();
			} catch (RemoteException e) {
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
		}
	};

	void doBindService() {
		boolean bound = bindService(new Intent(this,
				ApiService.class), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = bound;
	}

	void doUnbindService() {
		if (mIsBound) {
			if (mService != null) {
				try {
					Message msg = Message.obtain(null,
							ApiService.MSG_UNREGISTER_CLIENT);
					msg.replyTo = mMessenger;
					mService.send(msg);
				} catch (RemoteException e) {
				}
			}

			unbindService(mConnection);
			mIsBound = false;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		doBindService();
	}
	
	protected Messenger mService;

	boolean mIsBound;

	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ApiService.MSG_API_RECEIVED_SELF:
				onGetSelf((Member)msg.obj);
				break;
			case ApiService.MSG_API_RECEIVED_GROUPS:
				onGetGroups((Groups)msg.obj);
				break;
			case ApiService.MSG_API_RECEIVED_EVENTS:
				onGetEvents((Events)msg.obj);
				break;
			case ApiService.MSG_API_RECEIVED_CHECKINS_FOR_EVENT:
				onGetCheckins((Checkins)msg.obj);
				break;
			default:
				HangoutActivity.this.handleMessage(msg);
			}

		}
	}

	protected void handleMessage(Message message) {
		
	}
	
	private void onGetSelf(Member member) {
		getHangoutApplication().setMember(member);
		getHangoutApplication().getStorage().setMeetupCurrentMemberId(member.getId());
		getHangoutApplication().getMemberDao().save(member);
		Message message = Message.obtain(null,ApiService.MSG_API_GET_GROUPS, member.getId());
		Message messageEvents = Message.obtain(null, ApiService.MSG_API_GET_EVENTS, member.getId());
		try {
			mService.send(message);
			mService.send(messageEvents);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void onGetCheckins(Checkins checkins) {
		getHangoutApplication().getCheckinDao().save(checkins);
	}
	
	protected void onGetEvents(Events events) {
		getHangoutApplication().getEventDao().save(events);
	}
	
	protected void onGetGroups(Groups groups) {
		getHangoutApplication().getGroupDao().save(groups);		
		
	}
	
	protected HangoutApplication getHangoutApplication() {
		return (HangoutApplication)super.getApplication();
	}

	@Override
	protected void onStop() {
		super.onStop();
		doUnbindService();
	}
	
	protected void onServiceConnected() {
		
	}
	
	protected final boolean hasInternet() {
		NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
		if(info==null) { return false; }
		return info.isConnected();
	}
	
	
	
	final Messenger mMessenger = new Messenger(new IncomingHandler());
}
