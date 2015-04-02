package com.vn.vietatech.posman;

import java.util.ArrayList;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;

import android.app.Application;

public class MyApplication extends Application {
	private ArrayList<Section> _listSections = null;
	private Cashier _cashier = null;
	private ArrayList<Table> _tables = null;

	public ArrayList<Section> getSections() {
		return _listSections;
	}

	public void setSections(ArrayList<Section> listSections) {
		this._listSections = listSections;
	}
	
	public Cashier getCashier() {
		return _cashier;
	}

	public void setCashier(Cashier cashier) {
		this._cashier = cashier;
	}
	
	public ArrayList<Table> getTables() {
		return this._tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this._tables = tables;
	}
}
