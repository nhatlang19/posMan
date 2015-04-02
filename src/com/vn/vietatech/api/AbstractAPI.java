package com.vn.vietatech.api;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.utils.Utils;

public class AbstractAPI extends AsyncTask<String, String, String> {
	protected Context mContext;
	protected SoapObject request;
	
	protected static String METHOD_GET_SECTION = "GetSection";
	protected static String METHOD_GET_TABLELIST = "GetTableList";
	protected static String METHOD_GET_TABLE_BY_SECTION = "GetTableBySection";
	protected static String METHOD_GET_USER = "GetUser";

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

	static {
		NAMESPACE = "http://tempuri.org/";
		SERVER_IP = "113.161.79.56";
		URL = "http://" + SERVER_IP + "/V6BOService/V6BOService.asmx";
	}

	public AbstractAPI(Context context) throws Exception {
		mContext = context;

		if (Utils.isNetworkAvailable(context)) {
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

	protected SoapObject callService(ArrayList<String> params1,
			ArrayList<String> params2) throws Exception {

		request = new SoapObject(NAMESPACE, getMethod());

		for (int i = 0; i < params1.size(); i++) {
			request.addProperty(params1.get(i), params2.get(i));
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

	@Override
	protected String doInBackground(String... params) {
		return null;
	}
}
