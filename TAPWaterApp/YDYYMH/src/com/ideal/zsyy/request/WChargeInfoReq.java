package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class WChargeInfoReq extends CommonReq{
	
	private String loginid;
	private Boolean TJType;
	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public Boolean getTJType() {
		return TJType;
	}

	public void setTJType(Boolean tJType) {
		TJType = tJType;
	}

	

	
	

}
