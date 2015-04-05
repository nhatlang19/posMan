package com.vn.vietatech.model;

public class PosMenu {
	private String description;
	private String btnColor;
	private String fontColor;
	private String defaultValue;

	public PosMenu() {
		this.setDescription("");
		this.setBtnColor("");
		this.setFontColor("");
		this.setDefaultValue("");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(String btnColor) {
		this.btnColor = btnColor;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
