package com.ideal.zsyy.response;

import com.ideal2.base.gson.CommonRes;

public class MetrePriceRes extends CommonRes {
	private double TotleFee ;
	private double avgPrice;

	public double getTotleFee() {
		return TotleFee;
	}

	public void setTotleFee(double totleFee) {
		TotleFee = totleFee;
	}

	public double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}
	
}
