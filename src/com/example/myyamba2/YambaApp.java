package com.example.myyamba2;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApp extends Application implements OnSharedPreferenceChangeListener {
	static final String TAG = "YambaApp";
	private Twitter twitter;
	SharedPreferences prefs;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Prefs stuff
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		
		// twitter = new Twitter("student", "password");
		// twitter.setAPIRootUrl("http://yamba.marakana.com/api");
	
		Log.d(TAG, "onCreated Yamba App");
	}

	public Twitter getTwitter() {
		if( twitter == null) {
			String username = prefs.getString("username", "");
			String password = prefs.getString("password", "");
			String server = prefs.getString("server", "");
			
			// twitter stuff		
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl(server);
		}
		return twitter;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String key) {
		twitter = null;	
		Log.d(TAG, "\n\n\n onSharedPreferenceChanged for key: " + key);
	}
	
}
