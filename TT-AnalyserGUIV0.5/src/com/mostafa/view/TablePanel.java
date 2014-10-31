package com.mostafa.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TablePanel extends JPanel {

	private JTable table;
	private TTTableModel tableModel;
	
	
	public TablePanel() {

		tableModel = new TTTableModel();
		table = new JTable();
		
		setLayout(new BorderLayout());
		
		
		add(new JScrollPane(table),BorderLayout.CENTER);
		
	}
	
	public void setData(ArrayList db) {
		tableModel.setData(db);
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}

	public void clearData() {
		// TODO Auto-generated method stub
		ArrayList emptyData = new ArrayList();
		tableModel.setData(emptyData);
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}
	
	
	
	
}
