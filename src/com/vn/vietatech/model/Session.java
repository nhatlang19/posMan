package com.vn.vietatech.model;

public class Session {
	private int _id;
	private String _name;
	private String _revCtr;
	
	public Session() {}
	
	public Session(int id, String name) {
		this._id = id;
		this._name = name;
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
	
	public String getName() {
		return this._name;
	}
	
	public void setRevCtr(String revCtr) {
		this._revCtr = revCtr;
	}
	
	public String getRevCtr() {
		return this._revCtr;
	}
}
