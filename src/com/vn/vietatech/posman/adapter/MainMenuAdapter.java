package com.vn.vietatech.posman.adapter;

import java.util.ArrayList;

import com.vn.vietatech.api.SectionAPI;
import com.vn.vietatech.api.TableAPI;
import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.MyApplication;
import com.vn.vietatech.posman.R;
import com.vn.vietatech.posman.TableActivity;
import com.vn.vietatech.utils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<PosMenu> listPosMenu;
	MyApplication globalVariable;

	public MainMenuAdapter(Context c) {
		this.mContext = c;

		globalVariable = (MyApplication) mContext.getApplicationContext();
		listPosMenu = globalVariable.getListPosMenu();
	}

	public int getCount() {
		return listPosMenu.size();
	}

	public PosMenu getItem(int position) {
		return listPosMenu.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button btn;
		final PosMenu posMenu = listPosMenu.get(position);

		if (convertView == null) {
			btn = new Button(mContext);
			btn.setLayoutParams(new GridView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//			btn.setBackgroundColor(Color.parseColor(posMenu.getBtnColor()));
//			btn.setTextColor(Color.parseColor(posMenu.getFontColor()));
			btn.setBackgroundColor(Color.RED);
			btn.setTextColor(Color.BLACK);
			btn.setText(posMenu.getDescription());
//			btn.setEllipsize(TruncateAt.END);
			btn.setSingleLine(true);
			btn.setPadding(0,  0,  0,  0);
			btn.setMinWidth(0);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {

				}
			});

		} else {
			btn = (Button) convertView;
		}

		return btn;
	}
}