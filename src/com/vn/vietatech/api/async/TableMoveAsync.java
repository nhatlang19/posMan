package com.vn.vietatech.api.async;

import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.posman.POSMenuActivity;

public class TableMoveAsync extends AsyncTask<String, String, String> {
	private Context mContext;

	public TableMoveAsync(Context context) {
		this.mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		POSMenuActivity act = (POSMenuActivity) mContext;
		act.loadLayoutMoveTable();
		return null;
	}
}
