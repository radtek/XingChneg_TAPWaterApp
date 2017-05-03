package com.ideal.zsyy.response;


import com.ideal2.base.gson.CommonRes;

public class WChargeInfoRes extends CommonRes {
//	private List<ChargeInfoItem>ChargeInfoItem;
//
//	public List<ChargeInfoItem> getChargeInfoItem() {
//		return ChargeInfoItem;
//	}
//
//	public void setChargeInfoItem(List<ChargeInfoItem> chargeInfoItem) {
//		ChargeInfoItem = chargeInfoItem;
//	}
	private int ChargeCount;
	private double ChargeFee;
	private int ChargeWater;
	public int getChargeCount() {
		return ChargeCount;
	}

	public void setChargeCount(int chargeCount) {
		ChargeCount = chargeCount;
	}

	public double getChargeFee() {
		return ChargeFee;
	}

	public void setChargeFee(double chargeFee) {
		ChargeFee = chargeFee;
	}

	public int getChargeWater() {
		return ChargeWater;
	}

	public void setChargeWater(int chargeWater) {
		ChargeWater = chargeWater;
	} 

}
