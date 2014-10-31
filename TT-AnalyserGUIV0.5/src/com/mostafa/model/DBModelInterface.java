package com.mostafa.model;

import java.util.ArrayList;

public interface DBModelInterface {

	
	public void queryUnclearedTTs(String date);
	public ArrayList getUnclearedTTs();
	public void registerObserver(DBObserver observer);
	public void setDate(String date);
	
}
