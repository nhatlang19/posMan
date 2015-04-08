package com.vn.vietatech.posman;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vn.vietatech.api.AbstractAPI;
import com.vn.vietatech.api.OrderAPI;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Order;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.SubMenu;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.adapter.MainMenuAdapter;
import com.vn.vietatech.posman.adapter.SubMenuAdapter;
import com.vn.vietatech.posman.dialog.TransparentProgressDialog;
import com.vn.vietatech.posman.view.ItemRow;
import com.vn.vietatech.posman.view.TableView;
import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class POSMenuActivity extends ActionBarActivity {
	final Context context = this;
	MyApplication globalVariable;

	HorizontalScrollView horizontalView;
	ScrollView scrollView;
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
	MainMenuAdapter posMnuAdapter = null;
	GridView gridSubMenu;
	SubMenuAdapter subMnuAdapter;
	String tableNo;
	String tableStatus;
	TransparentProgressDialog pd;
	Order currentOrder = new Order();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posmenu);

		globalVariable = (MyApplication) getApplicationContext();

		tableNo = getIntent().getExtras().getString(
				TableActivity.KEY_SELECTED_TABLE);
		tableStatus = getIntent().getExtras().getString(
				TableActivity.KEY_STATUS);

		horizontalView = (HorizontalScrollView) findViewById(R.id.horizontalView);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
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
			if (!result) {
				Utils.showAlert(this, "Can not find kit folder on server");
			}
			pd = new TransparentProgressDialog(this, R.drawable.spinner);
			loadItems();
			pd.cancel();

			// load title
			this.setTitle(tableNo.trim() + "(" + tblOrder.getAllRows().size()
					+ ")-" + globalVariable.getCashier().getName());

		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}

		// IPlus click
		btnIPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView txtQty = (TextView) tblOrder.getColumnCurrentRow("Q");
				if (txtQty != null) {
					int qty = Integer.parseInt(txtQty.getText().toString());
					txtQty.setText((qty + 1) + "");
					txtMoney.setText(tblOrder.getAllTotal());
				}
			}
		});

		// ISub click
		btnISub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView txtQty = (TextView) tblOrder.getColumnCurrentRow("Q");
				if (txtQty != null) {
					int qty = Integer.parseInt(txtQty.getText().toString());
					txtQty.setText((qty - 1) + "");
					if (qty - 1 <= 0) {
						ItemRow row = tblOrder.getCurrentRow();
						if (row != null) {
							tblOrder.removeView(row);
						}
					}

					txtMoney.setText(tblOrder.getAllTotal());
				}
			}
		});

		// Ix click
		btnIx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ItemRow row = tblOrder.getCurrentRow();
				if (row != null) {
					tblOrder.removeView(row);
					txtMoney.setText(tblOrder.getAllTotal());
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

		txtPeople.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (posMnuAdapter == null) {
					posMnuAdapter = new MainMenuAdapter(context);
					gridMainMenu.setAdapter(posMnuAdapter);
				}
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
		intent.putExtra(TableActivity.KEY_REFRESH_CODE,
				TableActivity.REFRESH_TABLE);
		intent.putExtra(TableActivity.KEY_SELECTED_TABLE, tableNo);
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	}

	public void loadSubMenu(PosMenu selectedPOSMenu) {
		subMnuAdapter = new SubMenuAdapter(context, selectedPOSMenu);
		gridSubMenu.setAdapter(subMnuAdapter);
	}

	public void addItem(SubMenu selectedSubMenu) {
		try {
			ItemRow newRow = tblOrder.createNewRow(selectedSubMenu.getItem());
			scrollView.fullScroll(ScrollView.FOCUS_DOWN);

			txtMoney.setText(tblOrder.getAllTotal());
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}

	private void getCurrentOrder() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String POSBizDate = sdf.format(new Date());

		currentOrder = new OrderAPI(context).getOrderEditType("20150406",
				tableNo);
	}

	private void loadItems() throws Exception {
		if (tableStatus.equals(Table.ACTION_EDIT)) {
			this.getCurrentOrder();

			String posNo = SettingUtil.read(context).getPosId();
			ArrayList<Item> items = new OrderAPI(context)
					.getEditOrderNumberByPOS(currentOrder.getOrd(), posNo,
							currentOrder.getExt());
			for (Item item : items) {
				tblOrder.createNewRow(item);
			}
			scrollView.fullScroll(ScrollView.FOCUS_DOWN);

			txtMoney.setText(tblOrder.getAllTotal());
			txtPeople.setText(currentOrder.getPer());
		}
	}
}
