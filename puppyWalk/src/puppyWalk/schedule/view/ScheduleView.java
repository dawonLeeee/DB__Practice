package puppyWalk.schedule.view;

import static puppyWalk.common.JDBCTemplete.*;
import java.util.Scanner;
import puppyWalk.schedule.model.service.ScheduleService;

public class ScheduleView {
	
	private ScheduleService scheduleService = new ScheduleService();
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	public void mainMenu() {
		
		do {
			System.out.println("\n\n******스케줄 예약*******");
			System.out.println("1. 전체 스케줄 확인"); 
			System.out.println("2. (훈련/산책)별 스케줄 확인");
			System.out.println("3. 스케줄 예약하기");
			System.out.println("4. 내가 예약한 스케줄 확인");
			System.out.println("0. 메뉴 나가기");

			System.out.print("번호 선택 : ");
			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1:break;
			case 2:break;
			case 3:break;
			case 4:break;
			case 0:
				System.out.println("[메인 메뉴로 이동합니다]\n");
				break;
			default:
				System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}
	

}
