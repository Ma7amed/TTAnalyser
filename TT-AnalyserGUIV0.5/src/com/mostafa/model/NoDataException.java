package com.mostafa.model;

public class NoDataException extends Exception {
	
	public NoDataException() {}
	
	public NoDataException(String message) {
		super("Query Succeed, but no data");
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return "Query Succeed, but no data";
	}

	
	
}
