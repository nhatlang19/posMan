package com.vn.vietatech.adapter;

import java.util.ArrayList;
import java.util.Random;

import com.vn.vietatech.model.Session;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;

public class TableAdapter extends BaseAdapter {
	private Context mContext;
	private Session session;
	ArrayList<String> texts = new ArrayList<String>();

	public TableAdapter(Context c, Session currentSession) {
		session = currentSession;
		mContext = c;

		int x = 0;
		Random ran = new Random();
		int max = ran.nextInt(20) + 10;
		while (x < max) {

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
		return position;
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