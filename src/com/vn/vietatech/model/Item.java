package com.vn.vietatech.model;

public class Item {
	private String id;
	private String qty;
	private String printStatus;
	private String itemName;
	private String price;
	private String total;
	private String itemType;
	private String itemCode;
	private String modifierInt;
	private String masterCode;
	private String comboClass;
	private String hidden;
	private String instruction;

	public Item() {
		id = "";
		qty = "1";
		printStatus = "";
		itemName = "";
		price = "0";
		total = "0";
		itemType = "";
		itemCode = "";
		modifierInt = "";
		masterCode = "";
		comboClass = "";
		hidden = "";
		instruction = "";
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getComboClass() {
		return comboClass;
	}

	public void setComboClass(String comboClass) {
		this.comboClass = comboClass;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public String getModifier() {
		return modifierInt;
	}

	public void setModifier(String modifierInt) {
		this.modifierInt = modifierInt;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		float iPrice = Float.parseFloat(price);
		this.price = String.valueOf((int)iPrice);
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPrintStatus() {
		if(printStatus.equals("1")) {
			printStatus = "#";
		}
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
}
