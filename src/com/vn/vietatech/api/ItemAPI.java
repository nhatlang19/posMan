package com.vn.vietatech.api;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import com.vn.vietatech.model.Item;

import android.content.Context;

public class ItemAPI extends AbstractAPI {

	public ItemAPI(Context context) throws Exception {
		super(context);
	}

	public Item getItemBySubMenuSelected(String currSubItem) throws Exception {
		setMethod(METHOD_GET_ITEM);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("currSubItem", currSubItem);
		
		SoapObject response = (SoapObject) this.callService(params);
		SoapObject soapObject = (SoapObject)response.getProperty("diffgram");
		
		Item item = new Item();
		if (soapObject.getPropertyCount() != 0) {
			
			SoapObject webServiceResponse = (SoapObject) soapObject
					.getProperty("NewDataSet");
			SoapObject tableObject = (SoapObject) webServiceResponse
					.getProperty("Table");
		
			item.setItemCode(tableObject.getProperty("ItemCode").toString());
			item.setItemName(tableObject.getProperty("RecptDesc").toString());
			item.setPrice(tableObject.getProperty("UnitSellPrice").toString());
			item.setComboClass(tableObject.getProperty("ComboPack").toString());
			item.setItemType(tableObject.getProperty("WeightItem").toString());
		}
		return item;
	}
}
