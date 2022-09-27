package puppyWalk.common;

import java.util.Date;

public class TestClass {

	public static void main(String[] args) {
		String scheduleTime = "2022-09-21 11시 ~13시";
//		int year = Integer.parseInt(timeStr.substring(0, 4));
//		String month = timeStr.substring(5, 7);
//		String date = timeStr.substring(8, 10);
//		
//		System.out.println(year + "." + month + "." + date);
		
		
		int year = Integer.parseInt(scheduleTime.substring(0, 4));
		int month = Integer.parseInt(scheduleTime.substring(5, 7));
		int date = Integer.parseInt(scheduleTime.substring(8, 10));
		
		Date bookedDate = new Date(year, month, date);
		System.out.println(bookedDate);
		
		
		
		Date currDate = new Date(System.currentTimeMillis());
		
		System.out.println(bookedDate.compareTo(currDate));
	}
}
