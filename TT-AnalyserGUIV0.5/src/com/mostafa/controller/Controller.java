package com.mostafa.controller;

import com.mostafa.model.DBModelInterface;
import com.mostafa.view.DBView;

public class Controller implements ControllerInterface{

	private DBView dbView;
	private DBModelInterface dbModelInterface;
	
	
	public Controller(DBModelInterface dbModelInterface) {

		this.dbModelInterface = dbModelInterface;
		dbView = new DBView(this,dbModelInterface);
	}
	
	public void queryUnclearedTTs(String date) {
		dbModelInterface.setDate(date);
		//dbModelInterface.queryUnclearedTTs(date);
		Runnable t = (Runnable) dbModelInterface;
		Thread m = new Thread(t);
		m.start();
	}

}
