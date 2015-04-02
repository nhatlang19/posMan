package com.vn.vietatech.model;

public class Cashier {
	private int _id;
	private String _name;
	private String _pass;
	private String _userGroup;

	
	public Cashier() {
		this._id = 0;
		this._name = "";
		this._pass = "";
		this._userGroup = "";
	}
	
	public Cashier(int id, String name, String pass, String userGroup) {
		this._id = id;
		this._name = name;
		this._pass = pass;
		this._userGroup = userGroup;
	}
	
	public void setId(int id) {
		this._id = id;
	}
	
	public int getId() {
		return this._id;
	}

	public void setName(String name) {
		this._name = name;
	}

	public String getPass() {
		return _pass;
	}

	public void setPass(String _pass) {
		this._pass = _pass;
	}

	public String getUserGroup() {
		return _userGroup;
	}

	public void setUserGroup(String _userGroup) {
		this._userGroup = _userGroup;
	}
}
