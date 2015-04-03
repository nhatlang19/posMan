package com.vn.vietatech.api;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;

import android.content.Context;

public class UserApi extends AbstractAPI {

	public UserApi(Context context) throws Exception {
		super(context);
	}

	public Cashier login(String username, String password) throws Exception {
		setMethod(METHOD_GET_USER);
		Cashier cashier = new Cashier();

		ArrayList<String> params1 = new ArrayList<String>();
		params1.add("username");
		params1.add("password");
		ArrayList<String> params2 = new ArrayList<String>();
		params2.add(username);
		params2.add(password);
		SoapObject soapObject = this.callService(params1, params2);
		if (soapObject.getPropertyCount() != 0) {
			
			SoapObject webServiceResponse = (SoapObject) soapObject
					.getProperty("NewDataSet");
			
			SoapObject tableObject = (SoapObject) webServiceResponse
					.getProperty("Table");
			
			cashier.setId(tableObject.getProperty("CashierID").toString());
			cashier.setName(tableObject.getProperty("CashierName").toString());
			cashier.setPass(tableObject.getProperty("CashierPwd").toString());
			cashier.setUserGroup(tableObject.getProperty("UserGroup").toString());
		}
		return cashier;
	}
}
