package com.vn.vietatech.posman;

import java.util.ArrayList;

import com.vn.vietatech.api.SectionAPI;
import com.vn.vietatech.api.TableAPI;
import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.adapter.SectionAdapter;
import com.vn.vietatech.posman.adapter.TableAdapter;
import com.vn.vietatech.posman.dialog.TransparentProgressDialog;
import com.vn.vietatech.utils.Utils;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.Toast;

public class TableActivity extends ActionBarActivity implements
		OnItemSelectedListener {
	public static final int REFRESH_TABLE = 1;
	
	public static final String KEY_SELECTED_TABLE = "selectedTable";
	public static final String KEY_REFRESH_CODE = "refresh_code";
	public static final String KEY_STATUS = "statusTable";
	
	
	private static final int TIMER_LIMIT = 10000; // 10 seconds
	private Spinner spin;
	private GridView gridview;
	private ArrayList<Section> sections;
	private Section selectedSection;
	private SectionAdapter sectionAdapter;
	private TableAdapter tableAdapter = null;
	private Handler handler;
	private Runnable runnable;
	private boolean startDelay = false;
	private TransparentProgressDialog pd;
	
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);

		Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
		Button btnClose = (Button) findViewById(R.id.btnClose);

		pd = new TransparentProgressDialog(this, R.drawable.spinner);
		gridview = (GridView) findViewById(R.id.gridMainMenu);
		spin = (Spinner) findViewById(R.id.spinSession);
		loadSections();

		final MyApplication globalVariable = (MyApplication) getApplicationContext();
		this.setTitle(globalVariable.getCashier().getName());

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

	private void loadSections() {
		spin.setOnItemSelectedListener(this);

		final MyApplication globalVariable = (MyApplication) getApplicationContext();
		sections = globalVariable.getSections();
		if (sections == null) {
			try {
				sections = new SectionAPI(getApplicationContext()).getSection();
				globalVariable.setSections(sections);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}

		sectionAdapter = new SectionAdapter(this,
				android.R.layout.simple_spinner_item, sections);
		spin.setAdapter(sectionAdapter);

		if (sectionAdapter.getCount() != 0) {
			spin.setSelection(0);
		}
	}

	public void myStartActivity(Table selectedTable, boolean isAddNew) {
		Intent myIntent = new Intent(this, POSMenuActivity.class);
		myIntent.putExtra(KEY_SELECTED_TABLE, selectedTable.getTableNo());
		if(isAddNew) {
			myIntent.putExtra(KEY_STATUS, Table.ACTION_ADD);
		} else {
			myIntent.putExtra(KEY_STATUS, Table.ACTION_EDIT);
		}
		startActivityForResult(myIntent, REFRESH_TABLE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		int refresh_code = data.getIntExtra(KEY_REFRESH_CODE, REFRESH_TABLE);
		String tableNo =  data.getExtras().getString(KEY_SELECTED_TABLE);
		if(resultCode == RESULT_OK &&refresh_code == REFRESH_TABLE) {
			refresh();
			
			MyApplication globalVariable = (MyApplication) getApplicationContext();
			Cashier cashier = globalVariable.getCashier();
			try {
				boolean result = new TableAPI(context).updateTableStatus(Table.STATUS_CLOSE, cashier.getId(), tableNo);
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
				.show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * refresh tables
	 */
	public void refresh() {
		pd.show();
		gridview.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		tableAdapter = new TableAdapter(this, selectedSection);
		// load all pages
		gridview.setAdapter(tableAdapter);

		if (!startDelay) {
			handler.postDelayed(this.runnable, TIMER_LIMIT);
		}
		startDelay = true;
		pd.dismiss();
		return;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		selectedSection = sectionAdapter.getItem(position);
		refresh();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
