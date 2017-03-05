package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.FeeInfo;
import com.ideal2.base.gson.CommonRes;

public class MetrePriceRes extends CommonRes {
	private double TotleFee ;
	private double avgPrice;
	private String calcProc;
	private FeeInfo step1;
	private FeeInfo step2;
	private FeeInfo step3;

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

	public String getCalcProc() {
		return calcProc;
	}

	public void setCalcProc(String calcProc) {
		this.calcProc = calcProc;
	}

	public FeeInfo getStep1() {
		return step1;
	}

	public void setStep1(FeeInfo step1) {
		this.step1 = step1;
	}

	public FeeInfo getStep2() {
		return step2;
	}

	public void setStep2(FeeInfo step2) {
		this.step2 = step2;
	}

	public FeeInfo getStep3() {
		return step3;
	}

	public void setStep3(FeeInfo step3) {
		this.step3 = step3;
	}
	
}
