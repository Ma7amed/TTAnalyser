package com.mostafa.model;

public class TroubleTicket {

	private String orderid;
	private String faultNo;
	private boolean cleared;
	private String processstatus;
	private String currentStep;
	private String nextStep;
	private String createDate;
	
	private static String[] headers = {"Order ID","Fault No.", "Processstatus","Current Step","Next Step", "Create Date" };

	public TroubleTicket(String orderid, String faultNo, String processstatus,
			String currentStep, String nextStep, String createDate) {
		super();
		this.orderid = orderid;
		this.faultNo = faultNo;
		this.processstatus = processstatus;
		this.currentStep = currentStep;
		this.nextStep = nextStep;
		this.createDate = createDate;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public TroubleTicket(String orderid, String faultNo, String processstatus,
			String createDate) {
		super();
		this.orderid = orderid;
		this.faultNo = faultNo;
		this.processstatus = processstatus;
		this.createDate = createDate;
	}

	public String getProcessstatus() {
		return processstatus;
	}

	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public TroubleTicket(String orderid, String faultNo, boolean cleared) {
		super();
		this.orderid = orderid;
		this.faultNo = faultNo;
		this.cleared = cleared;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getFaultNo() {
		return faultNo;
	}

	public void setFaultNo(String faultNo) {
		this.faultNo = faultNo;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}

	public String toString() {
		return "orderid: " + orderid + ", faultNo.: " + faultNo + ", cleared: "
				+ cleared;
	}
	
	
	
	public static String[] getHeaders() {
		return headers;
	}




	/*
	 * private String orderid; private String faultNo; private boolean cleared;
	 * private String processstatus; private String createDate;
	 */

}
