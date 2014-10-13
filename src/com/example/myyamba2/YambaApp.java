package com.example.myyamba2;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.util.Log;

public class YambaApp extends Application {
	static final String TAG = "YambaApp";
	private Twitter twitter;
	
	@Override
	public void onCreate() {
		super.onCreate();
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		Log.d(TAG, "onCreated Yamba App");
	}

	public Twitter getTwitter() {
		return twitter;
	}
	
}
