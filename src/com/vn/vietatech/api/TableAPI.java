package com.vn.vietatech.api;

import java.util.ArrayList;
import java.util.HashMap;

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
		
		SoapObject response = (SoapObject)this.callService();
		SoapObject soapObject = (SoapObject)response.getProperty("diffgram");
		
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
	
	public ArrayList<Table> getTableBySection(Section section) throws Exception {
		setMethod(METHOD_GET_TABLE_BY_SECTION);
		
		ArrayList<Table> tables = new ArrayList<Table>();
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("section", section.getId());
		
		SoapObject response = (SoapObject) this.callService(params);
		SoapObject soapObject = (SoapObject)response.getProperty("diffgram");
		
		if (soapObject.getPropertyCount() != 0) {
			SoapObject webServiceResponse = (SoapObject) soapObject
					.getProperty("NewDataSet");
			
			for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
				SoapObject tableObject = (SoapObject) webServiceResponse
						.getProperty(i);

				Table table = new Table();
				table.setTableNo(tableObject.getProperty("TableNo").toString());
				table.setStatus(tableObject.getProperty("Status").toString());
				table.setOpenBy(tableObject.getProperty("OpenBy").toString());
				table.setDescription2(tableObject.getProperty("Description2").toString());
				table.setSection(section);
				tables.add(table);
			}
		}
		return tables;
	}
	
	public boolean updateTableStatus(String status, String cashierId, String currentTable) throws Exception {
		setMethod(METHOD_UPDATE_TABLE_STATUS);
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tableStatus", status);
		params.put("cashierID", cashierId);
		params.put("currentTable", currentTable);
		
		return Boolean.parseBoolean(callService(params).toString());
	}
}
