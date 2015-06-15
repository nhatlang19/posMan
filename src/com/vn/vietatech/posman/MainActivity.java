package com.vn.vietatech.posman;

import java.io.IOException;

import com.vn.vietatech.api.async.LoginAsync;
import com.vn.vietatech.api.async.UpdateTimeAsync;
import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.utils.UserUtil;
import com.vn.vietatech.utils.Utils;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {
	TextView txtUserName;
	TextView txtPassword;

	MyApplication globalVariable;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Utils.disableStrictMode();

		globalVariable = (MyApplication) getApplicationContext();

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		Button btnExit = (Button) findViewById(R.id.btnExit);

		txtUserName = (TextView) findViewById(R.id.txtUsername);
		txtPassword = (TextView) findViewById(R.id.txtPassword);

		try {
			Cashier cashier = UserUtil.read(this);
			if (cashier != null) {
				txtUserName.setText(cashier.getId().toString().trim());
			}
		} catch (IOException e1) {
		}

		txtPassword.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					login();
					return true;
				}
				return false;
			}
		});

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
				login();
			}
		});
	}

	private void login() {
		String username = txtUserName.getText().toString();
		String password = txtPassword.getText().toString();
		if (username.length() == 0 || password.length() == 0) {
			Toast.makeText(getApplicationContext(),
					"Username / password can not empty", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		new UpdateTimeAsync(context).execute();
		new LoginAsync(context, getApplication()).execute(username, password);
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
