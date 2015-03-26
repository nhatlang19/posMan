package com.vn.vietatech.posman.view;

import com.vn.vietatech.model.Order;
import com.vn.vietatech.posman.R;

import android.content.Context;
import android.graphics.Color;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableView extends TableLayout {
	private String[] headers = new String[] { "Q", "P", "ItemName", "Price",
			"Total", "ItemType", "ItemCode", "ModifierInt", "MasterCode",
			"ComboClass", "Hidden", "Instruction" };

	private Context mContext;
	
	public TableView(Context context) {
		super(context);
		mContext = context;
		
		setBackgroundColor(Color.WHITE);
		setLayoutParams(new HorizontalScrollView.LayoutParams(
			LayoutParams.MATCH_PARENT, 400));
		
		initHeader();
		
		Order order = new Order();
		order.setQty("1");
		addRow(order);
		Order order1 = new Order();
		order1.setQty("2");
		addRow(order1);
	}
	
	private void initHeader() {
		TableRow tblHeader = new TableRow(mContext);
		for(String header: headers) {
			TextView textView = new TextView(mContext);
			textView.setBackgroundResource(R.drawable.order_table_header);
			textView.setText(header);
			textView.setTextColor(Color.BLACK);
			tblHeader.addView(textView);
		}
		
		this.addView(tblHeader);
	}
	
	public void addRow(Order order) {
		if(order != null) {
			// TODO: add new row
			TableRow newRow = new TableRow(mContext);
			
			TextView textView = new TextView(mContext);
			textView.setText(order.getQty());
			textView.setTextColor(Color.BLACK);
			newRow.addView(textView);
			
			this.addView(newRow);
		}
	}
}
