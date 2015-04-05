package com.vn.vietatech.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

public class AbstractAPI extends AsyncTask<String, String, String> {
	protected Context mContext;
	protected SoapObject request;
	
	protected static String METHOD_GET_SECTION = "GetSection";
	protected static String METHOD_GET_TABLELIST = "GetTableList";
	protected static String METHOD_GET_TABLE_BY_SECTION = "GetTableBySection";
	protected static String METHOD_GET_USER = "GetUser";
	protected static String METHOD_UPDATE_TABLE_STATUS = "UpdateTableStatus";
	protected static String METHOD_GETPOSTMENUD = "GetPOSMenuD";
	
	

	protected static String NAMESPACE;
	protected static String SERVER_IP;
	protected static String URL;

	private String method = "";
	
	protected String getMethod() {
		return method;
	}

	protected void setMethod(String method) {
		this.method = method;
	}


	public AbstractAPI(Context context) throws Exception {
		mContext = context;
		
//		SERVER_IP = "113.161.79.56";
		SERVER_IP = SettingUtil.read(mContext).getServerIP();
		NAMESPACE = "http://tempuri.org/";
		URL = "http://" + SERVER_IP + "/V6BOService/V6BOService.asmx";

		if (!Utils.isNetworkAvailable(context)) {
			throw new Exception("No Internet Connection");
		}
	}

	protected String getSoapAction() {
		return NAMESPACE + getMethod();
	}

	protected SoapObject callService() throws Exception {
		request = new SoapObject(NAMESPACE, getMethod());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		SoapObject webServiceResponse = null;
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(getSoapAction(), envelope);
			if (envelope.bodyIn instanceof SoapFault) {
				String str = ((SoapFault) envelope.bodyIn).faultstring;
				throw new Exception(str);
			} else {
				webServiceResponse = (SoapObject)((SoapObject) envelope.getResponse()).getProperty("diffgram");
				return webServiceResponse;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	protected SoapObject callService(HashMap<String, String> params) throws Exception {

		request = new SoapObject(NAMESPACE, getMethod());

		// Get keys.
		Set<String> keys = params.keySet();
		// Loop over String keys.
		for (String key : keys) {
		    request.addProperty(key, params.get(key).toString());
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		SoapObject webServiceResponse = null;
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(getSoapAction(), envelope);
			if (envelope.bodyIn instanceof SoapFault) {
				String str = ((SoapFault) envelope.bodyIn).faultstring;
				throw new Exception(str);
			} else {
				webServiceResponse = (SoapObject)((SoapObject) envelope.getResponse()).getProperty("diffgram");
				return webServiceResponse;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	protected boolean callServiceExecute(HashMap<String, String> params) throws Exception {

		request = new SoapObject(NAMESPACE, getMethod());

		// Get keys.
		Set<String> keys = params.keySet();
		// Loop over String keys.
		for (String key : keys) {
		    request.addProperty(key, params.get(key).toString());
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(getSoapAction(), envelope);
			if (envelope.bodyIn instanceof SoapFault) {
				String str = ((SoapFault) envelope.bodyIn).faultstring;
				throw new Exception(str);
			} else {
				return Boolean.parseBoolean(envelope.getResponse().toString());
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	protected String doInBackground(String... params) {
		return null;
	}
}
