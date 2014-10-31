package com.mostafa.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	public MenuBar() {

		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		
		helpMenu.add(aboutItem);

		
		JMenu windowMenu = new JMenu("Window");
		JMenuItem prefItem = new JMenuItem("Preferences");
		
		windowMenu.add(prefItem);
		
		add(fileMenu);
		add(windowMenu);
		add(helpMenu);


	}

}
