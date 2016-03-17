package com.kaancelen.charterv3.models;

import java.io.Serializable;

public class JobRecord implements Serializable{

	private static final long serialVersionUID = 3989973818213717238L;
	
	private String personel;
	private String type;
	private String month;
	private String result;
	private String year;
	
	public String getPersonel() {
		return personel;
	}
	public void setPersonel(String personel) {
		this.personel = personel;
	}
	public String getType() {
		if(type == null){
			return "";
		}
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getResult() {
		if(result == null){
			return "";
		}
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
