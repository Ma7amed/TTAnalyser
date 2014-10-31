package com.mostafa.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class M7DB implements Database{

	static Logger logger = Logger.getLogger(M7DB.class);

	private Connection con;
	private String ip = "10.74.123.90";
	//private String ip = "10.75.4.136";
	private ArrayList clearedTTs;

	public M7DB() {
		clearedTTs = new ArrayList();
	}

	public void connect() throws SQLException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		logger.info("Connecting to DB (" + ip + ") ...");

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

		String url = "jdbc:oracle:thin:@" + ip + ":1521/mos7100";

		// String url = "jdbc:oracle:thin:@10.74.123.90:1521/mos7100";
		con = DriverManager.getConnection(url, "servicedesk", "servicedesk");
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

	public int getClearedTTCount(String date) throws SQLException {
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

	private void queryClearedTTs(String date) throws SQLException {
		// date: 20141025

		int countOfRows = getClearedTTCount(date);

		String sql = "select d.orderid,d.cttfaultno from nf_sys_process_record n,tbl_wftt_datasource d where n.performmethod='Record Time' and n.OPERATORNAME='System' and n.processid=d.processid and (d.orderid like ? )";
		PreparedStatement selectStatement = con.prepareStatement(sql);
		selectStatement.setString(1, "TT-" + date + "%");
		// selectStatement.setString(1, filter_interfaceid);
		ResultSet results = selectStatement.executeQuery();

		if (countOfRows == 0) {
			logger.error("No Data to Export");
			return;
		}

		logger.debug("Reading Rows");
		clearedTTs.clear();
		while (results.next()) {

			String orderid = results.getString("orderid");
			String faultNo = results.getString("cttfaultno");
			boolean cleared = true;

			TroubleTicket tt_Simple = new TroubleTicket(orderid, faultNo, cleared);

			clearedTTs.add(tt_Simple);

		}
		logger.debug(100 + "% (" + clearedTTs.size() + " rows)");
		logger.info("Reading complete");
		results.close();
		selectStatement.close();

	}

	public ArrayList queryTTs(ArrayList orderIds) throws SQLException {
		// take list of orderids, get the details (ArrayList<TroubleTicket>)
		
		
		ArrayList troubleTickets = new ArrayList();

		// construct the sql
		String sqlcond = "";
		for (int i = 0; i < orderIds.size(); i++) {
			String tt = (String) orderIds.get(i);
			if (sqlcond == "") {
				sqlcond = sqlcond + "'" + tt + "'";
			} else {
				sqlcond = sqlcond + ", '" + tt + "'";
			}
		}


		/*String sql = "select orderid, cttfaultno,processstatus, CREATEN_SUBMIT_100020 from tbl_wftt_datasource where orderid in"
				+ "(" + sqlcond + ")";*/
		String sql = "select t.orderid,t.cttfaultno,t.processstatus, s1.step_name \"CURSTEP\",s2.step_name \"NEXTSTEP\",to_char(t.CREATEN_SUBMIT_100020,'yyyy-mm-dd hh24:mi:ss') \"CREATEN_SUBMIT_100020\" from tbl_wftt_datasource t, tbl_wfconf_wfstep s1, tbl_wfconf_wfstep s2 where  t.CURSTEPID=s1.id and t.NEXTSTEPID=s2.id and  t.orderid in "
				+ "(" + sqlcond + ")";
		PreparedStatement selectStatement = con.prepareStatement(sql);
		//selectStatement.setString(1, sqlcond);

		ResultSet results = selectStatement.executeQuery();

		logger.debug("Reading Rows");
		int count = 0;

		while (results.next()) {
			String orderid = results.getString("orderid");
			String faultNo = results.getString("cttfaultno");
			String processstatus = results.getString("processstatus");
			String createDate = results.getString("CREATEN_SUBMIT_100020");
			String currentStep = results.getString("CURSTEP");
			String nextStep = results.getString("NEXTSTEP");;

			TroubleTicket tt = new TroubleTicket(orderid,faultNo,processstatus,currentStep,nextStep,createDate);

			troubleTickets.add(tt);

		}
		logger.debug(100 + "% (" + troubleTickets.size() + " rows)");
		logger.info("Reading complete");
		results.close();
		selectStatement.close();
		return troubleTickets;
	}

	public void printTTs() {
		for (int i = 0; i <= clearedTTs.size() - 1; i++) {
			TroubleTicket tt = (TroubleTicket) clearedTTs.get(i);
			System.out.println(tt);
		}

	}

	public ArrayList getClearedTTs(String date) throws SQLException {
		queryClearedTTs(date);
		return clearedTTs;
	}
}
