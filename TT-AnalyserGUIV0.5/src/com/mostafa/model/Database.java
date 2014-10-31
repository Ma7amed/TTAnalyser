package com.mostafa.model;

import java.sql.SQLException;

//import java.sql.Connection;

public interface Database {
	
	public void connect() throws SQLException;
	public void disconnect() throws SQLException;
	//public Connection getConnection();

}
