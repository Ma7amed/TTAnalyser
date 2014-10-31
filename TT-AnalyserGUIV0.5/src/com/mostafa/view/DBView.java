package com.mostafa.view;



import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.apache.log4j.Logger;

import com.mostafa.controller.ControllerInterface;
import com.mostafa.model.DBModelInterface;
import com.mostafa.model.DBObserver;
import com.mostafa.model.DBObserverEvent;

public class DBView implements DBObserver {
	
	static Logger logger = Logger.getLogger(DBView.class);

	private DBModelInterface dbModelInterface;
	private ControllerInterface controllerInterface;

	private JFrame frame;
	private QueryPanel_2 queryPanel;
	private TablePanel tablePanel;
	private StatusBar statusBar;
	private MenuBar menuBar;

	public DBView(ControllerInterface controller, DBModelInterface model) {
		//setLookAndFeel();
		this.controllerInterface = controller;
		this.dbModelInterface = model;

		dbModelInterface.registerObserver(this);

		createView();

		frame.setVisible(true);


	}

	public void updateView(DBObserverEvent e) {
		if (e.getStatus() == DBObserverEvent.QUERY_SUCCEED) {
			// update the view with the new data
			// update it here
			ArrayList unclearedTTs = dbModelInterface.getUnclearedTTs();
			// textArea.setText("I got the uncleared TTs, the count is: " +
			// unclearedTTs.size());
			tablePanel.setData(unclearedTTs);
			queryPanel.enableQueryBtn();
			showTable();
			hideStatusBar();
			
			String fileName = e.getMessage();
			
			JOptionPane.showConfirmDialog(this.frame,
					"Export complete successfully."+"\n"+fileName, "Succeed",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			
		} else if (e.getStatus() == DBObserverEvent.QUERY_SUCCEED_NODATA) {
			// clear the view
			tablePanel.clearData();
			queryPanel.enableQueryBtn();
			showTable();
			frame.setSize(700, 200);
			hideStatusBar();

		} else if (e.getStatus() == DBObserverEvent.QUERY_FAILED) {
			// show error
			JOptionPane.showConfirmDialog(this.frame,
					"Problem during query, please check log", "Error",
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			queryPanel.enableQueryBtn();
			hideStatusBar();
		} else {
			// don't know what to do :)
		}

	}

	private void createView() {

		frame = new JFrame("Uncleared TTs Detector");
		queryPanel = new QueryPanel_2();
		tablePanel = new TablePanel();
		menuBar = new MenuBar();
		hideTable();
		statusBar = new StatusBar();
		
		Border inner=BorderFactory.createEtchedBorder();
		Border outer=BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compound = BorderFactory.createCompoundBorder(outer, inner);
		queryPanel.setBorder(compound);
		tablePanel.setBorder(outer);
		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BorderLayout());
		
		
		
		frame.add(queryPanel,BorderLayout.NORTH);
		frame.add(tablePanel,BorderLayout.CENTER);
		frame.add(statusBar,BorderLayout.SOUTH);
		
		frame.setLocationRelativeTo(null);

		showStatusBar();
		frame.pack();
		hideStatusBar();

		queryPanel.setListener(new QueryPanelListener() {

			public void queryOccurred() {

				showStatusBar();
				queryPanel.disableQueryBtn();
				controllerInterface.queryUnclearedTTs(queryPanel.getDate());
			}
		});

	}

	public void showTable() {
		tablePanel.setVisible(true);
	}

	public void hideTable() {
		logger.debug("Hiding Table");
		tablePanel.setVisible(false);
	}

	public void showStatusBar() {
		logger.debug("Showing Status Bar");
		statusBar.setVisible(true);
	}

	public void hideStatusBar() {
		logger.debug("hiding Status Bar");
		statusBar.setVisible(false);
	}

	public void setLookAndFeel() {

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

	}
	
	

}
