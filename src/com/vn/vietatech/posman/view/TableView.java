package com.vn.vietatech.posman.view;

import java.util.ArrayList;

import com.vn.vietatech.model.Item;
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
	private ArrayList<ItemRow> listRow;
	private int currentIndex = -1;

	private ArrayList<String> arrHeader = new ArrayList<String>();

	public TableView(Context context, HorizontalScrollView parent) {
		super(context);
		mContext = context;

		listRow = new ArrayList<ItemRow>();

		setLayoutParams(parent.getLayoutParams());

		initHeader();
	}

	public ArrayList<ItemRow> getAllRows() {
		return listRow;
	}

	public ItemRow getCurrentRow() {
		if(currentIndex != -1) {
			return listRow.get(currentIndex);
		}
		return null;
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

	public ItemRow addRow(Item item) {
		if (item != null) {
			// add new row
			final ItemRow newRow = new ItemRow(mContext);
			newRow.addAllColumns(item);
			newRow.setId(listRow.size());
			newRow.setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			newRow.setPadding(0, 5, 0, 5);
			newRow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// set other row : #ffffff
					clearBgRow();

					newRow.setBackgroundColor(Color.parseColor("#edf0fe"));

					
					for (int i = listRow.size() - 1; i >= 0; i--) {
						ItemRow row = listRow.get(i);
						if(row.getId() == newRow.getId()) {
							setCurrentIndex(i);
							break;
						}
					}
					// set current row
					
				}
			});

			// add into table
			this.addView(newRow);

			// add into array list
			listRow.add(newRow);
			
			// update list id
			return newRow;
		}
		
		return null;
	}
	
	public ItemRow createNewRow(Item item) {
		int index = -1;
		for (int i = listRow.size() - 1; i >= 0; i--) {
			ItemRow row = listRow.get(i);
			if(row.getCurrentItem().getItemCode().equals(item.getItemCode())) {
				index = i;
				break;
			}
		}
		if(index != -1) {
			// update quality
			TextView txtQ = (TextView)getColumnByRow(index, "Q");
			int q = Integer.parseInt(txtQ.getText().toString()) + 1;
			txtQ.setText(String.valueOf(q));
			
			return listRow.get(index);
		} 
		
		return this.addRow(item);
	}

	public Object getColumnCurrentRow(String name) {
		int index = arrHeader.indexOf(name);
		if (index != -1 && currentIndex != -1) {
			return listRow.get(currentIndex).getChildAt(index);
		}
		return null;
	}

	public Object getColumnByRow(int index, String name) {
		int indexCol = arrHeader.indexOf(name);
		if (indexCol != -1 && index >= 0) {
			return listRow.get(index).getChildAt(indexCol);
		}
		return null;
	}

	private void clearBgRow() {
		for (ItemRow row : listRow) {
			row.setBackgroundColor(Color.parseColor("#ffffff"));
		}
	}

	@Override
	public void removeView(View view) {
		super.removeView(view);

		ItemRow deletedRow = (ItemRow) view;
		for (int i = listRow.size() - 1; i >= 0; i--) {
			ItemRow row = listRow.get(i);
			if (row.getId() == deletedRow.getId()) {
				listRow.remove(row);
			}
		}
		view = null;
		
		setCurrentIndex(-1);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public String getAllTotal() {
		int total = 0;
		for (int i = listRow.size() - 1; i >= 0; i--) {
			TextView txtQ = (TextView)getColumnByRow(i, "Q");
			int q = Integer.parseInt(txtQ.getText().toString()) + 1;
						
			TextView txtPrice = (TextView)getColumnByRow(i, "Price");
			TextView txtTotal = (TextView)getColumnByRow(i, "Total");
			int t = Integer.parseInt(txtPrice.getText().toString()) * q;
			txtTotal.setText(String.valueOf(t));
			total += t;
		}
		return String.valueOf(total);
	}
}
