package com.mostafa.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class MyFileWriter {

	static Logger logger = Logger.getLogger(MyFileWriter.class);

	private File exportFile;
	BufferedWriter writer;

	public MyFileWriter() {

		try {
			exportFile = getFile();
			writer = new BufferedWriter(new FileWriter(exportFile, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeln(String[] text) {
		try {
			String line = "";
			for (int i = 0; i < text.length; i++) {
				if (line == "") {
					line = line + "\"" + text[i].replaceAll("\"", "\"\"") + "\"";
				} else {
					line = line + ",\"" + text[i] + "\"";
				}
			}
			writer.write(line + "\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private File getFile() throws IOException {
		int count = 1;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String fileName = dateFormat.format(cal.getTime()) + "_" + "TT_Export"
				+ "-" + count + ".csv";

		File exportDir = new File(new File(".").getCanonicalPath()
				+ File.separator + "export");
		if (!exportDir.exists()) {
			logger.debug("Creating dir: " + exportDir);
			exportDir.mkdir();
		}
		exportFile = new File(exportDir + File.separator + fileName);
		while (true) {
			if (exportFile.exists()) {
				count++;
				fileName = dateFormat.format(cal.getTime()) + "_" + "TT_Export"
						+ "-" + count + ".csv";
				exportFile = null;
				exportFile = new File(exportDir + File.separator + fileName);

			} else {

				logger.info("Exporting to: " + exportFile.getAbsoluteFile());
				return exportFile;
			}
		}
	}

	public void exportToFile(ArrayList data) {
		// check
		if (!(data.get(0).getClass() == TroubleTicket.class)) {
			logger.error("Can't export ...");
			return;
		}

		writeln(TroubleTicket.getHeaders());
		for (int i = 0; i < data.size(); i++) {
			TroubleTicket tt = ((TroubleTicket) data.get(i));
			String orderid = tt.getOrderid();
			String faultNo = tt.getFaultNo();
			String processstatus = tt.getProcessstatus();
			String currentStep = tt.getCurrentStep();
			String nextStep = tt.getNextStep();
			String createDate = tt.getCreateDate();

			String[] text = new String[6];
			text[0] = orderid;
			text[1] = faultNo;
			text[2] = processstatus;
			text[3] = currentStep;
			text[4] = nextStep;
			text[5] = createDate;
			
			writeln(text);
		}
		close();

	}
	
	public String getExportFileName() {
		return exportFile.getAbsoluteFile().toString();
	}

}
