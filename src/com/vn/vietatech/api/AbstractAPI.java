package com.vn.vietatech.api;

import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.utils.Utils;

public  class AbstractAPI extends AsyncTask<String, String, String> {
	protected Context mContext;
	
	public AbstractAPI(Context context) throws Exception {
		mContext = context;
		
		if(Utils.isNetworkAvailable(context)) {
			throw new Exception("No Internet Connection");
		}
	}

	@Override
	protected String doInBackground(String... params) {
		return null;
	}
	
	
}
