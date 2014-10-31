package com.mostafa.model;

public class DBObserverEvent {

	public static final int QUERY_SUCCEED = 0;
	public static final int QUERY_FAILED = 1;
	public static final int QUERY_SUCCEED_NODATA = 2;
	private int status;
	private String message;

	public DBObserverEvent(int status) {
		this.status=status;
		this.setMessage("");
	}
	
	public DBObserverEvent(int status,String message) {
		this.status=status;
		this.setMessage(message);
	}
	
	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
