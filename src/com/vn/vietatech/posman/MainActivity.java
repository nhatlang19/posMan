package com.vn.vietatech.posman;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		Button btnExit = (Button) findViewById(R.id.btnExit);

		// exit application
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		// login
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView txtUserName = (TextView) findViewById(R.id.txtUsername);
				TextView txtPassword = (TextView) findViewById(R.id.txtPassword);

				String username = txtUserName.getText().toString();
				String password = txtPassword.getText().toString();

				Intent myIntent = new Intent(MainActivity.this,
						POSMenuActivity.class);
				startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
