package com.example.myyamba2;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
		setContentView(R.layout.activity_main);
//		buttonUpdate = (Button) findViewById(R.id.button_update); 
		editStatus = (EditText) findViewById(R.id.edit_status);
		
//		buttonUpdate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	@Override
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
				e.printStackTrace();
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
}
