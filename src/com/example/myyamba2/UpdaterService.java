package com.example.myyamba2;

import java.util.List;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	static final String TAG = "UpdaterService";
//	static final int DELAY = 30;
//	Twitter twitter;
	boolean running = true;
	
	@Override
	public void onCreate() {
//		twitter = new Twitter("student", "password");
//		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		
		Log.d(TAG, "onCreated");
		super.onCreate();
	} 

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread() {
			public void run() {
				try {
					while(running) {
						List<Status> timeline = ((YambaApp) getApplication()).getTwitter().getPublicTimeline();
						
						for (Status status : timeline) {
							Log.d(TAG, String.format("%s: %s", status.user.name,
									status.text));
						}
						int delay = Integer.parseInt(((YambaApp) getApplication()).prefs.getString("delay", "30"));
						Thread.sleep(delay * 1000);
						
					}
				} catch (TwitterException e) {
					e.printStackTrace();
					Log.d(TAG, "Failed bcz of network error.", e);
				} catch (InterruptedException e) {
					Log.d(TAG, "Updater interrupted.", e);
				}
			}
		}.start();
		
		// Log.d(TAG, "onStarted");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		running = false;
		Log.d(TAG, "onDestroyed");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
