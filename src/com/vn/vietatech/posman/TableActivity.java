package com.vn.vietatech.posman;

import java.util.ArrayList;

import com.vn.vietatech.model.Session;
import com.vn.vietatech.posman.adapter.SessionAdapter;
import com.vn.vietatech.posman.adapter.TableAdapter;
import com.vn.vietatech.posman.dialog.TransparentProgressDialog;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class TableActivity extends ActionBarActivity implements
		OnItemSelectedListener {
	public static final int REFRESH_TABLE = 1;
	private static final int TIMER_LIMIT = 10000; // 10 seconds
	private Spinner spin;
	private GridView gridview;
	private ArrayList<Session> sessions;
	private Session selectedSession;
	private SessionAdapter sessionAdapter;
	private TableAdapter tableAdapter = null;
	private Handler handler;
	private Runnable runnable;
	private boolean startDelay = false;
	private TransparentProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);

		Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
		Button btnClose = (Button) findViewById(R.id.btnClose);

		pd = new TransparentProgressDialog(this, R.drawable.spinner);
		gridview = (GridView) findViewById(R.id.gridview);
		spin = (Spinner) findViewById(R.id.spinSession);
		this.loadSessions();

		this.setTitle("Admin02");

		// close
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		// refresh tables
		btnRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				refresh();
			}
		});

		initShowWaitScreen();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (!startDelay) {
				handler.postDelayed(this.runnable, TIMER_LIMIT);
			}
			startDelay = true;
			break;
		default:
			if (startDelay) {
				handler.removeCallbacks(this.runnable);
			}
			startDelay = false;
			break;
		}
		return super.onTouchEvent(event);
	}

	private void initShowWaitScreen() {
		runnable = new Runnable() {
			public void run() {
				gridview.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
			}
		};
		handler = new Handler();
		startDelay = true;
		handler.postDelayed(this.runnable, TIMER_LIMIT);
	}

	private void loadSessions() {
		spin.setOnItemSelectedListener(this);

		final MyApplication globalVariable = (MyApplication) getApplicationContext();
		sessions = globalVariable.getSessions();
		if (sessions == null) {
			sessions = new ArrayList<Session>();

			Session session = new Session();
			session.setId(1);
			session.setName("Joaquin");
			sessions.add(session);

			session = new Session();
			session.setId(2);
			session.setName("Toni");
			sessions.add(session);

			globalVariable.setSessions(sessions);
		}

		sessionAdapter = new SessionAdapter(this,
				android.R.layout.simple_spinner_item, sessions);
		spin.setAdapter(sessionAdapter);

		if (sessionAdapter.getCount() != 0) {
			spin.setSelection(0);
		}
	}

	public void myStartActivity() {
		Intent myIntent = new Intent(this, POSMenuActivity.class);
		startActivityForResult(myIntent, REFRESH_TABLE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		int refresh_code = data.getIntExtra("refresh_code", REFRESH_TABLE);
		if(resultCode == RESULT_OK &&refresh_code == REFRESH_TABLE) {
			refresh();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * refresh tables
	 */
	public void refresh() {
		gridview.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		tableAdapter = new TableAdapter(this, selectedSession);
		// load all pages
		gridview.setAdapter(tableAdapter);

		if (!startDelay) {
			handler.postDelayed(this.runnable, TIMER_LIMIT);
		}
		startDelay = true;

		return;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		selectedSession = sessionAdapter.getItem(position);
		refresh();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
