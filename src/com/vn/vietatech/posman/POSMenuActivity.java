package com.vn.vietatech.posman;

import java.util.ArrayList;
import java.util.HashMap;

import com.vn.vietatech.api.AbstractAPI;
import com.vn.vietatech.api.OrderAPI;
import com.vn.vietatech.api.TableAPI;
import com.vn.vietatech.api.async.TableMoveAsync;
import com.vn.vietatech.api.async.TableRowAsync;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Order;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.Remark;
import com.vn.vietatech.model.SubMenu;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.adapter.MainMenuAdapter;
import com.vn.vietatech.posman.adapter.RemarkAdapter;
import com.vn.vietatech.posman.adapter.SubMenuAdapter;
import com.vn.vietatech.posman.adapter.TableListAdapter;
import com.vn.vietatech.posman.dialog.DialogConfirm;
import com.vn.vietatech.posman.view.ItemRow;
import com.vn.vietatech.posman.view.TableOrder;
import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class POSMenuActivity extends ActionBarActivity {
	final Context context = this;
	MyApplication globalVariable;

	HorizontalScrollView horizontalView;
	LinearLayout ll_main, MTLayout, parentView;
	ScrollView vBody;
	TableOrder tblOrder;
	Button btnIPlus, btnIR, btnISub;
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
	Spinner spinRemark;
	EditText txtRemark;
	Order currentOrder = new Order();
	Spinner spinTableListMT;
	Remark selectedRemark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posmenu);

		initControl();
		
		// load table list to move
		new TableMoveAsync(context).execute();
		
		loadEvent();

		try {
			boolean result = new AbstractAPI(this).isKitFolderExist();
			if (!result) {
				Utils.showAlert(this, "Can not find kit folder");
			}
			loadItems();

		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra(TableActivity.KEY_REFRESH_CODE,
				TableActivity.REFRESH_TABLE);
		intent.putExtra(TableActivity.KEY_SELECTED_TABLE, tableNo);
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	}
	
	private void initControl() {
		globalVariable = (MyApplication) getApplicationContext();
		tableNo = getIntent().getExtras().getString(
				TableActivity.KEY_SELECTED_TABLE);
		tableStatus = getIntent().getExtras().getString(
				TableActivity.KEY_STATUS);

		horizontalView = (HorizontalScrollView) findViewById(R.id.horizontalView);
		parentView = (LinearLayout) findViewById(R.id.parentView);
		ll_main = (LinearLayout) findViewById(R.id.ll_main);
		MTLayout = (LinearLayout) findViewById(R.id.MTLayout);
		vBody = (ScrollView) findViewById(R.id.vBody);
		btnIPlus = (Button) findViewById(R.id.btnIPlus);
		btnISub = (Button) findViewById(R.id.btnISub);
		btnIx = (Button) findViewById(R.id.btnIx);
		btnMT = (Button) findViewById(R.id.btnMT);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnIR = (Button) findViewById(R.id.btnIR);
		btnX = (Button) findViewById(R.id.btnX);
		btnX.setWidth(btnIPlus.getWidth());
		txtPeople = (EditText) findViewById(R.id.txtPeople);
		txtMoney = (EditText) findViewById(R.id.txtMoney);
		gridMainMenu = (GridView) findViewById(R.id.gridMainMenu);
		gridSubMenu = (GridView) findViewById(R.id.gridSubMenu);
		spinRemark = (Spinner) findViewById(R.id.spinRemark);
		txtRemark = (EditText) findViewById(R.id.txtRemark);
		spinTableListMT = (Spinner) findViewById(R.id.spinTableListMT);

		tblOrder = new TableOrder(this, ll_main);

		ll_main.addView(tblOrder.getTable().getHeader(), 0);
		vBody.addView(tblOrder.getTable().getBody());
	}
	
	private void loadEvent() {
		spinRemark.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedRemark = (Remark) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				selectedRemark = null;
			}
		});

		// IPlus click
		btnIPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tblOrder.plus();
				txtMoney.setText(tblOrder.getAllTotal());
			}
		});

		// ISub click
		btnISub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tblOrder.sub();
				txtMoney.setText(tblOrder.getAllTotal());
			}
		});

		// Ix click
		btnIx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tblOrder.removeRow();
				txtMoney.setText(tblOrder.getAllTotal());
			}
		});

		// insert remark
		btnIR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView txtStatus = (TextView) tblOrder
						.getColumnCurrentRow("P");
				if (txtStatus != null && !txtStatus.getText().equals("#")) {
					if (selectedRemark != null) {
						TextView txtInstruction = (TextView) tblOrder
								.getColumnCurrentRow("Instruction");
						if (txtInstruction != null) {
							String instruction = txtInstruction.getText()
									.toString();
							if (instruction.length() != 0) {
								instruction = instruction + ";"
										+ selectedRemark.getName();
							} else {
								instruction = selectedRemark.getName();
							}
							txtInstruction.setText(instruction);
							txtRemark.setText(instruction);
							
							tblOrder.getCurrentRow().getCurrentItem().setInstruction(instruction);
						}
					}
				}
			}
		});

		btnMT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadMoveTableForm();
			}

		});

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String status = tblOrder.checkStatus(tableStatus);
				if(status == null) {
					send(status);
				} else {
					if(status.equals(TableOrder.STATUS_DATATABLE_NO_DATA)) {
						Utils.showAlert(context, status);
					} else if(status.equals(TableOrder.STATUS_DATATABLE_SEND_ALL)
							|| status.equals(TableOrder.STATUS_DATATABLE_RESEND)) {
						new DialogConfirm(context, status) {
							public void run() {
								send(status);
							}
						};
					}
				}
			}

		});

		btnX.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DialogConfirm(context, "are you sure?") {
					public void run() {
						backForm();
					}
				};
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
				loadPickerDialog();
			}
		});
	}

	public void loadSubMenu(PosMenu selectedPOSMenu) {
		subMnuAdapter = new SubMenuAdapter(context, selectedPOSMenu);
		gridSubMenu.setAdapter(subMnuAdapter);
	}

	public void addItem(SubMenu selectedSubMenu) {
		try {
			tblOrder.createNewRow(selectedSubMenu.getItem());
			vBody.fullScroll(ScrollView.FOCUS_DOWN);

			txtMoney.setText(tblOrder.getAllTotal());
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * load Items
	 * 
	 * load all item rows
	 * @throws Exception
	 */
	private void loadItems() throws Exception {
		if (tableStatus.equals(Table.ACTION_EDIT)) {
			new TableRowAsync(context).execute(tableNo);
		} else {
			// open form set people
			txtPeople.performClick();
			updateTitle();
		}
	}

	/**
	 * update Form title
	 */
	private void updateTitle() {
		setTitle(tableNo.trim() + "(" + tblOrder.getAllRows().size() + ")-"
				+ globalVariable.getCashier().getName());
	}

	/**
	 * load number picker when set people
	 */
	private void loadPickerDialog() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View promptView = layoutInflater.inflate(R.layout.number_picker_dialog,
				null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		final NumberPicker np = (NumberPicker) promptView
				.findViewById(R.id.npPeople);
		np.setMaxValue(30);
		np.setMinValue(1);
		if (txtPeople.getText().toString().length() != 0) {
			np.setValue(Integer.parseInt(txtPeople.getText().toString()));
		}

		alertDialogBuilder.setView(promptView);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setTitle("Set people");
		alertDialogBuilder.setPositiveButton("Set",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						txtPeople.setText(String.valueOf(np.getValue()));
						dialog.cancel();
					}
				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	//##### ZONE MOVE TABLE #####///
	private void restoreGrid() {
		gridMainMenu.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 300));
		gridSubMenu.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		MTLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0));
	}
	
	private void loadMoveTableForm() {
		MTLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 200));
		gridMainMenu.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
		gridSubMenu.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
		
		Button btnOkMT = (Button) findViewById(R.id.btnOkMT);
		Button btnCancelMT = (Button) findViewById(R.id.btnCancelMT);
		btnOkMT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Table table = (Table) spinTableListMT.getItemAtPosition(spinTableListMT.getSelectedItemPosition());
				String moveTable = table.getTableNo();
				if (moveTable.isEmpty() || moveTable.equals(tableNo)) {
					Utils.showAlert(context, "This is current table");
					return;
				}

				HashMap<String, String> result;
				try {
					result = new TableAPI(context)
							.getStatusOfMoveTable(moveTable);

					moveTable = moveTable.trim();
					if (result.containsKey("Status")
							&& result.containsKey("OpenBy")) {
						String status = result.get("Status").toString();
						String openBy = result.get("OpenBy").toString();
						switch (status) {
						case "O":
							if (openBy.isEmpty()) {
								Utils.showAlert(context, "Table " + moveTable + " is having guests");
							} else {
								Utils.showAlert(context,"Table " + moveTable + " is having guests and editting by cashier ("
												+ openBy + ")");
							}
							break;
						case "A":
							// TODO: move table
							tableNo = moveTable;
							updateTitle();
							restoreGrid();
							break;
						}
					} else {
						Toast.makeText(context, "Can not move table",Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnCancelMT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				restoreGrid();
			}
		});
	}
	
	public void loadLayoutMoveTable() {
		// layout move table
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(2, Color.BLACK);
		drawable.setColor(Color.parseColor("#dddddd"));
		MTLayout.setBackgroundDrawable(drawable);
		
		ArrayList<Table> tableList = globalVariable.getTables();
		if (tableList != null) {
			TableListAdapter tableListAdapter = new TableListAdapter(context,
					android.R.layout.simple_spinner_item, tableList);
			
			spinTableListMT.setAdapter(tableListAdapter);
		}
	}
	//##### ZONE MOVE TABLE #####///

	private void send(String status) {
		// send to kitchen
		System.out.println(tblOrder.toString());
		
		if(status == null) {
			
		} else {
			if(status.equals(TableOrder.STATUS_DATATABLE_SEND_ALL)) {
				
			} else if(status.equals(TableOrder.STATUS_DATATABLE_RESEND)) {
				
			}
		}
		
		// close form
		backForm();
	}
	
	public void backForm() {
		Intent intent = new Intent();
		intent.putExtra(TableActivity.KEY_REFRESH_CODE, 1);
		intent.putExtra(TableActivity.KEY_SELECTED_TABLE,
				tableNo);
		setResult(RESULT_OK, intent);
		finish();
	}

	/**
	 * using in background
	 * @param result
	 * @throws Exception
	 */
	public void addRowByOrder(Order result) throws Exception {
		String posNo = SettingUtil.read(context).getPosId();

		ArrayList<Item> items = new OrderAPI(context).getEditOrderNumberByPOS(
				result.getOrd(), posNo, result.getExt());
		for (Item item : items) {
			tblOrder.createNewRow(item);
		}
		vBody.fullScroll(ScrollView.FOCUS_DOWN);

		txtMoney.setText(tblOrder.getAllTotal());
		txtPeople.setText(result.getPer());

		// update title
		updateTitle();
	}

	public void loadRemarks(Item item) {
		RemarkAdapter remarkAdapter = new RemarkAdapter(context,
				android.R.layout.simple_spinner_item, item);
		spinRemark.setAdapter(remarkAdapter);
		spinRemark.setBackgroundResource(R.drawable.spinner_background_remark_with_data);
		
		txtRemark.setText(item.getInstruction());
	}
}
