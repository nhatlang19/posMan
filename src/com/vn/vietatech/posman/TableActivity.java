package com.vn.vietatech.posman;

import com.vn.vietatech.adapter.SessionAdapter;
import com.vn.vietatech.adapter.TableAdapter;
import com.vn.vietatech.dialog.TransparentProgressDialog;
import com.vn.vietatech.model.Session;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class TableActivity extends ActionBarActivity implements OnItemSelectedListener {
	private static final int TIMER_LIMIT = 10000; // 10 seconds
	private Spinner spin;
	private GridView gridview;
	private Session[] sessions;
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
//				pd.show();
				showOrderForm();
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
		switch(event.getAction()) {
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if(!startDelay) {
					handler.postDelayed(this.runnable, TIMER_LIMIT);
				}
				startDelay = true;
				break;
			default:
				if(startDelay) {
					handler.removeCallbacks(this.runnable);
				}
				startDelay = false;
				break;
		}
		return super.onTouchEvent(event);
	}
	
	private void showOrderForm() {
		// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(this);

		View promptView = layoutInflater.inflate(R.layout.order_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set prompts.xml to be the layout file of the alertdialog builder
		alertDialogBuilder.setView(promptView);

//
//		// setup a dialog window
//		alertDialogBuilder
//				.setCancelable(false)
//				
//				.setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int id) {
//							}
//						})
//				.setNegativeButton("Cancel",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,	int id) {
//							}
//						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();

		alertD.show();
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
		if(sessions == null) {
			sessions = new Session[2];

			sessions[0] = new Session();
			sessions[0].setId(1);
			sessions[0].setName("Joaquin");

			sessions[1] = new Session();
			sessions[1].setId(2);
			sessions[1].setName("Alberto");
			
			globalVariable.setSessions(sessions);
		}
		
		sessionAdapter = new SessionAdapter(this
				, android.R.layout.simple_spinner_item, sessions);
		spin.setAdapter(sessionAdapter);
		
		if(sessionAdapter.getCount() != 0) {
			spin.setSelection(0); 
		}
	}

	/**
	 * refresh tables
	 */
	public void refresh() {
		startDelay = true;
		handler.postDelayed(this.runnable, TIMER_LIMIT);
		
		gridview.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		tableAdapter = new TableAdapter(this, selectedSession);
    	// load all pages
		gridview.setAdapter(tableAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(getApplicationContext(), position,
						Toast.LENGTH_SHORT).show();
			}
		});
		
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
