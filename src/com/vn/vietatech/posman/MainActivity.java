package com.vn.vietatech.posman;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.vn.vietatech.api.*;
import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.posman.dialog.TransparentProgressDialog;
import com.vn.vietatech.utils.UserUtil;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	private TransparentProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		disableStrictMode();
		
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
		pd = new TransparentProgressDialog(this, R.drawable.spinner);
		pd.show();

		String username = txtUserName.getText().toString();
		String password = txtPassword.getText().toString();
		if (username.length() == 0 || password.length() == 0) {
			Toast.makeText(getApplicationContext(),
					"Username / password can not empty",
					Toast.LENGTH_SHORT).show();
			pd.dismiss();
			return;
		}
		
		try {
			Cashier cashier = new UserApi(getApplicationContext())
					.login(username, password);

			if (cashier.getId().length() != 0) {
				// cache user info
				globalVariable.setCashier(cashier);

				// log recent login
				UserUtil.write(cashier, getApplicationContext());

				new TableAPI(getApplicationContext()).execute();
				new PosMenuAPI(getApplicationContext()).execute();
				
				Intent myIntent = new Intent(MainActivity.this,
						TableActivity.class);
				startActivity(myIntent);
			} else {
				Toast.makeText(getApplicationContext(),
						"Invalid Username / password",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		} finally {
			pd.dismiss();
		}
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
	

	/**
	 * This snippet allows UI on main thread. Normally it's 2 lines but since
	 * we're supporting 2.x, we need to reflect.
	 */
	private void disableStrictMode() {
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder().permitAll().build();
		// StrictMode.setThreadPolicy(policy);

		try {
			Class<?> strictModeClass = Class.forName("android.os.StrictMode",
					true, Thread.currentThread().getContextClassLoader());
			Class<?> threadPolicyClass = Class.forName(
					"android.os.StrictMode$ThreadPolicy", true, Thread
							.currentThread().getContextClassLoader());
			Class<?> threadPolicyBuilderClass = Class.forName(
					"android.os.StrictMode$ThreadPolicy$Builder", true, Thread
							.currentThread().getContextClassLoader());

			Method setThreadPolicyMethod = strictModeClass.getMethod(
					"setThreadPolicy", threadPolicyClass);

			Method detectAllMethod = threadPolicyBuilderClass
					.getMethod("detectAll");
			Method penaltyMethod = threadPolicyBuilderClass
					.getMethod("penaltyLog");
			Method buildMethod = threadPolicyBuilderClass.getMethod("build");

			Constructor<?> threadPolicyBuilderConstructor = threadPolicyBuilderClass
					.getConstructor();
			Object threadPolicyBuilderObject = threadPolicyBuilderConstructor
					.newInstance();

			Object obj = detectAllMethod.invoke(threadPolicyBuilderObject);

			obj = penaltyMethod.invoke(obj);
			Object threadPolicyObject = buildMethod.invoke(obj);
			setThreadPolicyMethod.invoke(strictModeClass, threadPolicyObject);
		} catch (Exception ex) {
			Log.w("disableStrictMode", ex);
		}
	}
}
