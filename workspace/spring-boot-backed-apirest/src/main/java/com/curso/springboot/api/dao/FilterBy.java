package com.curso.springboot.api.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FilterBy {

	private String attrName;
	private String attrValue;
	private String attrOperation;
	private String attrType;
	private String attrFormat;

	public FilterBy(String attrName, String attrValue, String attrOperation, String attrType, String attrFormat) {

		this.attrName = attrName;
		this.attrValue = attrValue;
		this.attrOperation = attrOperation;
		this.attrType = attrType;
		this.attrFormat = attrFormat;

	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Object getAttrValue() throws Exception {
		if ("date".equals(this.attrType)) {
			SimpleDateFormat sdf = new SimpleDateFormat(this.attrFormat);
			return new Timestamp(sdf.parse(attrValue).getTime());
		} else {
			return attrValue;
		}
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getAttrOperation() {
		return attrOperation;
	}

	public void setAttrOperation(String attrOperation) {
		this.attrOperation = attrOperation;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrFormat() {
		return attrFormat;
	}

	public void setAttrFormat(String attrFormat) {
		this.attrFormat = attrFormat;
	}
}
