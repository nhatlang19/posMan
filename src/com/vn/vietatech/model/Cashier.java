package com.vn.vietatech.model;

public class Cashier {
	private int _id;
	private String _name;
	
	public Cashier() {}
	
	public Cashier(int id, String name) {
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
}
