package com.vn.vietatech.posman.adapter;

import java.util.ArrayList;
import java.util.Random;

import com.vn.vietatech.model.Session;
import com.vn.vietatech.posman.R;
import com.vn.vietatech.posman.TableActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;
import android.widget.TextView;

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
		final int pos = position;
		if (convertView == null) {
			btn = new Button(mContext);
			btn.setLayoutParams(new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			btn.setBackgroundResource(R.drawable.table_item);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					showOrderForm(texts.get(pos));
				}
			});

		} else {
			btn = (Button) convertView;
		}

		btn.setText(texts.get(position));
		return btn;
	}

	private void showOrderForm(String name) {
		// get order_dialog.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);

		View promptView = layoutInflater.inflate(R.layout.order_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				mContext);

		// set order_dialog.xml to be the layout file of the alertdialog builder
		alertDialogBuilder.setView(promptView);
		alertDialogBuilder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				TableActivity tableActivity = (TableActivity) mContext;
				tableActivity.refresh();
			}
		});

		// create an alert dialog
		final AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();

		final TextView lbTitle = (TextView) promptView.findViewById(R.id.lbTitle);
		lbTitle.setText(name);
		final Button btnSave = (Button) promptView.findViewById(R.id.btnSave);
		final Button btnOk = (Button) promptView.findViewById(R.id.btnOk);
		final Button btnCancel = (Button) promptView
				.findViewById(R.id.btnCancel);

		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				alertD.cancel();
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				alertD.cancel();
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				alertD.cancel();
			}
		});
	}

}