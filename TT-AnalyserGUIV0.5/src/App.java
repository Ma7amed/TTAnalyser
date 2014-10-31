

import java.util.Calendar;

import com.mostafa.controller.Controller;
import com.mostafa.model.DBModel;

public class App {

	public static void main2(String[] args) {


		
	}

	public static void main(String[] args) {

		
		DBModel dbModel = new DBModel();
		Controller controller = new Controller(dbModel);
		

	}
	
	public static void maind(String[] args) {

		Calendar cal = Calendar.getInstance();
		int x = cal.MONTH;
		System.out.println(x);
	}
	
	


}
