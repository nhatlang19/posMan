package com.vn.vietatech.posman.adapter;

import com.vn.vietatech.model.Session;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SessionAdapter  extends ArrayAdapter<Session>{

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Session)
    private Session[] values;

    public SessionAdapter(Context context, int textViewResourceId,
    		Session[] values) {
        super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
	}

	public int getCount() {
		return values.length;
	}

	public Session getItem(int position) {
		return values[position];
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		
		if (convertView != null){
			label = (TextView) convertView;
        } else {
        	LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        	label = (TextView) inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );
        }
		
		label.setText(values[position].getName());
		return label;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		if (convertView != null){
			label = (TextView) convertView;
        } else {
        	LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        	label = (TextView) inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );
        }
		label.setText(values[position].getName());
		return label;
	}
}
