package com.mostafa.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

public class QueryPanel_1 extends JPanel {

	static Logger logger = Logger.getLogger(QueryPanel_1.class);

	private JButton queryBtn;
	private JComboBox yearCombo;
	private JComboBox monthCombo;
	private JComboBox dayCombo;
	private JLabel yearLabel;
	private JLabel monthLabel;
	private JLabel dayLabel;
	private QueryPanelListener listener;
	private Image img1;
	private Image img2;

	private Thread t;

	public QueryPanel_1() {

		String[] year = new String[30];
		for (int i = 0; i <= 29; i++)
			year[i] = Integer.toString(i + 2000);

		String[] month = new String[12];
		for (int i = 0; i <= 11; i++)
			month[i] = Integer.toString(i + 1);

		String[] day = new String[31];
		for (int i = 0; i <= 30; i++)
			day[i] = Integer.toString(i + 1);

		yearCombo = new JComboBox(year);
		yearCombo.setToolTipText("Year");
		monthCombo = new JComboBox(month);
		monthCombo.setToolTipText("Month");
		dayCombo = new JComboBox(day);
		dayCombo.setToolTipText("Day");
		yearLabel = new JLabel("Year:");
		monthLabel = new JLabel("Month:");
		dayLabel = new JLabel("Day:");
		

		initComboBox();

		queryBtn = new JButton();
		queryBtn.setToolTipText("Query Uncleared TTs");
		

		
		try {
			img1 = ImageIO.read(getClass().getResource("/images/search.png"));
			img2 = ImageIO.read(getClass().getResource("/images/search2.png"));
			queryBtn.setIcon(new ImageIcon(img2));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		queryBtn.setIcon(new ImageIcon(img1));
		queryBtn.setPreferredSize(new Dimension(queryBtn.getPreferredSize().width,yearCombo.getPreferredSize().height));

		// queryBtn.setPreferredSize(new JButton("Query!!").getPreferredSize());
		queryBtn.setHorizontalAlignment(SwingConstants.LEFT);

		//setLayout(new FlowLayout(FlowLayout.LEFT));

		// add(queryField);
		/*add(yearCombo);
		add(monthCombo);
		add(dayCombo);
		add(queryBtn);*/

		queryBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				listener.queryOccurred();
			}
		});
		
		
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// First Row

		gc.gridx = 0;
		gc.gridy = 0;

		gc.weightx = 0;
		gc.weighty = 1;

		gc.gridx = 0;
		//gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(5, 10, 0, 10);
		add(yearLabel, gc);

		//gc.anchor = GridBagConstraints.LINE_START;
		//gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(5, 0, 0, 10);
		add(monthLabel, gc);
		
		gc.gridx = 2;
		gc.gridy = 0;
		add(dayLabel, gc);
		
		// Second Row
		
		gc.gridx=0;
		gc.gridy=1;
		gc.insets = new Insets(0, 10, 10, 10);
		add(yearCombo,gc);
		
		gc.gridx=1;
		gc.gridy=1;
		gc.insets = new Insets(0, 0, 10, 10);
		add(monthCombo,gc);
		
		

		gc.gridx=2;
		gc.gridy=1;
		
		add(dayCombo,gc);
		
		gc.gridx=3;
		gc.gridy=1;
		gc.weightx=1;
		add(queryBtn,gc);
		

	}

	private void initComboBox() {

		// set value to today
		DateFormat yearformat = new SimpleDateFormat("yyyy");
		DateFormat monthformat = new SimpleDateFormat("MM");
		DateFormat dayformat = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();

		yearCombo.setSelectedItem(yearformat.format(cal.getTime()));
		monthCombo.setSelectedItem(monthformat.format(cal.getTime()));
		dayCombo.setSelectedItem(dayformat.format(cal.getTime()));
	}

	public void disableQueryBtn() {
		queryBtn.setEnabled(false);
		yearCombo.setEnabled(false);
		monthCombo.setEnabled(false);
		dayCombo.setEnabled(false);
		yearLabel.setEnabled(false);
		monthLabel.setEnabled(false);
		dayLabel.setEnabled(false);
		showLoading();
	}

	public void enableQueryBtn() {
		queryBtn.setEnabled(true);
		yearCombo.setEnabled(true);
		monthCombo.setEnabled(true);
		dayCombo.setEnabled(true);
		yearLabel.setEnabled(true);
		monthLabel.setEnabled(true);
		dayLabel.setEnabled(true);
		showQuery();
	}

	public String getDate() {
		String date = yearCombo.getSelectedItem().toString()
				+ monthCombo.getSelectedItem().toString()
				+ dayCombo.getSelectedItem().toString();
		logger.debug("Selected Date: " + date);
		return date;
	}

	public void setListener(QueryPanelListener listener) {
		this.listener = listener;
	}

	public void showLoading() {

		t = new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						queryBtn.setIcon(new ImageIcon(img1));
						Thread.sleep(500);
						queryBtn.setIcon(new ImageIcon(img2));
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						break;
					}
				}
			}
		});
	//	t.start();
	}

	public void showQuery() {
		t.interrupt();
	}
	

}
