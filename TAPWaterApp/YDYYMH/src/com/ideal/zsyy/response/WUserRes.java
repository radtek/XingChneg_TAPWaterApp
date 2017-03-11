package com.ideal.zsyy.response;

import com.ideal2.base.gson.CommonRes;

public class WUserRes extends CommonRes {

	private String LOGINID;
	private String USERNAME;
	private int MeterDateTimeBegin;
	private int MeterDateTimeEnd;
	private String departMentId;
	private String departmentName;
	private String telePhoneNo;
	private int IsPrinter;
	public String getLOGINID() {
		return LOGINID;
	}
	public void setLOGINID(String lOGINID) {
		LOGINID = lOGINID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public int getMeterDateTimeEnd() {
		return MeterDateTimeEnd;
	}
	public void setMeterDateTimeEnd(int meterDateTimeEnd) {
		MeterDateTimeEnd = meterDateTimeEnd;
	}
	public int getMeterDateTimeBegin() {
		return MeterDateTimeBegin;
	}
	public void setMeterDateTimeBegin(int meterDateTimeBegin) {
		MeterDateTimeBegin = meterDateTimeBegin;
	}
	public String getDepartMentId() {
		return departMentId;
	}
	public void setDepartMentId(String departMentId) {
		this.departMentId = departMentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getTelePhoneNo() {
		return telePhoneNo;
	}
	public void setTelePhoneNo(String telePhoneNo) {
		this.telePhoneNo = telePhoneNo;
	}
	public int getIsPrinter() {
		return IsPrinter;
	}
	public void setIsPrinter(int isPrinter) {
		this.IsPrinter = isPrinter;
	}
	
	
}
