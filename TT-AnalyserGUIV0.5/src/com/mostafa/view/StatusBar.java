package com.mostafa.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {
	
	
	private JProgressBar progressBar;
	
	public StatusBar() {
		//textField = new JTextField("HH");
		//textField.setHorizontalAlignment(SwingConstants.LEFT);
		progressBar = new JProgressBar(0,100);
		progressBar.setValue(50);
		progressBar.setIndeterminate(true);
		
		add(progressBar);

		
		//setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(this.WIDTH, 16));
		
		
		
		
	}

}
