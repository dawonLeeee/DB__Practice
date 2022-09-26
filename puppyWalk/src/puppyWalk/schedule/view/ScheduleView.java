package puppyWalk.schedule.view;

import static puppyWalk.common.JDBCTemplete.*;


import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


import puppyWalk.main.view.MainView;
import puppyWalk.schedule.model.service.ScheduleService;
import puppyWalk.schedule.vo.Schedule;

public class ScheduleView {
	
	private ScheduleService service = new ScheduleService();
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	private Set<String> partnerList = new HashSet<>();
	
	public void mainMenu() {
		
		do {
			System.out.println("\n\n******스케줄 조회 / 예약*******");
			System.out.println("1. 스케줄 확인"); // ok
			System.out.println("2. 스케줄 검색"); // ok
			System.out.println("3. 내가 예약한 스케줄 확인");
			System.out.println("0. 메뉴 나가기");

			System.out.print("번호 선택 : ");
			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1: selectMenu(); break;
			case 2: searchMenu(); break;
			case 3: selectMySchedule(); break;
			case 0:
				System.out.println("[메인 메뉴로 이동합니다]\n");
				break;
			default:
				System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}




	/**
	 * 내가 예약한 스케줄 확인
	 */
	private void selectMySchedule() {

		System.out.println("\n[내가 예약한 스케줄 확인]\n");
		
		while(true) {
			
			System.out.println("시작 시간 :");
			System.out.print("(9 / 11 / 13 / 15 / 17)시 중에서 선택 ..(숫자만) : ");
			int scheduleTime = sc.nextInt();
			sc.nextLine();
			
			if((scheduleTime == 9) ||
					(scheduleTime == 11) ||
					(scheduleTime == 13) ||
					(scheduleTime == 15) ||
					(scheduleTime == 17)) {
				try {
					List<Schedule> scheduleList = service.searchScheduleByTime(scheduleTime);
					System.out.println("\n[입력하신 시간대의 스케줄 조회]\n");
					
					printAllSchedule(scheduleList);
					
				} catch (Exception e) {
					System.out.println("\n<<스케줄 목록 조회 중 예외 발생>>\n");
					e.printStackTrace();
				}
				break;
			} else {
				System.out.println("\n<<(9 / 11 / 13 / 15 / 17)시 중에서만 선택해주세요(숫자)>>\n");
			}
			
		}	
		
	}




	/**
	 * 스케줄 조회 메뉴
	 */
	private void selectMenu() {
		
		// 파트너리스트를 업로드하기 위한 부분
		try {
			List<Schedule> scheduleList = service.selectAllSchedule();
			
			// 스케줄 조회 메뉴에서 필요한 List
			Set<String> partnerList = new HashSet<>();
			for(Schedule s: scheduleList)
				partnerList.add(s.getMemberName());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("\n<<스케줄 목록 조회 중 예외 발생>>\n");
		}

		
		System.out.println("\n[스케줄 상세조회]\n");
			System.out.println("1. (훈련/산책)별 스케줄 조회");
			System.out.println("2. 파트너별 스케줄 조회");
			System.out.println("3. 전체 스케줄 조회");
			
			System.out.print("번호 선택 : ");
			int input = sc.nextInt();
			sc.nextLine();
			
			
			switch(input) {
			case 1 : selectScheduleByType(); break; // ok
			case 2 : selectScheduleByPartner(); break; // ok
			case 3 : selectAllSchedule(); break; //ok
			case 0:
				System.out.println("[상위 메뉴로 이동합니다]\n");
				break;
			default:
				System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}
			

			bookSchedule();
	}

	
	
	/**
	 * 스케줄 검색 메뉴
	 */
	private void searchMenu() {
		

		System.out.println("\n\n******스케줄 검색 메뉴*******");
		System.out.println("1. 날짜와 시간대로 스케줄 검색"); // ok
		System.out.println("2. 시간대별 스케줄 검색"); // ok
		System.out.println("0. 메뉴 나가기");

		System.out.print("번호 선택 : ");
		input = sc.nextInt();
		sc.nextLine();

		switch (input) {
		case 1:
			searchScheduleByDate();
			break;
		case 2:
			searchScheduleByTime();
			break;
		case 0:
			System.out.println("[메인 메뉴로 이동합니다]\n");
			break;
		default:
			System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
		}

		
		while(true) {
			System.out.println("\n스케줄을 예약하시겠습니까?(Y / N) : \n");
			String isBook = sc.nextLine();
			if(isBook.equals("Y"))
				bookSchedule();
			else if(isBook.equals("N")) break;
			else System.out.println("\n<<(Y / N)만 입력해주세요>>\n");
		}
	}

	/**
	 * (훈련/산책)별 스케줄 조회
	 */
	private void selectScheduleByType() {

		try {
			System.out.print("\n(훈련/산책) 선택 : \n");
			String serviceType = sc.nextLine();
			while (true) {
				if (serviceType.equals("훈련") || serviceType.equals("산책")) {
					List<Schedule> typeScheduleList = service.selectAllSchedule(serviceType);
					printAllSchedule(typeScheduleList);
					break;
				} else
					System.out.println("\n[(훈련/산책)중에서만 선택해주세요]\n");
			}
		} catch(Exception e) {
			System.out.println("\n<<스케줄 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}

	}


	/**
	 * 파트너별 스케줄 조회
	 */
	private void selectScheduleByPartner() {

		System.out.print("\n[파트너 명단]\n");
		System.out.print(" | ");
		for(String s : partnerList) 
			System.out.print(s + " | ");
		
		System.out.println("\n조회할 파트너 이름을 입력하세요 : \n");
		String partner = sc.nextLine();
		while(true) {
			if(partnerList.contains(partner)) {
				try {
					List<Schedule> PartnerScheduleList = service.selectScheduleByPartner(partner);
					printAllSchedule(PartnerScheduleList);
				} catch(Exception e) {
					System.out.println("\n<<파트너 목록 조회 중 예외 발생>>\n");
					e.printStackTrace();
				}
				break;
			} else
				System.out.println("\n[파트너 명단에 있는 이름만 입력해주세요]\n");
		}
	}
	

	/**
	 *  전체 스케줄 확인
	 */
	private void selectAllSchedule() {

		System.out.println("\n[전체 스케줄 확인]\n");

		try {
			List<Schedule> scheduleList = service.selectAllSchedule();
			printAllSchedule(scheduleList);
			
			// 스케줄 조회 메뉴에서 필요한 List
			partnerList = new HashSet<>();
			for(Schedule s: scheduleList)
				partnerList.add(s.getMemberName());
			
			
			System.out.println("\n[스케줄 상세조회]\n");
			System.out.println("1. (훈련/산책)별 스케줄 조회");
			System.out.println("2. 파트너별 스케줄 조회");
			System.out.println("3. 전체 스케줄 조회");
			
			System.out.print("번호 선택 : ");
			int input = sc.nextInt();
			sc.nextLine();
			
			
			switch(input) {
			case 1 : break;
			case 2 : selectScheduleByPartner(partnerList); break;
			case 3 : selectAllSchedule(); break;
			case 0:
				System.out.println("[상위 메뉴로 이동합니다]\n");
				break;
			default:
				System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}
			
			
			while(true) {
				if(input == 1) { 
					while(true) {
						System.out.print("\n(훈련/산책) 선택 : \n");
						String serviceType = sc.nextLine();
						if(serviceType.equals("훈련") || serviceType.equals("산책")) {
							List<Schedule> typeScheduleList =  service.selectAllSchedule(serviceType);
							printAllSchedule(typeScheduleList);
							break;
						}
						else
							System.out.println("\n[(훈련/산책)중에서만 선택해주세요]\n");	
					}
					break;
				}
				
			}

		} catch (Exception e) {
			System.out.println("\n<<스케줄 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}


	
	
	
	/**
	 *  시간대별 스케줄 검색
	 */
	private void searchScheduleByTime() {

		System.out.println("\n[시간대별 스케줄 검색]\n");
		
		while(true) {
			
			System.out.println("시작 시간 :");
			System.out.print("(9 / 11 / 13 / 15 / 17)시 중에서 선택 ..(숫자만) : ");
			int scheduleTime = sc.nextInt();
			sc.nextLine();
			
			if((scheduleTime == 9) ||
					(scheduleTime == 11) ||
					(scheduleTime == 13) ||
					(scheduleTime == 15) ||
					(scheduleTime == 17)) {
				try {
					List<Schedule> scheduleList = service.searchScheduleByTime(scheduleTime);
					System.out.println("\n[입력하신 시간대의 스케줄 조회]\n");
					
					printAllSchedule(scheduleList);
					
				} catch (Exception e) {
					System.out.println("\n<<스케줄 목록 조회 중 예외 발생>>\n");
					e.printStackTrace();
				}
				break;
			} else {
				System.out.println("\n<<(9 / 11 / 13 / 15 / 17)시 중에서만 선택해주세요(숫자)>>\n");
			}
			
		}	
	}


	/**
	 * 날짜로 스케줄 검색
	 */
	private void searchScheduleByDate() {

		System.out.println("\n[날짜와 시간대로 스케줄 검색]\n");
		
		String scheduleTime = "";
		try {
			System.out.print("연도 입력(숫자만) : ");
			int sYear = sc.nextInt();
			if(sYear/1000 == 1)
				scheduleTime += "20" + sYear;
			else
				scheduleTime += sYear;
			
			System.out.print("월 입력(숫자만) : ");
			int sMonth = sc.nextInt();
			if(sMonth >= 0 && (sMonth/10 == 0))
				scheduleTime += "0" + sMonth;
			else
				scheduleTime += sMonth;
			
			System.out.print("일 입력(숫자만) : ");
			int sDay = sc.nextInt();
			if(sDay >= 0 && (sDay/10 == 0))
				scheduleTime += "0" + sDay;
			else
				scheduleTime += sDay;
			
			System.out.println("scheduleTime" + scheduleTime);

		} catch(InputMismatchException e) {
			System.out.println("\n<<숫자만 입력해주세요>>\n");
		}
		
		try {
			List<Schedule> scheduleList = service.searchScheduleByDate(scheduleTime);
			System.out.println("\n[입력하신 날짜 이후의 스케줄 조회]\n");
			printAllSchedule(scheduleList);
			
		} catch (Exception e) {
			System.out.println("\n<<스케줄 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 스케줄 예약하기
	 */
	private void bookSchedule() {
		
		while(true) {
			System.out.println("\n스케줄을 예약하시겠습니까?(Y / N) : \n");
			String isBook = sc.nextLine().toUpperCase();
			if (isBook.equals("Y")) {
				
				System.out.println("\n예약하고자 하는 스케줄 번호를 입력하세요 : \n");
				int scheduleNo = sc.nextInt();
				sc.nextLine();
				try {
					int result1 = service.bookSchedule1(scheduleNo);
					int result2 = service.bookSchedule2(MainView.loginMember.getMemberNo(), scheduleNo);
					if (result1 * result2 > 0) {
						List<Schedule> scheduleList = service.selectScheduleByNo(scheduleNo);
						Schedule s = scheduleList.get(1);
						System.out.printf("\n****%d번(%s / %s 파트너) %s | 예약되었습니다 멍멍!****\n\n", s.getScheduleNo(),
								s.getServiceType(), s.getMemberName(), s.getScheduleTime());

					} else {
						System.out.println("\n[이미 예약된 스케줄입니다]\n");
						break;
					}

				} catch (Exception e) {
					System.out.println("\n<<스케줄 목록 조회 중 예외 발생>>\n");
					e.printStackTrace();
				}
				if (isBook.equals("N"))
					break;
				else
					System.out.println("\n<<(Y / N)만 입력해주세요>>\n");
			}
		}

		
	}

	/** 전체 스케줄 출력 메서드
	 * @param scheduleList
	 */
	private void printAllSchedule(List<Schedule> scheduleList) {
		
		for(Schedule s : scheduleList) {
			int scheduleNo = s.getScheduleNo();
			String scheduleTime = s.getScheduleTime();
			String serviceType = s.getServiceType();
			String partnerName = s.getMemberName();
			String isBook = s.getIsBook();
			
			System.out.println(scheduleNo + " | " + serviceType + " | " + scheduleTime + " | 예약여부  : " + isBook
					 + " | 파트너 : " + partnerName);
			System.out.println("-----------------------------------------------------");

			System.out.println();
		}
		
	}

}
