package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.utils.Utils;

public abstract class AbstractAPI {
	public AbstractAPI(Context context) throws Exception {
		if(Utils.isNetworkAvailable(context)) {
			throw new Exception("No Internet Connection");
		}
	}
}
