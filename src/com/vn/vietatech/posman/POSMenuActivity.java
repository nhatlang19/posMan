package com.vn.vietatech.posman;

import com.vn.vietatech.posman.view.TableView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

public class POSMenuActivity extends ActionBarActivity {
	HorizontalScrollView horizontalView;
	TableView tblOrder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posmenu);
		
		horizontalView = (HorizontalScrollView)findViewById(R.id.horizontalView);
		
		tblOrder = new TableView(getApplicationContext());
		horizontalView.addView(tblOrder);
	}
}
