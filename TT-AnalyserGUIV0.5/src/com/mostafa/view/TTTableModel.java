package com.mostafa.view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.mostafa.model.TroubleTicket;

public class TTTableModel extends AbstractTableModel {

	private ArrayList db;

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return TroubleTicket.getHeaders().length;
	}

	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		//return super.isCellEditable(arg0, arg1);
		return true;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return db.size();
	}

	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		TroubleTicket tt = (TroubleTicket) db.get(row);

		switch (col) {
		case 0:
			return tt.getOrderid();
		case 1:
			return tt.getFaultNo();
		case 2:
			return tt.getProcessstatus();
		case 3:
			return tt.getCurrentStep();
		case 4:
			return tt.getNextStep();
		case 5:
			return tt.getCreateDate();
		}
		return null;
	}

	public void setData(ArrayList db) {
		this.db = db;
	}

	public String getColumnName(int col) {

		return TroubleTicket.getHeaders()[col];
		
	}

}
