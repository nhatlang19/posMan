package com.vn.vietatech.posman;

import java.util.ArrayList;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Session;
import com.vn.vietatech.model.Table;

import android.app.Application;

public class MyApplication extends Application {
	private ArrayList<Session> _listSessions = null;
	private Cashier _cashier = null;
	private ArrayList<Table> _tables = null;

	public ArrayList<Session> getSessions() {
		return _listSessions;
	}

	public void setSessions(ArrayList<Session> listSessions) {
		this._listSessions = listSessions;
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
