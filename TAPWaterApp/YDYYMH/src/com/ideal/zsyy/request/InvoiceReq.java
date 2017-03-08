package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class InvoiceReq extends CommonReq {

	private String invoiceNo;
	private String readMeterId;
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getReadMeterId() {
		return readMeterId;
	}
	public void setReadMeterId(String readMeterId) {
		this.readMeterId = readMeterId;
	}
	
}
