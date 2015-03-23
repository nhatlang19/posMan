package com.vn.vietatech.posman;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;

public class TableAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<String> texts = new ArrayList<String>();

	public TableAdapter(Context c) {
		mContext = c;

		int x = 0;

		while (x < 1000) {

			texts.add(x + "");
			x++;
		}
	}

	public int getCount() {
		return texts.size();
	}

	public Object getItem(int position) {
		return texts.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button btn;
		if (convertView == null) {
			btn = new Button(mContext);
			btn.setLayoutParams(new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			btn.setBackgroundColor(android.graphics.Color.YELLOW);
		} else {
			btn = (Button) convertView;
		}

		btn.setText(texts.get(position));
		return btn;
	}

}