package com.mostafa.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DBModel implements DBModelInterface, Runnable {

	static Logger logger = Logger.getLogger(DBModel.class);

	private M5DB m5DB;
	private M7DB m7DB;
	private ArrayList clearedTTs;
	private ArrayList clearedAlarms;
	private ArrayList unclearedAlarms;
	private ArrayList unclearetedTTs;
	private MyFileWriter myFileWriter;
	private String date;
	private String fileName;

	private DBObserver observer;

	public DBModel() {
		m5DB = new M5DB();
		m7DB = new M7DB();
		clearedTTs = new ArrayList();
		clearedAlarms = new ArrayList();
		unclearedAlarms = new ArrayList();
		unclearetedTTs = new ArrayList();
	}

	public void queryCleared(String date) throws SQLException {
		// query cleared Alarms from mos5100 -> clearedAlarms
		// query cleared TTs from mos7100 -> clearedTTs

		clearedAlarms.clear();
		m5DB.connect();
		clearedAlarms = m5DB.getClearedAlarms(date);
		m5DB.disconnect();

		logger.debug("Query successfully " + clearedAlarms.size()
				+ " cleared Alarms from MOS5100 DB");

		clearedTTs.clear();
		m7DB.connect();
		clearedTTs = m7DB.getClearedTTs(date);
		m7DB.disconnect();
		// TODO Auto-generated catch block

		logger.debug("Query successfully " + clearedTTs.size()
				+ " cleared TTs from MOS7100 DB");

	}

	public void queryUncleared() throws NoDataException {
		// query alarms whose TTs not cleared -> unclearedAlarms
		// query the TT details of these TTs of alarms -> unclearedTTs

		if (clearedAlarms.size() == 0) {
			throw new NoDataException();
		}

		HashMap tickets = new HashMap();

		for (int i = 0; i < clearedTTs.size(); i++) {
			TroubleTicket ticket = (TroubleTicket) clearedTTs.get(i);
			tickets.put(ticket.getOrderid(), ticket);
		}
		unclearedAlarms.clear();
		for (int i = 0; i < clearedAlarms.size(); i++) {
			Alarm alarm = (Alarm) clearedAlarms.get(i);
			if (!tickets.containsKey(alarm.getTicketid())) {
				// logger.debug("Found one TT not cleared: " +
				// alarm.getTicketid());
				unclearedAlarms.add(alarm);
			}
		}
		logger.debug("Found " + unclearedAlarms.size() + " TTs uncleared :(");

		if (unclearedAlarms.size() == 0) {
			throw new NoDataException();
		}

	}

	private void queryUnclearTTDetails() {
		ArrayList orderIds = new ArrayList();
		// create arraylist of tts
		for (int i = 0; i < unclearedAlarms.size(); i++) {
			orderIds.add(((Alarm) unclearedAlarms.get(i)).getTicketid());
		}

		try {
			unclearetedTTs.clear();
			m7DB.connect();
			unclearetedTTs = m7DB.queryTTs(orderIds);
			m7DB.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String exportToFile() {
		// export the uncleared TTs to csv file in the program location
		myFileWriter = new MyFileWriter();
		myFileWriter.exportToFile(unclearetedTTs);
		return myFileWriter.getExportFileName();
	}

	public void queryUnclearedTTs(String date) {
		// All in one
		try {
			queryCleared(date);
			queryUncleared();
			queryUnclearTTDetails();
			fileName = exportToFile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			observer.updateView(new DBObserverEvent(DBObserverEvent.QUERY_FAILED));
			return;
		} catch (NoDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			observer.updateView(new DBObserverEvent(DBObserverEvent.QUERY_SUCCEED_NODATA));
			return;
		}

		observer.updateView(new DBObserverEvent(DBObserverEvent.QUERY_SUCCEED,fileName));
		// when everything done -> inform the observers
	}

	public ArrayList getUnclearedTTs() {
		// TODO Auto-generated method stub
		return unclearetedTTs;
	}

	public void registerObserver(DBObserver observer) {
		this.observer = observer;
	}

	public void run() {
		queryUnclearedTTs(date);
	}

	public void setDate(String date) {
		this.date = date;
	}

}
