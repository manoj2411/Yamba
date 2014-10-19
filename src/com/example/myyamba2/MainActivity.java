package com.example.myyamba2;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//public class MainActivity extends Activity implements OnClickListener {
public class MainActivity extends Activity {
//	Button buttonUpdate;
	EditText editStatus;
	public static String TAG = "StatusActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Point A
		//Debug.startMethodTracing("Yamba.trace");
		
		setContentView(R.layout.activity_main);
		// buttonUpdate = (Button) findViewById(R.id.button_update); 
		editStatus = (EditText) findViewById(R.id.edit_status);		
		// buttonUpdate.setOnClickListener(this);
	}

	
	@Override
	protected void onStop() {
		super.onStop();
		// Point B
		// Debug.stopMethodTracing();
	}

	// @Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		editStatus.setText("");
		new PostToTwitter().execute(status);
		Log.d("Status Avtivity", "Update Status Clicked with text: " + status);
	}
	
	class PostToTwitter extends AsyncTask<String, Void, String> {
		
		// New Thread
		@Override
		protected String doInBackground(String... params) {
			try {
				Twitter twitter = new Twitter("student", "password");
				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				twitter.setStatus(params[0]);
				return  "Successfully posted: " + params[0];
			} catch (TwitterException e) {
				Log.e(TAG, "Died: ", e);
				return "Failed posting: ";
			}
		}
		
		// UI Thread
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
		}
		
	}

	// Menu stuff
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intentUpdateer = new Intent(this, UpdaterService.class);
		Intent intentRefresh = new Intent(this, RefreshService.class);
		
		switch(item.getItemId()) {
		case R.id.item_start_service:
			startService(intentUpdateer);
			return true;
		case R.id.stop_service:
			stopService(intentUpdateer);
			return true;
		case R.id.refresh_service:
			Log.d(TAG, "Menu clicked -- refresh_service");
			startService(intentRefresh);
			return true;
		case R.id.item_prefs:
			Log.d(TAG, "Menu clicked -- Preferences");
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
		default:
			Log.d(TAG, "Menu clicked But default habdler");
			return false;
		}
	}
	
	// Called only once when menu button clicked for the first time
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
}
