package com.vn.vietatech.posman.view;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.posman.view.table.DataTable;
import com.vn.vietatech.posman.view.table.TableHeader;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

public class ItemRow extends TableRow {

	private Context mContext;
	private Item currentItem;

	public ItemRow(Context context) {
		super(context);

		mContext = context;
	}
	
	public Item getCurrentItem() {
		return this.currentItem;
	}

	public void addAllColumns(Item item, TableHeader tblHeader) {
		currentItem = item;
		
		// quality
		TextView txtQuality = createColumn(currentItem.getQty(), tblHeader, "Q");
		this.addView(txtQuality);
		// status
		TextView txtStatus = createColumn(currentItem.getPrintStatus(), tblHeader, "P");
		this.addView(txtStatus);
		// name
		TextView txtName = createColumn(currentItem.getItemName(), tblHeader, "ItemName");
		this.addView(txtName);
		// price
		TextView txtPrice = createColumn(currentItem.getPrice(), tblHeader, "Price");
		this.addView(txtPrice);
		// total
		int total = Integer.parseInt(currentItem.getQty())
				* Integer.parseInt(currentItem.getPrice());
		TextView txtTotal = createColumn(String.valueOf(total), tblHeader, "Total");
		this.addView(txtTotal);
		// currentItemType
		TextView txtItemType = createColumn(currentItem.getItemType(), tblHeader, "ItemType");
		this.addView(txtItemType);
		// currentItemCode
		TextView txtItemCode = createColumn(currentItem.getItemCode(), tblHeader, "ItemCode");
		this.addView(txtItemCode);
		// ModifierInt
		TextView txtModifierInt = createColumn(currentItem.getModifier(), tblHeader, "ModifierInt");
		this.addView(txtModifierInt);
		// MasterCode
		TextView txtMasterCode = createColumn(currentItem.getMasterCode(), tblHeader, "MasterCode");
		this.addView(txtMasterCode);
		// ComboClass
		TextView txtComboClass = createColumn(currentItem.getComboClass(), tblHeader, "ComboClass");
		this.addView(txtComboClass);
		// Hidden
		TextView txtHidden = createColumn(currentItem.getHidden(), tblHeader, "Hidden");
		this.addView(txtHidden);
		// Instruction
		TextView txtInstruction = createColumn(currentItem.getInstruction(), tblHeader, "Instruction");
		this.addView(txtInstruction);
	}

	private TextView createColumn(String item, TableHeader tblHeader, String columnName) {
		DataTable data = tblHeader.getColHeader(columnName);
		
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(new LayoutParams(data.getColWidth(), LayoutParams.WRAP_CONTENT));
		textView.setPadding(20, 5, 20, 5);
		textView.setGravity(data.getColGravity());
		textView.setText(item.trim());
		textView.setTextColor(Color.BLACK);

		return textView;
	}
}
