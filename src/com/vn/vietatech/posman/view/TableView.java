package com.vn.vietatech.posman.view;

import java.util.ArrayList;

import com.vn.vietatech.model.Order;
import com.vn.vietatech.posman.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableView extends TableLayout {
	private String[] headers = new String[] { "Q", "P", "ItemName", "Price",
			"Total", "ItemType", "ItemCode", "ModifierInt", "MasterCode",
			"ComboClass", "Hidden", "Instruction" };

	private Context mContext;
	private ArrayList<TableRow> listRow;
	private TableRow currentRow = null;
	private ArrayList<String> listOrder;
	
	private ArrayList<String> arrHeader = new ArrayList<String>();

	public TableView(Context context, HorizontalScrollView parent) {
		super(context);
		mContext = context;

		listRow = new ArrayList<TableRow>();
		listOrder = new ArrayList<String>();
		
		setLayoutParams(parent.getLayoutParams());

		initHeader();
	}

	public ArrayList<TableRow> getAllRows() {
		return listRow;
	}
	
	public TableRow getCurrentRow() {
		return currentRow;
	}
	
	public ArrayList<String> getListOrder() {
		return listOrder;
	}

	public void setCurrentRow(TableRow currentRow) {
		this.currentRow = currentRow;
	}

	private void initHeader() {
		TableRow tblHeader = new TableRow(mContext);
		tblHeader.setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		for (String header : headers) {
			TextView textView = new TextView(mContext);
			textView.setBackgroundResource(R.drawable.order_table_header);
			textView.setText(header);
			textView.setTextColor(Color.BLACK);
			tblHeader.addView(textView);
			
			arrHeader.add(header);
		}

		this.addView(tblHeader);
	}

	public void addRow(Order order) {
		if (order != null) {
			// add new row
			final TableRow newRow = new TableRow(mContext);
			newRow.setId(listRow.size());
			newRow.setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			newRow.setPadding(0, 5, 0, 5);
			// quality
			TextView txtQuality = createColumn(order.getQty());
			newRow.addView(txtQuality);

			// status
			TextView txtStatus = createColumn(order.getPrintStatus());
			newRow.addView(txtStatus);

			// name
			TextView txtName = createColumn(order.getItemName());
			newRow.addView(txtName);

			newRow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// set other row : #ffffff
					clearBgRow();

					newRow.setBackgroundColor(Color.parseColor("#edf0fe"));

					// set current row
					setCurrentRow(newRow);
				}
			});

			// add into table
			this.addView(newRow);

			// add into array list
			listRow.add(newRow);
			listOrder.add(order.getId());
		}
	}
	
	public Object getColumnCurrentRow(String name) {
		int index = arrHeader.indexOf(name);
		if(index != -1) {
			return getCurrentRow().getChildAt(index);
		}
		return null;
	}
	
	public Object getColumnByRow(int index, String name) {
		int indexCol = arrHeader.indexOf(name);
		if(indexCol != -1) {
			return listRow.get(index).getChildAt(indexCol);
		}
		return null;
	}

	private TextView createColumn(String item) {
		TextView textView = new TextView(mContext);
		textView.setPadding(10, 5, 10, 5);
		textView.setText(item);
		textView.setTextColor(Color.BLACK);

		return textView;
	}

	private void clearBgRow() {
		for (TableRow row : listRow) {
			row.setBackgroundColor(Color.parseColor("#ffffff"));
		}
	}

	@Override
	public void removeView(View view) {
		super.removeView(view);
		
		TableRow deletedRow = (TableRow) view;
		for (int i = listRow.size() - 1; i >= 0; i--) {
			TableRow row = listRow.get(i);
			if(row.getId() == deletedRow.getId()) {
				listRow.remove(row);
			}
		}
		view = null;
	}
}
