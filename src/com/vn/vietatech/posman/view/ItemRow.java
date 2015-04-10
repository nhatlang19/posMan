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

	public void addAllColumns(Item item, TableView header) {
		currentItem = item;
		
		// quality
		TextView txtQuality = createColumn(currentItem.getQty(), Gravity.CENTER, header.getWidthHeaderByRow("Q"));
		this.addView(txtQuality);
		// status
		TextView txtStatus = createColumn(currentItem.getPrintStatus(), Gravity.CENTER, header.getWidthHeaderByRow("P"));
		this.addView(txtStatus);
		// name
		TextView txtName = createColumn(currentItem.getItemName(), Gravity.LEFT, header.getWidthHeaderByRow("ItemName"));
		this.addView(txtName);
		// price
		TextView txtPrice = createColumn(currentItem.getPrice(), Gravity.LEFT, header.getWidthHeaderByRow("Price"));
		this.addView(txtPrice);
		// total
		int total = Integer.parseInt(currentItem.getQty())
				* Integer.parseInt(currentItem.getPrice());
		TextView txtTotal = createColumn(String.valueOf(total), Gravity.LEFT, header.getWidthHeaderByRow("Total"));
		this.addView(txtTotal);
		// currentItemType
		TextView txtItemType = createColumn(currentItem.getItemType(), Gravity.CENTER, header.getWidthHeaderByRow("ItemType"));
		this.addView(txtItemType);
		// currentItemCode
		TextView txtItemCode = createColumn(currentItem.getItemCode(), Gravity.CENTER, header.getWidthHeaderByRow("ItemCode"));
		this.addView(txtItemCode);
		// ModifierInt
		TextView txtModifierInt = createColumn(currentItem.getModifier(), Gravity.LEFT, header.getWidthHeaderByRow("ModifierInt"));
		this.addView(txtModifierInt);
		// MasterCode
		TextView txtMasterCode = createColumn(currentItem.getMasterCode(), Gravity.LEFT, header.getWidthHeaderByRow("MasterCode"));
		this.addView(txtMasterCode);
		// ComboClass
		TextView txtComboClass = createColumn(currentItem.getComboClass(), Gravity.CENTER, header.getWidthHeaderByRow("ComboClass"));
		this.addView(txtComboClass);
		// Hidden
		TextView txtHidden = createColumn(currentItem.getHidden(), Gravity.LEFT, header.getWidthHeaderByRow("Hidden"));
		this.addView(txtHidden);
		// Instruction
		TextView txtInstruction = createColumn(currentItem.getInstruction(), Gravity.LEFT, header.getWidthHeaderByRow("Instruction"));
		this.addView(txtInstruction);
	}

	private TextView createColumn(String item, int gravity, int widthHeader) {
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(new LayoutParams(widthHeader, LayoutParams.WRAP_CONTENT));
		textView.setPadding(20, 5, 20, 5);
		textView.setGravity(gravity);
		textView.setText(item);
		textView.setTextColor(Color.BLACK);

		return textView;
	}
}
