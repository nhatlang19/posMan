package com.vn.vietatech.posman.view;

import java.util.ArrayList;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.posman.MyApplication;
import com.vn.vietatech.posman.R;
import com.vn.vietatech.posman.view.table.DataTable;
import com.vn.vietatech.posman.view.table.MyTable;
import com.vn.vietatech.utils.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableOrder extends TableLayout {
	private String[] headers = new String[] { "Q", "P", "ItemName", "Price",
			"Total", "ItemType", "ItemCode", "ModifierInt", "MasterCode",
			"ComboClass", "Hidden", "Instruction" };

	private Integer[] headerWidth = new Integer[] { 80, 60, 300, 200, 200, 200, 200,
			200, 200, 230, 170, 200 };
	
	private Integer[] headerGravity = new Integer[] { Gravity.CENTER, Gravity.CENTER, Gravity.LEFT, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER,
			Gravity.CENTER, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER };
	
	private MyTable table;
	
	MyApplication globalVariable;

	public TableOrder(Context context, LinearLayout parent) {
		super(context);

		setLayoutParams(parent.getLayoutParams());
		table = new MyTable(context, initDataTable());
	}
	
	public MyTable getTable() {
		return table;
	}
	
	private ArrayList<DataTable> initDataTable() {
		ArrayList<DataTable> data = new ArrayList<DataTable>();
		int size = headers.length;
		
		for(int i=0;i<size; i++) {
			String name = headers[i];
			int width = headerWidth[i];
			int gravity = headerGravity[i];
			
			data.add(new DataTable(name, width, gravity));
		}
		return data;
	}

	public ArrayList<ItemRow> getAllRows() {
		return table.getBody().getAllRows();
	}

	public ItemRow getCurrentRow() {
		return table.getBody().getCurrentRow();
	}
	
	public void createNewRow(Item item) {
		ArrayList<ItemRow> listRow = table.getBody().getAllRows();
		int index = -1;
		for (int i = listRow.size() - 1; i >= 0; i--) {
			ItemRow row = listRow.get(i);
			if (row.getCurrentItem().getItemCode().equals(item.getItemCode())) {
				table.getBody().clearBgRow();
				table.getBody().getAllRows().get(i).setBackgroundColor(Color.parseColor("#edf0fe"));
				table.getBody().setCurrentIndex(i);
				index = i;
				break;
			}
		}
		if (index != -1) {
			// update quality
			TextView txtQ = (TextView) getColumnByRow(index, "Q");
			int q = Integer.parseInt(txtQ.getText().toString()) + 1;
			txtQ.setText(String.valueOf(q));
		} else {
			table.getBody().addRow(item);
		}
	}

	public Object getColumnCurrentRow(String name) {
		return table.getBody().getColumnCurrentRow(name);
	}

	public Object getColumnByRow(int index, String name) {
		return table.getBody().getColumnByRow(index, name);
	}

	public String getAllTotal() {
		ArrayList<ItemRow> listRow = table.getBody().getAllRows();
		
		int total = 0;
		for (int i = listRow.size() - 1; i >= 0; i--) {
			TextView txtQ = (TextView) getColumnByRow(i, "Q");
			int q = Integer.parseInt(txtQ.getText().toString());

			TextView txtPrice = (TextView) getColumnByRow(i, "Price");
			TextView txtTotal = (TextView) getColumnByRow(i, "Total");
			int t = Integer.parseInt(txtPrice.getText().toString()) * q;
			txtTotal.setText(String.valueOf(t));
			total += t;
		}
		return Utils.formatPrice(total);
	}
}
