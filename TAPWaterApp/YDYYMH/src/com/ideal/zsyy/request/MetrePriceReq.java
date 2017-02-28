package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class MetrePriceReq extends CommonReq  {
	private String readMeterRecordId; 
    private String waterUserId ;
    private double currData ;
	public String getReadMeterRecordId() {
		return readMeterRecordId;
	}
	public void setReadMeterRecordId(String readMeterRecordId) {
		this.readMeterRecordId = readMeterRecordId;
	}
	public String getWaterUserId() {
		return waterUserId;
	}
	public void setWaterUserId(String waterUserId) {
		this.waterUserId = waterUserId;
	}
	public double getCurrData() {
		return currData;
	}
	public void setCurrData(double currData) {
		this.currData = currData;
	}
    
}
