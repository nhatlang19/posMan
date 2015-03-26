package com.vn.vietatech.model;

public class Setting {
	private String _serverIP;
	private String _database;
	private String _user;
	private String _pass;
	private String _storeNo;
	private String _posGroup;
	private String _posId;
	private String _subMenu;

	public String getServerIP() {
		return _serverIP;
	}

	public void setServerIP(String _serverIP) {
		this._serverIP = _serverIP;
	}

	public String getDatabase() {
		return _database;
	}

	public void setDatabase(String _database) {
		this._database = _database;
	}

	public String getUser() {
		return _user;
	}

	public void setUser(String _user) {
		this._user = _user;
	}

	public String getPass() {
		return _pass;
	}

	public void setPass(String _pass) {
		this._pass = _pass;
	}
	
	public String getStoreNo() {
		return _storeNo;
	}

	public void setStoreNo(String _storeNo) {
		this._storeNo = _storeNo;
	}
	
	public String getPosGroup() {
		return _posGroup;
	}

	public void setPosGroup(String _posGroup) {
		this._posGroup = _posGroup;
	}
	
	public String getPosId() {
		return _posId;
	}

	public void setPosId(String _posId) {
		this._posId = _posId;
	}
	
	public String getSubMenu() {
		return _subMenu;
	}

	public void setSubMenu(String _subMenu) {
		this._subMenu = _subMenu;
	}
}
