package com.vn.vietatech.posman.view.table;

import java.util.ArrayList;

import android.content.Context;

public class MyTable  {
	private TableHeader header;
	private TableBody body;
	
	public MyTable(Context context, ArrayList<DataTable> dataTable) {
		header = new TableHeader(context, dataTable);
		body = new TableBody(context, header);
	}
	
	public TableBody getBody() {
		return body;
	}
	
	public TableHeader getHeader() {
		return header;
	}
}
