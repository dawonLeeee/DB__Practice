package puppyWalk.schedule.vo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {


		
		String scheduleTime = "2022-09-20 15시 ~17시";
		
		SimpleDateFormat dateFormat = new 
                SimpleDateFormat ("yyyy-MM-dd");
            
			try {
				Date bookDate = dateFormat.parse(scheduleTime.substring(0, 10));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Date now = new Date();
		
		
		
		System.out.println(now.before(bookDate));


	}
}
