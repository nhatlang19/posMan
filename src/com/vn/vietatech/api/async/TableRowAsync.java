package com.vn.vietatech.api.async;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.api.OrderAPI;
import com.vn.vietatech.model.Order;
import com.vn.vietatech.posman.POSMenuActivity;
import com.vn.vietatech.posman.R;
import com.vn.vietatech.posman.dialog.TransparentProgressDialog;

public class TableRowAsync extends AsyncTask<String, Order, Order> {

	private Context mContext;
	private String tableNo;
	private TransparentProgressDialog pd;

	public TableRowAsync(Context context) {
		this.mContext = context;

		pd = new TransparentProgressDialog(mContext, R.drawable.spinner);
		pd.show();
	}

	@Override
	protected Order doInBackground(String... params) {
		tableNo = params[0];

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String POSBizDate = sdf.format(new Date());

		Order currentOrder = null;
		try {
			currentOrder = new OrderAPI(mContext).getOrderEditType(POSBizDate,
					tableNo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return currentOrder;
	}

	@Override
	protected void onPostExecute(Order result) {
		POSMenuActivity act = (POSMenuActivity) mContext;
		try {
			act.addRowByOrder(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pd.dismiss();
		}
		super.onPostExecute(result);
	}
}
