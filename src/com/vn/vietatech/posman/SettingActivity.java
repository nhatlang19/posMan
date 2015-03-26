package com.vn.vietatech.posman;

import com.vn.vietatech.model.Setting;
import com.vn.vietatech.utils.SettingUtil;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends ActionBarActivity {

	private TextView txtServerIp;
	private TextView txtDatabase;
	private TextView txtUser;
	private TextView txtPass;
	private TextView txtStoreNo;
	private TextView txtPosGroup;
	private TextView txtPosId;
	private TextView txtSubMenu;
	private Button btnSaveConfig;
	private Button btnTestConnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		txtServerIp = (TextView) findViewById(R.id.txtServerIP);
		txtDatabase = (TextView) findViewById(R.id.txtDatabase);
		txtUser = (TextView) findViewById(R.id.txtUser);
		txtPass = (TextView) findViewById(R.id.txtPass);
		txtStoreNo = (TextView) findViewById(R.id.txtStoreNo);
		txtPosGroup = (TextView) findViewById(R.id.txtPosGroup);
		txtPosId = (TextView) findViewById(R.id.txtPosId);
		txtSubMenu = (TextView) findViewById(R.id.txtSubMenu);

		btnSaveConfig = (Button) findViewById(R.id.btnSaveConfig);
		btnTestConnect = (Button) findViewById(R.id.btnTestConnect);

		// load settings
		loadSettings();

		btnSaveConfig.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Setting setting = new Setting();
				setting.setServerIP(txtServerIp.getText().toString());
				setting.setDatabase(txtDatabase.getText().toString());
				setting.setUser(txtUser.getText().toString());
				setting.setPass(txtPass.getText().toString());
				setting.setStoreNo(txtStoreNo.getText().toString());
				setting.setPosGroup(txtPosGroup.getText().toString());
				setting.setPosId(txtPosId.getText().toString());
				setting.setSubMenu(txtSubMenu.getText().toString());

				SettingUtil.write(setting, getApplicationContext());

				Toast.makeText(getApplicationContext(),
						"Save config successful", Toast.LENGTH_LONG).show();
			}
		});

		btnTestConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

	}

	private void loadSettings() {
		Setting setting = SettingUtil.read(getApplicationContext());
		if (setting != null) {
			txtServerIp.setText(setting.getServerIP());
			txtDatabase.setText(setting.getDatabase());
			txtUser.setText(setting.getUser());
			txtPass.setText(setting.getPass());
			txtStoreNo.setText(setting.getStoreNo());
			txtPosGroup.setText(setting.getPosGroup());
			txtPosId.setText(setting.getPosId());
			txtSubMenu.setText(setting.getSubMenu());
		}
	}
}
