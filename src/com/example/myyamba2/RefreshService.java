package com.example.myyamba2;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {
	
	static final String TAG = "RefreshService";
	
	@Override
	public void onCreate() { 		
		Log.d(TAG, "onCreated");
		super.onCreate();
	} 

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroyed");
		super.onDestroy();
	}	
	
	public RefreshService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		try {
			List<Status> timeline = ((YambaApp) getApplication()).getTwitter().getPublicTimeline();
			
			for (Status status : timeline) {
				Log.d(TAG, String.format("%s: %s", status.user.name,
						status.text));
			}
		} catch (TwitterException e) {
			Log.d(TAG, "Access failed", e);
		}
		
	}

}
