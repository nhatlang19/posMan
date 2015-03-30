package com.vn.vietatech.posman;

import com.vn.vietatech.posman.view.TableView;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableRow;
import android.widget.TextView;

public class POSMenuActivity extends ActionBarActivity {
	protected static final int REFRESH_TABLE = 1;
	
	HorizontalScrollView horizontalView;
	TableView tblOrder;
	Button btnIPlus;
	Button btnISub;
	Button btnIx;
	Button btnMT;
	Button btnSend;
	Button btnX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posmenu);
		
		horizontalView = (HorizontalScrollView) findViewById(R.id.horizontalView);
		btnIPlus = (Button) findViewById(R.id.btnIPlus);
		btnISub = (Button) findViewById(R.id.btnISub);
		btnIx = (Button) findViewById(R.id.btnIx);
		btnMT = (Button) findViewById(R.id.btnMT);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnX = (Button) findViewById(R.id.btnX);

		tblOrder = new TableView(getApplicationContext());
		horizontalView.addView(tblOrder);

		// IPlus click
		btnIPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TableRow currentRow = tblOrder.getCurrentRow();
				if (currentRow != null) {
					TextView txtQty = (TextView) currentRow.getChildAt(0);
					int qty = Integer.parseInt(txtQty.getText().toString());
					txtQty.setText((qty + 1) + "");
				}
			}
		});

		// ISub click
		btnISub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TableRow currentRow = tblOrder.getCurrentRow();
				if (currentRow != null) {
					TextView txtQty = (TextView) currentRow.getChildAt(0);
					int qty = Integer.parseInt(txtQty.getText().toString());
					txtQty.setText((qty - 1) + "");
					if (qty - 1 <= 0) {
						tblOrder.removeView(currentRow);
					}
				}
			}
		});

		// Ix click
		btnIx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TableRow currentRow = tblOrder.getCurrentRow();
				if (currentRow != null) {
					tblOrder.removeView(currentRow);
				}
			}
		});
		
		btnMT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
			
		});
		
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
			
		});
		
		btnX.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("refresh_code", REFRESH_TABLE);
				setResult(RESULT_OK, intent);
				finish();
			}
			
		});
	}
}
