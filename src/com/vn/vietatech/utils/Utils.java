package com.vn.vietatech.utils;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;

public class Utils {
	public static boolean isNetworkAvailable(Context context) {
		return ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo() != null;
	}

	public static void showAlert(Context context, String message) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		// Create the AlertDialog object and return it
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static int parseColor(String colorDelphi) {
		int RGB_Delphi = Integer.parseInt(colorDelphi);

		int R = (int) RGB_Delphi % 256;
		int G = ((int) RGB_Delphi / 256) % 256;
		int B = ((int) RGB_Delphi / 256 / 256) % 256;

		return Color.rgb(R, G, B);
	}
	
	public static String formatPrice (int price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###");
	    return formatter.format(price);
	}
}
