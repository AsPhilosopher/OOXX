package Service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUnits {

	public String getPercentage(double rate) {
		int int_rate = (int)(rate*100);
		return "%" + Integer.toString(int_rate);
	}
	
	public String getNowTime() {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
		Date date=new Date();
		return dateFormater.format(date);
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyUnits myUnits = new MyUnits();
		System.out.println(myUnits.getPercentage(1));
		System.out.println(myUnits.getPercentage(0.36));
		System.out.println(myUnits.getPercentage(0.369089));
	}*/

}
