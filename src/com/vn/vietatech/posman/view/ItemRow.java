package com.vn.vietatech.posman.view;

import com.vn.vietatech.model.Item;

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

	public void addAllColumns(Item item) {
		currentItem = item;
		
		// quality
		TextView txtQuality = createColumn(currentItem.getQty(), Gravity.CENTER);
		this.addView(txtQuality);
		// status
		TextView txtStatus = createColumn(currentItem.getPrintStatus(), Gravity.CENTER);
		this.addView(txtStatus);
		// name
		TextView txtName = createColumn(currentItem.getItemName(), Gravity.LEFT);
		this.addView(txtName);
		// price
		TextView txtPrice = createColumn(currentItem.getPrice(), Gravity.CENTER);
		this.addView(txtPrice);
		// total
		int total = Integer.parseInt(currentItem.getQty())
				* Integer.parseInt(currentItem.getPrice());
		TextView txtTotal = createColumn(String.valueOf(total), Gravity.CENTER);
		this.addView(txtTotal);
		// currentItemType
		TextView txtItemType = createColumn(currentItem.getItemType(), Gravity.CENTER);
		this.addView(txtItemType);
		// currentItemCode
		TextView txtItemCode = createColumn(currentItem.getItemCode(), Gravity.CENTER);
		this.addView(txtItemCode);
		// ModifierInt
		TextView txtModifierInt = createColumn(currentItem.getModifier(), Gravity.LEFT);
		this.addView(txtModifierInt);
		// MasterCode
		TextView txtMasterCode = createColumn(currentItem.getMasterCode(), Gravity.CENTER);
		this.addView(txtMasterCode);
		// ComboClass
		TextView txtComboClass = createColumn(currentItem.getComboClass(), Gravity.CENTER);
		this.addView(txtComboClass);
		// Hidden
		TextView txtHidden = createColumn(currentItem.getHidden(), Gravity.CENTER);
		this.addView(txtHidden);
		// Instruction
		TextView txtInstruction = createColumn(currentItem.getInstruction(), Gravity.LEFT);
		this.addView(txtInstruction);
	}

	private TextView createColumn(String item, int gravity) {
		TextView textView = new TextView(mContext);
		textView.setPadding(10, 5, 10, 5);
		textView.setGravity(gravity);
		textView.setText(item);
		textView.setTextColor(Color.BLACK);

		return textView;
	}
}
