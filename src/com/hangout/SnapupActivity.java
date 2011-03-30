package com.hangout;

import oauth.signpost.OAuthConsumer;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;

import com.hangout.api.Member;
import com.hangout.service.ApiService;

public class SnapupActivity extends HangoutActivity {
	
	private static final int DIALOG_NEED_INTERNET = 0;
	private static final int DIALOG_NEED_AUTHENTICATE = 1;
	private static final int DIALOG_LOGGING_IN = 2;
	
	
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);			
		
		long currentMeetupMemberId = getHangoutApplication().getStorage().getMeetupCurrentMemberId();
		if(currentMeetupMemberId!=0) {
			if(getHangoutApplication().getMember()==null) {
				getHangoutApplication().initializeMember(currentMeetupMemberId);
				getHangoutApplication().setMember(getHangoutApplication().getMemberDao().get(currentMeetupMemberId));
			}
			Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			finish();
			return;
		}
		setContentView(R.layout.main);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id){
		case DIALOG_NEED_INTERNET: 
			dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.need_internet_connection));
			return dialog;
		
		case DIALOG_NEED_AUTHENTICATE:
			dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.need_to_authenticate));
			return dialog;
			
		case DIALOG_LOGGING_IN:
			dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.logging_in));
			return dialog;
		}
		return null;
	}

	@Override
	public void onResume() {
		super.onResume();		
		Uri data = getIntent().getData();
		if (data == null) {
			new AsyncTask<Void, Uri, Uri>() {

				@Override
				protected void onPreExecute() {
					showDialog(DIALOG_NEED_AUTHENTICATE);
				}
				@Override
				protected Uri doInBackground(Void... arg0) {
					String uri;
					try {
						uri = getHangoutApplication().getProvider().retrieveRequestToken(getHangoutApplication().getConsumer(),
								getString(R.string.meetup_callback));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					return Uri.parse(uri);
				}

				@Override
				protected void onPostExecute(Uri result) {
					removeDialog(DIALOG_NEED_AUTHENTICATE);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(result);
					startActivity(intent);
				}

			}.execute();
		} else {
			String verifier = data.getQueryParameter("oauth_verifier");
			new AsyncTask<String,Void,Void>() {

				protected void onPreExecute() {
					showDialog(DIALOG_LOGGING_IN);
				};
				
				@Override
				protected Void doInBackground(String... params) {
					try {
						getHangoutApplication().getProvider().retrieveAccessToken(getHangoutApplication().getConsumer(), params[0]);
						Member member = getHangoutApplication().getApi().getSelf();
						getHangoutApplication().getStorage().setMeetupCurrentMemberId(member.getId());
						getHangoutApplication().setMember(member);
					} catch (Exception e) {

					}
					return null;
				}
				
				protected void onPostExecute(Void result) {
					removeDialog(DIALOG_LOGGING_IN);
					Intent intent = new Intent(SnapupActivity.this,HomeActivity.class);
					Storage storage = getHangoutApplication().getStorage();
					OAuthConsumer consumer = getHangoutApplication().getConsumer();
					storage.saveMeetupToken(consumer.getToken());
					storage.saveMeetupToken(consumer.getTokenSecret());				
					Message message = Message.obtain(null, ApiService.MSG_API_NEED_AUTHENTICATION);
					try {
						mService.send(message);
					} catch (RemoteException e) {}
					finish();
					startActivity(intent);
				};
				
			}.execute(verifier);
 
		}
	}

}