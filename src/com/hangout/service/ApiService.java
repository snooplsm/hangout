package com.hangout.service;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.hangout.HangoutApplication;
import com.hangout.Storage;
import com.hangout.api.Events;
import com.hangout.api.Groups;
import com.hangout.api.Member;
import com.hangout.api.TokenExpiredException;

public class ApiService extends Service {

	public static final int MSG_REGISTER_CLIENT = 1;

	int mValue = 0;
	/**
	 * Command to the service to unregister a client, ot stop receiving
	 * callbacks from the service. The Message's replyTo field must be a
	 * Messenger of the client as previously given with MSG_REGISTER_CLIENT.
	 */
	public static final int MSG_UNREGISTER_CLIENT = 2;

	public static final int MSG_API_GET_SELF = 3;

	public static final int MSG_API_RECEIVED_SELF = 4;

	public static final int MSG_API_IO_EXCEPTION = 5;

	public static final int MSG_API_NEED_AUTHENTICATION = 6;

	public static final int MSG_API_MEETUP_TOKEN_CHANGED = 7;

	public static final int MSG_API_GET_UPCOMMING_EVENTS = 8;

	public static final int MSG_API_GET_GROUPS = 9;

	public static final int MSG_API_RECEIVED_GROUPS = 10;

	public static final int MSG_API_GET_EVENTS = 11;

	public static final int MSG_API_RECEIVED_EVENTS = 11;

	ArrayList<Messenger> mClients = new ArrayList<Messenger>();

	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				break;
			case MSG_API_GET_SELF:
				// new Thread() {
				//
				// @Override
				// public void run() {
				try {
					System.out.println("get self");
					Member member = getHangoutApplication().getApi().getSelf();
					ApiService.this.sendMessage(MSG_API_RECEIVED_SELF, member);
				} catch (IOException e) {
					ApiService.this.sendMessage(MSG_API_IO_EXCEPTION,
							MSG_API_GET_SELF);
				} catch (TokenExpiredException e) {
					ApiService.this.sendMessage(MSG_API_NEED_AUTHENTICATION,
							null);
				}
				// }

				// }.start();
				break;
			case MSG_API_GET_UPCOMMING_EVENTS: {
				final long memberId = (Long) msg.obj;
				new Thread() {

					@Override
					public void run() {
						try {
							Events events = getHangoutApplication().getApi()
									.getUpcommingEvents(memberId);
							ApiService.this.sendMessage(MSG_API_GET_SELF,
									events);
						} catch (IOException e) {
							ApiService.this.sendMessage(MSG_API_IO_EXCEPTION,
									MSG_API_GET_UPCOMMING_EVENTS);
						} catch (TokenExpiredException e) {
							ApiService.this.sendMessage(
									MSG_API_NEED_AUTHENTICATION, null);
						}
					}

				}.start();
			}
				break;
			case MSG_API_GET_GROUPS: {
				final long memberId = (Long) msg.obj;
				new Thread() {

					@Override
					public void run() {
						try {
							Groups groups = getHangoutApplication().getApi()
									.getGroups(memberId);
							ApiService.this.sendMessage(
									MSG_API_RECEIVED_GROUPS, groups);
						} catch (IOException e) {
							ApiService.this.sendMessage(MSG_API_IO_EXCEPTION,
									MSG_API_GET_GROUPS);
						} catch (TokenExpiredException e) {
							ApiService.this.sendMessage(
									MSG_API_NEED_AUTHENTICATION, null);
						}
					}
				}.start();
			}
			case MSG_API_GET_EVENTS: {
				final long memberId = (Long) msg.obj;
				new Thread() {
					@Override
					public void run() {
						try {
							Events events = getHangoutApplication().getApi()
									.getUpcommingEvents(memberId);
							ApiService.this.sendMessage(
									MSG_API_RECEIVED_EVENTS, events);
						} catch (IOException e) {
							ApiService.this.sendMessage(MSG_API_IO_EXCEPTION,
									MSG_API_GET_EVENTS);
						} catch (TokenExpiredException e) {
							ApiService.this.sendMessage(
									MSG_API_NEED_AUTHENTICATION, null);
						}
					}
				}.start();

				break;
			}
			case MSG_API_MEETUP_TOKEN_CHANGED:
				Storage storage = getHangoutApplication().getStorage();
				getHangoutApplication().getConsumer().setTokenWithSecret(
						storage.getMeetupToken(),
						storage.getMeetupTokenSecret());
			default:
				super.handleMessage(msg);
			}
		}
	}

	Messenger mMessenger = new Messenger(new IncomingHandler());

	private void sendMessage(int code, Object msg) {

		for (int i = mClients.size() - 1; i >= 0; i--) {
			Messenger messenger = mClients.get(i);
			try {
				messenger.send(Message.obtain(null, code, msg));
			} catch (RemoteException e) {
				mClients.remove(i);
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	private HangoutApplication getHangoutApplication() {
		return (HangoutApplication) getApplication();
	}

}
