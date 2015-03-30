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

	public TableView(Context context) {
		super(context);
		mContext = context;

		listRow = new ArrayList<TableRow>();

		setBackgroundColor(Color.WHITE);
		setLayoutParams(new HorizontalScrollView.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		initHeader();

		Order order = new Order();
		order.setQty("1");
		order.setPrintStatus("1");
		order.setItemName("Tino");
		addRow(order);

		Order order1 = new Order();
		order1.setQty("2");
		order.setPrintStatus("2");
		order.setItemName("Toni");
		addRow(order1);

		order = new Order();
		order.setQty("1");
		order.setPrintStatus("1");
		order.setItemName("Tino");
		addRow(order);

		order1 = new Order();
		order1.setQty("2");
		order.setPrintStatus("1");
		order.setItemName("Toni");
		addRow(order1);
	}

	public ArrayList<TableRow> getAllRows() {
		return listRow;
	}
	
	public TableRow getCurrentRow() {
		return currentRow;
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
		}
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
