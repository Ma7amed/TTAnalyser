package com.mostafa.model;

public class Alarm {

	private int serial;
	private String ticketID;
	private int ticketStatus;
	
	
	public int getSerial() {
		return serial;
	}


	public void setSerial(int serial) {
		this.serial = serial;
	}


	public String getTicketid() {
		return ticketID;
	}


	public void setTicketid(String ticketid) {
		this.ticketID = ticketid;
	}


	public int getTicketstatus() {
		return ticketStatus;
	}


	public void setTicketstatus(int ticketstatus) {
		this.ticketStatus = ticketstatus;
	}


	public Alarm(int serial, String ticketid, int ticketstatus) {
		super();
		this.serial = serial;
		this.ticketID = ticketid;
		this.ticketStatus = ticketstatus;
	}
	
	public String toString() {
		return "serial: " + serial + ", ticketid.: " + ticketID + ", ticketstatus: " + ticketStatus;
	}
	
	
}
