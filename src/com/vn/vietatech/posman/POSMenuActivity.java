package com.vn.vietatech.posman;

import java.util.ArrayList;
import java.util.HashMap;

import com.vn.vietatech.api.AbstractAPI;
import com.vn.vietatech.model.Order;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.SubMenu;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.adapter.MainMenuAdapter;
import com.vn.vietatech.posman.adapter.SubMenuAdapter;
import com.vn.vietatech.posman.adapter.TableAdapter;
import com.vn.vietatech.posman.view.TableView;
import com.vn.vietatech.utils.Utils;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class POSMenuActivity extends ActionBarActivity {
	protected static final int REFRESH_TABLE = 1;
	
	protected static final String KEY_SELECTED_TABLE = "selectedTable";
	protected static final String KEY_REFRESH_CODE = "refresh_code";

	final Context context = this;

	HorizontalScrollView horizontalView;
	TableView tblOrder;
	Button btnIPlus;
	Button btnISub;
	Button btnIx;
	Button btnMT;
	Button btnSend;
	Button btnX;
	EditText txtPeople;
	EditText txtMoney;
	GridView gridMainMenu;
	MainMenuAdapter posMnuAdapter;
	
	GridView gridSubMenu;
	SubMenuAdapter subMnuAdapter;
	
	String tableNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posmenu);
		
		tableNo =  getIntent().getExtras().getString(KEY_SELECTED_TABLE);

		horizontalView = (HorizontalScrollView) findViewById(R.id.horizontalView);
		btnIPlus = (Button) findViewById(R.id.btnIPlus);
		btnISub = (Button) findViewById(R.id.btnISub);
		btnIx = (Button) findViewById(R.id.btnIx);
		btnMT = (Button) findViewById(R.id.btnMT);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnX = (Button) findViewById(R.id.btnX);
		btnX.setWidth(btnIPlus.getWidth());
		txtPeople = (EditText) findViewById(R.id.txtPeople);
		txtMoney = (EditText) findViewById(R.id.txtMoney);
		gridMainMenu = (GridView) findViewById(R.id.gridMainMenu);
		gridSubMenu = (GridView) findViewById(R.id.gridSubMenu);

		tblOrder = new TableView(getApplicationContext(), horizontalView);
		horizontalView.addView(tblOrder);
		
		try {
			boolean result = new AbstractAPI(this).isKitFolderExist();
			if(!result) {
				Utils.showAlert(this, "Can not find kit folder on server");
			}
		} catch (Exception e)  {
			Toast.makeText(this, e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

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
				backAction();
			}

		});

		// txtPeople click
		txtPeople.setClickable(true);
		txtPeople.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				View promptView = layoutInflater.inflate(
						R.layout.number_picker_dialog, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				final NumberPicker np = (NumberPicker) promptView
						.findViewById(R.id.npPeople);
				np.setMaxValue(30);
				np.setMinValue(1);
				if (txtPeople.getText().toString().length() != 0) {
					np.setValue(Integer
							.parseInt(txtPeople.getText().toString()));
				}

				alertDialogBuilder.setView(promptView);

				alertDialogBuilder.setTitle("Set people");
				alertDialogBuilder.setPositiveButton("Set",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								txtPeople.setText(String.valueOf(np.getValue()));
								posMnuAdapter = new MainMenuAdapter(context);
								gridMainMenu.setAdapter(posMnuAdapter);

								dialog.cancel();
							}
						}).setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				AlertDialog alertD = alertDialogBuilder.create();
				alertD.show();
			}
		});
	}
	
	 @Override
	public void onBackPressed() {
		 backAction();
	}
	 
	 private void backAction() {
		Intent intent = new Intent();
		intent.putExtra(KEY_REFRESH_CODE, REFRESH_TABLE);
		intent.putExtra(KEY_SELECTED_TABLE, tableNo);
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	 }
	 
	 public void loadSubMenu(PosMenu selectedPOSMenu) {
		subMnuAdapter = new SubMenuAdapter(context,  selectedPOSMenu);
		gridSubMenu.setAdapter(subMnuAdapter);
	 }
	 
	 private HashMap<String, Integer> orderIds = new HashMap<String, Integer>();
	
	 public void setOrder(SubMenu selectedSubMenu) {
		 String key = selectedSubMenu.getDefaultValue();
		 if(orderIds.containsKey(key)) {
			 int number = orderIds.get(key);
			 orderIds.put(key, number + 1);
			 
			 tblOrder.getColumnByRow();
		 } else {
			 Order order = new Order();
			 tblOrder.addRow(order);
		 }
	 }
}
