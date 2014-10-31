package com.mostafa.view;

import java.net.URL;

import javax.swing.ImageIcon;

public class Utils {

	public static ImageIcon createImageIcon(String path) {
		URL imgURL = Utils.class.getResource(path);

		if (imgURL == null) {
			System.err.println("Failed to create Icon");
			return null;
		}
		return new ImageIcon(imgURL);
	}
	
}
