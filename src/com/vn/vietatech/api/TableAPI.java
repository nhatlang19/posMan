package com.vn.vietatech.api;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.MyApplication;

import android.content.Context;
import android.util.Log;

public class TableAPI extends AbstractAPI {
	public TableAPI(Context context) throws Exception {
		super(context);
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		final MyApplication globalVariable = (MyApplication) mContext;
		ArrayList<Table> tables = globalVariable.getTables();
		if (tables == null) {
			try {
				globalVariable.setTables(getTableList());
			} catch (Exception e) {}
		}
		
		return super.doInBackground(params);
	}
	
	public ArrayList<Table> getTableList() throws Exception {
		setMethod(METHOD_GET_TABLELIST);
		
		ArrayList<Table> tables = new ArrayList<Table>();
		
		SoapObject soapObject = this.callService();
		if (soapObject.getPropertyCount() != 0) {
			SoapObject webServiceResponse = (SoapObject) soapObject
					.getProperty("NewDataSet");
			for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
				SoapObject tableObject = (SoapObject) webServiceResponse
						.getProperty(i);

				Table table = new Table();
				table.setTableNo(tableObject.getProperty("TableNo").toString());

				tables.add(table);
			}
		}
		return tables;
	}
	
	public ArrayList<Table> getTableBySection(String session) throws Exception {
		setMethod(METHOD_GET_TABLE_BY_SECTION);
		
		ArrayList<Table> tables = new ArrayList<Table>();
		
		ArrayList<String> params1 = new ArrayList<String>();
		params1.add("session");
		ArrayList<String> params2 = new ArrayList<String>();
		params2.add(session);
		SoapObject soapObject = this.callService(params1, params2);
		Log.v("TABLEAPI", soapObject.toString());
		Log.v("TABLEAPI", session);
		
		System.out.println(soapObject.toString());
		if (soapObject.getPropertyCount() != 0) {
			System.out.println(soapObject.toString());
			
//			SoapObject webServiceResponse = (SoapObject) ((SoapObject) soapObject
//					.getProperty("diffgram")).getProperty("NewDataSet");
//			for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
//				SoapObject table = (SoapObject) webServiceResponse
//						.getProperty(i);
//
//			}
		}
		return tables;
	}
}
