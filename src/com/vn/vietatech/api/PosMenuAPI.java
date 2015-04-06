package com.vn.vietatech.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Setting;
import com.vn.vietatech.model.SubMenu;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.MyApplication;
import com.vn.vietatech.utils.SettingUtil;

import android.content.Context;

public class PosMenuAPI extends AbstractAPI {

	public PosMenuAPI(Context context) throws Exception {
		super(context);
	}
	
	@Override
	protected String doInBackground(String... params) {
		final MyApplication globalVariable = (MyApplication) mContext;
		ArrayList<PosMenu> list = globalVariable.getListPosMenu();
		if (list == null) {
			try {
				Setting setting = SettingUtil.read(mContext);
				globalVariable.setListPosMenu(getPOSMenuD(setting.getPosGroup()));
			} catch (Exception e) {}
		}
		
		return super.doInBackground(params);
	}

	public ArrayList<PosMenu> getPOSMenuD(String POSGroup) throws Exception {
		if(POSGroup.length() == 0) {
			POSGroup = "1";
		}
		
		setMethod(METHOD_GET_POS_MENU);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("POSGroup", POSGroup);
		
		SoapObject response = (SoapObject) this.callService(params);
		SoapObject soapObject = (SoapObject)response.getProperty("diffgram");
		ArrayList<PosMenu> posMenuList = new ArrayList<PosMenu>();
		if (soapObject.getPropertyCount() != 0) {
			
			SoapObject webServiceResponse = (SoapObject) soapObject
					.getProperty("NewDataSet");
			
			for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
				SoapObject tableObject = (SoapObject) webServiceResponse
						.getProperty(i);
			
				PosMenu posMenu = new PosMenu();
				posMenu.setDescription(tableObject.getProperty("Description").toString());
				posMenu.setBtnColor(tableObject.getProperty("BtnColor").toString());
				posMenu.setFontColor(tableObject.getProperty("FontColor").toString());
				posMenu.setDefaultValue(tableObject.getProperty("DefaultValue").toString());
				
				posMenuList .add(posMenu);
			}
		}
		return posMenuList;
	}
	
	public ArrayList<SubMenu> getSubMenu(String selectedPOSMenu, String POSGroup) throws Exception {
		if(POSGroup.length() == 0) {
			POSGroup = "1";
		}
		
		setMethod(METHOD_GET_SUB_MENU);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectedPOSMenu", selectedPOSMenu);
		params.put("POSGroup", POSGroup);
		
		SoapObject response = (SoapObject) this.callService(params);
		SoapObject soapObject = (SoapObject)response.getProperty("diffgram");
		ArrayList<SubMenu> subMenuList = new ArrayList<SubMenu>();
		if (soapObject.getPropertyCount() != 0) {
			
			SoapObject webServiceResponse = (SoapObject) soapObject
					.getProperty("NewDataSet");
			
			for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
				SoapObject tableObject = (SoapObject) webServiceResponse
						.getProperty(i);
			
				SubMenu subMenu = new SubMenu();
				subMenu.setDescription(tableObject.getProperty("Description").toString());
				subMenu.setSeqNum(tableObject.getProperty("SeqNum").toString());
				subMenu.setDefaultValue(tableObject.getProperty("DefaultValue").toString());
				
				subMenuList .add(subMenu);
			}
		}
		return subMenuList;
	}
}
