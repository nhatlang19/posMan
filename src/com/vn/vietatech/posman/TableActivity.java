package com.vn.vietatech.posman;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class TableActivity extends ActionBarActivity {
	private static final int TIMER_LIMIT = 10000; // 10 seconds
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);

		Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
		Button btnClose = (Button) findViewById(R.id.btnClose);

		this.setTitle("Admin02");
		
		refresh();

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
		
		autoCloseAfter10Seconds();
	}
	
	private void autoCloseAfter10Seconds() {
		Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, TIMER_LIMIT);
	}

	/**
	 * refresh tables
	 */
	public void refresh() {
		// TODO: load all pages
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new TableAdapter(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(TableActivity.this, "" + position,
						Toast.LENGTH_SHORT).show();
			}
		});
		return;
	}
}
