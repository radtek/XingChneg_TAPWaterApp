package com.ideal.zsyy.request;

import java.util.List;

import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.entity.WUserItem;
import com.ideal2.base.gson.CommonReq;

public class WUploadUserReq extends CommonReq {
	private String readMeterRecordId;
	private int waterMeterEndNumber;
	private int totalNumber;
	private double avePrice;
	private double waterTotalCharge;
	private double extraChargePrice1;
	private double extraCharge1;
	private double extraChargePrice2;
	private double extraCharge2;
	private double extraTotalCharge;
	private double totalCharge;
	private double OVERDUEMONEY;
	private String readMeterRecordDate;
	private String checkState;
	private String checkDateTime;
	private String checker;
	private String chargeState;
	private String chargeID;
	private String Longitude;
	private String Latitude;
	private String Phone;
	private String Memo1;
	private double totalNumberFirst;
	private double avePriceFirst;
	private double waterTotalChargeFirst;
	private double totalNumberSecond;
	private double avePriceSecond;
	private double waterTotalChargeSecond;
	private double totalNumberThird;
	private double avePriceThird;
	private double waterTotalChargeThird;
	public String getReadMeterRecordId() {
		return readMeterRecordId;
	}
	public void setReadMeterRecordId(String readMeterRecordId) {
		this.readMeterRecordId = readMeterRecordId;
	}
	public int getWaterMeterEndNumber() {
		return waterMeterEndNumber;
	}
	public void setWaterMeterEndNumber(int waterMeterEndNumber) {
		this.waterMeterEndNumber = waterMeterEndNumber;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public double getAvePrice() {
		return avePrice;
	}
	public void setAvePrice(double avePrice) {
		this.avePrice = avePrice;
	}
	public double getWaterTotalCharge() {
		return waterTotalCharge;
	}
	public void setWaterTotalCharge(double waterTotalCharge) {
		this.waterTotalCharge = waterTotalCharge;
	}
	public double getExtraChargePrice1() {
		return extraChargePrice1;
	}
	public void setExtraChargePrice1(double extraChargePrice1) {
		this.extraChargePrice1 = extraChargePrice1;
	}
	public double getExtraCharge1() {
		return extraCharge1;
	}
	public void setExtraCharge1(double extraCharge1) {
		this.extraCharge1 = extraCharge1;
	}
	public double getExtraChargePrice2() {
		return extraChargePrice2;
	}
	public void setExtraChargePrice2(double extraChargePrice2) {
		this.extraChargePrice2 = extraChargePrice2;
	}
	public double getExtraCharge2() {
		return extraCharge2;
	}
	public void setExtraCharge2(double extraCharge2) {
		this.extraCharge2 = extraCharge2;
	}
	public double getExtraTotalCharge() {
		return extraTotalCharge;
	}
	public void setExtraTotalCharge(double extraTotalCharge) {
		this.extraTotalCharge = extraTotalCharge;
	}
	public double getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(double totalCharge) {
		this.totalCharge = totalCharge;
	}
	public double getOVERDUEMONEY() {
		return OVERDUEMONEY;
	}
	public void setOVERDUEMONEY(double oVERDUEMONEY) {
		OVERDUEMONEY = oVERDUEMONEY;
	}
	public String getReadMeterRecordDate() {
		return readMeterRecordDate;
	}
	public void setReadMeterRecordDate(String readMeterRecordDate) {
		this.readMeterRecordDate = readMeterRecordDate;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public String getCheckDateTime() {
		return checkDateTime;
	}
	public void setCheckDateTime(String checkDateTime) {
		this.checkDateTime = checkDateTime;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getChargeState() {
		return chargeState;
	}
	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}
	public String getChargeID() {
		return chargeID;
	}
	public void setChargeID(String chargeID) {
		this.chargeID = chargeID;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getMemo1() {
		return Memo1;
	}
	public void setMemo1(String memo1) {
		Memo1 = memo1;
	}
	public double getTotalNumberFirst() {
		return totalNumberFirst;
	}
	public void setTotalNumberFirst(double totalNumberFirst) {
		this.totalNumberFirst = totalNumberFirst;
	}
	public double getAvePriceFirst() {
		return avePriceFirst;
	}
	public void setAvePriceFirst(double avePriceFirst) {
		this.avePriceFirst = avePriceFirst;
	}
	public double getWaterTotalChargeFirst() {
		return waterTotalChargeFirst;
	}
	public void setWaterTotalChargeFirst(double waterTotalChargeFirst) {
		this.waterTotalChargeFirst = waterTotalChargeFirst;
	}
	public double getTotalNumberSecond() {
		return totalNumberSecond;
	}
	public void setTotalNumberSecond(double totalNumberSecond) {
		this.totalNumberSecond = totalNumberSecond;
	}
	public double getAvePriceSecond() {
		return avePriceSecond;
	}
	public void setAvePriceSecond(double avePriceSecond) {
		this.avePriceSecond = avePriceSecond;
	}
	public double getWaterTotalChargeSecond() {
		return waterTotalChargeSecond;
	}
	public void setWaterTotalChargeSecond(double waterTotalChargeSecond) {
		this.waterTotalChargeSecond = waterTotalChargeSecond;
	}
	public double getTotalNumberThird() {
		return totalNumberThird;
	}
	public void setTotalNumberThird(double totalNumberThird) {
		this.totalNumberThird = totalNumberThird;
	}
	public double getAvePriceThird() {
		return avePriceThird;
	}
	public void setAvePriceThird(double avePriceThird) {
		this.avePriceThird = avePriceThird;
	}
	public double getWaterTotalChargeThird() {
		return waterTotalChargeThird;
	}
	public void setWaterTotalChargeThird(double waterTotalChargeThird) {
		this.waterTotalChargeThird = waterTotalChargeThird;
	}
	
}
