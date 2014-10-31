package com.mostafa.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class M5DB implements Database{

	static Logger logger = Logger.getLogger(M5DB.class);

	private Connection con;
	private String ip = "10.74.123.112";
	//private String ip = "10.75.4.164";
	private ArrayList clearedAlarms;

	public M5DB() {
		clearedAlarms = new ArrayList();
	}

	public void connect() throws SQLException {

		logger.info("Connecting to DB (" + ip + ") ...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (con != null) {
			disconnect();
			connect();
		}
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Enumeration drivers = DriverManager.getDrivers();

		String url = "jdbc:oracle:thin:@" + ip + ":1521/mos5100";

		// String url = "jdbc:oracle:thin:@10.74.123.90:1521/mos7100";
		con = DriverManager.getConnection(url, "inventory", "inventory");
		logger.info("Database connection established :)");
		logger.debug(url);

	}

	public void disconnect() throws SQLException {

		logger.info("Disconnecting ... ");

		if (con != null) {
			con.close();
			con = null;
			logger.info("Disconnected successfully :)");
		}
	}

	private int getTTCount(String date) throws SQLException {
		// String checksql =
		// "select count(*) from nf_sys_process_record n,tbl_wftt_datasource d where n.performmethod='Record Time' and n.OPERATORNAME='System' and n.processid=d.processid and d.orderid like 'TT-?%' ";
		String checksql = "select count(*) from tbl_wftt_datasource where orderid like ? ";
		PreparedStatement checkStmt = con.prepareStatement(checksql);
		checkStmt.setString(1, "TT-" + date + "%");
		ResultSet checkResult = checkStmt.executeQuery();
		checkResult.next();
		int count = checkResult.getInt(1);

		checkResult.close();
		checkStmt.close();

		return count;
	}

	private void queryHistoryAlarms(String date) throws SQLException {
		// date: 20141025

		//int countOfRows = getAlarmsCount(date);

		String sql = "select serial, ticketid, ticketstatus from reporter_status_h where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h1 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h2 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h3 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h4 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h5 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h6 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h7 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h8 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h9 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h10 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h11 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 union select serial, ticketid, ticketstatus from reporter_status_h12 where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1";
		//String sql = "select * from reporter_status where clearflag=1 and ACKNOWLEDGED=1 and  ticketid like ? and ttflag=1 ";
		PreparedStatement selectStatement = con.prepareStatement(sql);
		for(int i=1;i<=13;i++)
		selectStatement.setString(i, "TT-" + date + "%");
		//selectStatement.setString(1, "TT-" + "20141024" + "%");
		logger.info("Execute Query");
		ResultSet results = selectStatement.executeQuery();

		/*if (countOfRows == 0) {
			logger.error("No Data to Export");
			return;
		}*/
		logger.debug("Reading Rows");
		clearedAlarms.clear();
		while (results.next()) {
			int serial = results.getInt("serial");
			String ticketID = results.getString("ticketid");
			int ticketStatus = results.getInt("ticketstatus");

			Alarm m5Alarm = new Alarm(serial,ticketID,ticketStatus);

			clearedAlarms.add(m5Alarm);

		}
		logger.debug(100 + "% (" + clearedAlarms.size() + " rows)");
		logger.info("Reading complete");
		results.close();
		selectStatement.close();

	}

	public void printAlarms() {
		for (int i = 0; i <= clearedAlarms.size() - 1; i++) {
			Alarm tt = (Alarm) clearedAlarms.get(i);
			System.out.println(tt);
		}

	}
	
	public ArrayList getClearedAlarms(String date) throws SQLException {
		queryHistoryAlarms(date);
		return clearedAlarms;
	}
}
