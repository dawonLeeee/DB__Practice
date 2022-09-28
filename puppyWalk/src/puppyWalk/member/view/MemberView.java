package puppyWalk.member.view;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import puppyWalk.board.model.service.BoardService;
import puppyWalk.board.view.BoardView;
import puppyWalk.board.vo.Board;
import puppyWalk.dog.vo.Dog;
import puppyWalk.main.model.service.MainService;
import puppyWalk.main.view.MainView;
import puppyWalk.member.model.service.MemberService;
import puppyWalk.member.vo.Member;
import puppyWalk.schedule.vo.Schedule;

public class MemberView {

	private Scanner sc = new Scanner(System.in);
	private MemberService service = new MemberService();
	private BoardView boardView = new BoardView();
	
	private int input;

	public void memberMenu() {
		
		do {
			System.out.println("\n\n******회원 메뉴*******");
			System.out.println("1. 내 정보 조회");
			System.out.println("2. 내 정보 수정");
			System.out.println("3. 비밀번호 변경");
			System.out.println("4. 회원 탈퇴");
			System.out.println("0. 메뉴 나가기");

			System.out.print("\n메뉴 선택 : ");
			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1: selectMyInfo(); break; //ok
			case 2: updateMyInfo(); break; //ok
			case 3: updatePassword(); break; //ok
			case 4: secession(); break; // ok
			case 0: System.out.println("[메인 메뉴로 이동합니다]\n"); break;
			default: System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}
	

	

	public void partnerMenu() {
		do {
			System.out.println("\n\n******파트너 메뉴*******");
			System.out.println("1. 내 정보 조회"); //ok
			System.out.println("2. 내 정보 수정");
			System.out.println("3. 비밀번호 변경"); // ok
			System.out.println("4. 스케줄 등록/수정하기");
			System.out.println("5. 회원 탈퇴");
			System.out.println("0. 로그아웃");
			System.out.println("99. 프로그램 종료");

			System.out.print("\n메뉴 선택 : ");
			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1: selectPartnerInfo(); break;
			case 2: updatePartnerInfo(); break;
			case 3: updatePassword(); break;
			case 4: updateSchedule(); break;
			case 5: secession(); break;
			case 0:
				MainView.loginMember = null;
				System.out.println("\n[로그아웃 되었습니다]\n");
				input = -1; 
				break;
			case 99: 
				System.out.println("<<프로그램 종료>>");
				System.exit(0);
			default: System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}

	





// ------------------------------------------------------------------------------------

	
	
	




	/**
	 *  스케줄 등록하기
	 */
	private void updateSchedule() {

		System.out.println("[스케줄 등록하기]");
		

		System.out.print("\n날짜 입력(YYYY-MM-DD) : \n");
		String scheduleTime = sc.nextLine();
		
		int sTime;
		while(true) {
			System.out.print("\n시간 입력(숫자만 : 9/11/13/15/17중 선택) : \n");
			sTime = sc.nextInt(); sc.nextLine();
			if(sTime==9 || sTime==11 || sTime==13 || sTime==15 || sTime==17) {
				break;
			} else
				System.out.print("\n[(숫자만 : 9/11/13/15/17중 선택)해주세요]\n");
		}
		
		String serviceType;
		while(true) {
			System.out.print("\n서비스 타입 선택(훈련/산책) : \n");
			serviceType = sc.nextLine();
			if(serviceType.equals("훈련") || serviceType.equals("산책")) {
				break;
			} else
				System.out.print("\n[(훈련/산책)중에서만 입력해주세요]\n");
		}
		
		scheduleTime +=  " " + Integer.toString(sTime) + ":00:00";
		
		
		Schedule mySchedule = new Schedule();
		mySchedule.setScheduleTime(scheduleTime);
		mySchedule.setServiceType(serviceType);
		
		try {
			int result = service.updateSchedule(MainView.loginMember.getMemberNo(), mySchedule);
			if (result > 0) {
				System.out.println("\n[내 스케줄 등록 완료]\n");
			}
			else
				System.out.println("\n[내 스케줄 등록 실패]\n");
		} catch (Exception e) {
			System.out.println("\n<<내 스케줄 등록 중 오류 발생>>\n");
			e.printStackTrace();
		}
		
		
	}




	/**
	 * 내 정보 조회
	 */
	private void selectMyInfo() {

		System.out.println("\n[내 정보 조회]\n");
		
		System.out.println("===========================================");
		System.out.println("회원 번호 : " + MainView.loginMember.getMemberNo());
		System.out.println("아이디 : " + MainView.loginMember.getMemberId());
		System.out.println("이름 : " + MainView.loginMember.getMemberName());
		System.out.println("성별 : " + (MainView.loginMember.getMemberRRNumber().charAt(7) == 'M' ? "남" : "여"));
		System.out.println("가입일 : " + MainView.loginMember.getEnrollDate());
		System.out.println("===========================================");
		try {
			Dog dogInfo = service.selectDogInfo(MainView.loginMember.getDogNo());
			
			System.out.println("반려견 이름 : " + dogInfo.getDogName());
			System.out.println("반려견 종 : " + dogInfo.getDogVar());
			System.out.println("반려견 나이 : " + (dogInfo.getDogAge()==0? "정보없음" : dogInfo.getDogAge()));
			System.out.println("반려견 성별 : " + (dogInfo.getDogGender().equals("M")? "수컷" : "암컷"));
			System.out.println("반려견 소개 : " + dogInfo.getDogComment());
			System.out.println("반려견 주의사항 : " + dogInfo.getDogAlert());
			
			
		} catch(Exception e) {
			System.out.println("\n[스케줄 조회 중 예외 발생]\n");
			e.printStackTrace();
		}
		
		System.out.println("===========================================");
		System.out.println("나의 예약 : ");
		try {
			Map<Schedule, String> scheduleMap = service.selectMySchedule(MainView.loginMember.getMemberNo());
			
			if(scheduleMap.isEmpty())
				System.out.println("\n[스케줄이 비어 있습니다]\n");
			else {
				for(Schedule schedule : scheduleMap.keySet()) {
					System.out.print("스케줄 번호 : " + schedule.getScheduleNo() + " | ");
					System.out.println("예약 시간 : " + schedule.getScheduleTime());
					System.out.print("서비스 분류 : " + schedule.getServiceType() + " | ");
					System.out.println("파트너 : " + scheduleMap.get(schedule));
				}
			}
			System.out.println("===========================================");
			
		} catch(Exception e) {
			System.out.println("\n[스케줄 조회 중 예외 발생]\n");
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 내 정보 수정
	 */
	private void updateMyInfo() {
		
		System.out.println("[내 정보 수정]");
		String memberName = "";
		String partnerIntro = "";
		
		input = -1;
		do {
			try {
				System.out.println("1. 이름 변경");
				System.out.println("2. 내 반려견 정보 변경");
				System.out.println("0. 내 정보 변경 완료");

				System.out.print("\n메뉴 선택 >>");
				input = sc.nextInt();
				sc.nextLine(); // 입력버퍼의 개행문자 제거
				System.out.println();
				
				

				switch (input) {
				case 1: // 이름 변경
					System.out.print("변경할 이름 : ");
					memberName = sc.nextLine();
					break;
				case 2: // 내 반려견 정보 변경
					updateDogInfo();
					break;
				case 0 : break;
				default:
					System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
				}

			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
				sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자열 제거
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("\n<<내 정보 수정 중 오류 발생>>\n");
			}
			
			if(input == 1) {
				try {
					int result = service.updateMyInfo(MainView.loginMember.getMemberNo(), memberName);
					if (result > 0) {
						MainView.loginMember.setMemberName(memberName);
						System.out.println("\n[내 정보 수정 완료]\n");
					} else
						System.out.println("\n[내 정보 수정 실패]\n");
				} catch (Exception e) {
					System.out.println("\n<<내 정보 수정 중 오류 발생>>\n");
					e.printStackTrace();
				}
			}

		} while (input != 0);
	}
	



	/**
	 * 비밀번호 변경
	 */
	private void updatePassword() {

		int result = 0;

		
		System.out.print("\n현재 비밀번호 입력 : ");
		String memberPw = sc.next();
		String newPw1;

		try {

			while(true) {
				System.out.print("새 비밀번호 입력 : ");
				newPw1 = sc.next();
				
				System.out.print("비밀번호 확인 : ");
				String newPw2 = sc.next();
				
				if(newPw1.equals(newPw2)) break;
				else	System.out.println("\n[비밀번호가 일치하지 않습니다]\n");
			}
				
				result = service.updatePassword(MainView.loginMember.getMemberNo(), memberPw, newPw1);
				if (result > 0)
					System.out.println("\n[비밀번호 변경 완료]\n");
				else
					System.out.println("\n[현재 비밀번호와 일치하지 않습니다]\n");

			

		} catch (Exception e) {
			System.out.println("\n<<비밀번호 수정 중 오류 발생>>\n");
			e.printStackTrace();
		}

	}

	/**
	 * 회원 탈퇴
	 */
	private void secession() {

		System.out.println("\n[회원 탈퇴]\n");
		System.out.print("정말로 탈퇴하겠습니까?(Y/N) : ");
		String isSecession = sc.next().toUpperCase();
		while(true) {
			if (isSecession.equals("Y")) {
	
				try {
					service.secession(MainView.loginMember);
					System.out.println("[회원 탈퇴 완료]");
					input = 0;
					MainView.loginMember = null;
				} catch (Exception e) {
					System.out.println("회원 탈퇴 중 오류 발생");
					e.printStackTrace();
				}
				break;
			}else if(!isSecession.equals("N")) {
				System.out.println("\n[(Y/N)만 입력해주세요]\n");
			} else { // 'N' 선택
				break;
			}
		}
	}

//--------------------------------------------------------------------------------
	
	/**
	 * (파트너) 내 정보 조회
	 */
	private void selectPartnerInfo() {

		System.out.println("\n[내 정보 조회]\n");
		
		
		
		System.out.println("===========================================");
		System.out.println("회원 번호 : " + MainView.loginMember.getMemberNo());
		System.out.println("아이디 : " + MainView.loginMember.getMemberId());
		System.out.println("이름 : " + MainView.loginMember.getMemberName());
		System.out.println("성별 : " + (MainView.loginMember.getMemberRRNumber().charAt(7) == 'M' ? "남" : "여"));
		System.out.println("가입일 : " + MainView.loginMember.getEnrollDate());
		System.out.println("===========================================");
		try {
			Dog dogInfo = service.selectDogInfo(MainView.loginMember.getDogNo());
			
			System.out.println("반려견 이름 : " + dogInfo.getDogName());
			System.out.println("반려견 종 : " + dogInfo.getDogVar());
			System.out.println("반려견 나이 : " + (dogInfo.getDogAge()==0? "정보없음" : dogInfo.getDogAge()));
			System.out.println("반려견 성별 : " + (dogInfo.getDogGender().equals("M")? "수컷" : "암컷"));
			System.out.println("반려견 소개 : " + dogInfo.getDogComment());
			System.out.println("반려견 주의사항 : " + dogInfo.getDogAlert());
			
			
		} catch(Exception e) {
			System.out.println("\n[스케줄 조회 중 예외 발생]\n");
			e.printStackTrace();
		}
		System.out.println("===========================================");
		
		System.out.println("나의 예약 : ");
		try {
			List<Schedule> scheduleList = service.selectPartnerSchedule(MainView.loginMember.getMemberNo(), "1");
			
			if(scheduleList.isEmpty())
				System.out.println("[예정된 예약이 없습니다]\n");
			else {
				for(Schedule schedule : scheduleList) {
					System.out.print("스케줄 번호 : " + schedule.getScheduleNo() + " | ");
					System.out.println("예약 시간 : " + schedule.getScheduleTime());
					System.out.print("서비스 분류 : " + schedule.getServiceType() + " | ");
					System.out.print("예약 여부 : " + schedule.getIsBook() + " | ");
					System.out.println("예약자 : " + schedule.getMemberName());
					System.out.println("-------------------------------------------");


				}
			}
			System.out.println("===========================================");
			///바꾸기///
			Member partnerInfo = service.selectPartnerInfo(MainView.loginMember.getMemberNo());
			
			System.out.println("파트너 소개 : " + partnerInfo.getPartnerIntro());
			int partnerGrade = partnerInfo.getPartnerGrade();
			if(partnerGrade == 0) System.out.println("파트너 평점 : 아직 아무도 평가하지 않았습니다");
			else {
				String star = "";
				for(int i = 1; i <= 5-partnerGrade; i++) {
					star += "☆";
				}
				for(int i = 1; i <= partnerGrade; i++) {
					star += "★";
				}
				System.out.println("파트너 평점 : " + star);
				}
			System.out.println("===========================================");
			
			while(true) {
				System.out.println("[지난 예약을 조회하시겠습니까? (Y / N) : ]\n");
				String isSelect = sc.nextLine().toUpperCase();
				if(isSelect.equals("Y")) {

					List<Schedule> pastScheduleList = service.selectPartnerSchedule(MainView.loginMember.getMemberNo(), "2");
					
					if(pastScheduleList.isEmpty())
						System.out.println("[지난 예약이 없습니다]\n");
					else {
						for(Schedule schedule : pastScheduleList) {
							System.out.print("스케줄 번호 : " + schedule.getScheduleNo() + " | ");
							System.out.println("예약 시간 : " + schedule.getScheduleTime());
							System.out.print("서비스 분류 : " + schedule.getServiceType() + " | ");
							System.out.print("예약 여부 : " + schedule.getIsBook() + " | ");
							System.out.println("예약자 : " + schedule.getMemberName());
							System.out.println("-------------------------------------------");


						}
					}
					break;
				} else if(isSelect.equals("N")) break;
				else	System.out.println("\n[(Y / N)중에서만 입력해주세요]\n");
			}
			
		} catch(Exception e) {
			System.out.println("\n[파트너 정보 조회 중 예외 발생]\n");
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 파트너 정보 수정
	 */
	private void updatePartnerInfo() {

		
		String memberName = "";
		String partnerIntro = "";
		
		input = -1;
		do {
			try {
				System.out.println("[내 정보 수정]");
				System.out.println("1. 이름 변경");
				System.out.println("2. 내 소개 변경");
				System.out.println("3. 내 반려견 정보 변경");
				System.out.println("0. 내 정보 변경 완료");

				System.out.print("\n메뉴 선택 >>");
				input = sc.nextInt();
				sc.nextLine(); // 입력버퍼의 개행문자 제거
				System.out.println();
				
				

				switch (input) {
				case 1: // 이름 변경
					System.out.print("변경할 이름 : ");
					memberName = sc.nextLine();
					break;
				case 2: // 내 소개 변경
					System.out.print("내 소개 변경: ");
					partnerIntro += boardView.inputContent();
					break;
				case 3: // 내 반려견 정보 변경
					updateDogInfo();
					break;
				case 0 : break;
				default:
					System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
				}

			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
				sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자열 제거
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("\n<<내 정보 수정 중 오류 발생>>\n");
			}

		
	
			if(input == 1) {
				if(!memberName.equals(""))
					try {
						int result = service.updateMyInfo(MainView.loginMember.getMemberNo(), memberName);
						
						if (result > 0) {
							MainView.loginMember.setMemberName(memberName);
							System.out.println("\n[내 이름 수정 완료]\n");
						}
						else
							System.out.println("\n[내 이름 수정 실패]\n");
					} catch (Exception e) {
						System.out.println("\n<<내 이름 수정 중 오류 발생>>\n");
						e.printStackTrace();
					}
			}
			
			if(input == 2) {
				if(!partnerIntro.equals(""))
					try {
						int result = service.updatePartnerInfo(MainView.loginMember.getMemberNo(), partnerIntro);
						
						if (result > 0) {
							MainView.loginMember.setPartnerIntro(partnerIntro);
							System.out.println("내 정보 수정 완료");
						}
						else
							System.out.println("내 정보 수정 실패");
					} catch (Exception e) {
						System.out.println("글자수 제한을 초과했습니다");
						e.printStackTrace();
					}
			}
		} while (input != 0);
	}




	/**
	 * 내 반려견 정보 수정
	 */
	public void updateDogInfo() {
		
		System.out.println("[내 반려견 정보 수정]");
		Dog dogInfo = new Dog();
		
		Dog currDogInfo = new Dog();
		try {
			currDogInfo = service.selectDogInfo(MainView.loginMember.getDogNo());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String dogName = currDogInfo.getDogName();
		String dogGender = currDogInfo.getDogGender();
		int dogAge = currDogInfo.getDogAge();
		String dogComment = currDogInfo.getDogComment();
		String dogAlert = currDogInfo.getDogAlert();
		String dogVar = currDogInfo.getDogVar();
		
		
		
		input = -1;
		do {
			try {
				System.out.println("1. 반려견 이름 변경");
				System.out.println("2. 반려견 종 변경");
				System.out.println("3. 반려견 성별 변경");
				System.out.println("4. 반려견 나이 변경");
				System.out.println("5. 반려견 소개 변경");
				System.out.println("6. 반려견 주의사항 변경");
				System.out.println("0. 반려견 정보 변경 완료");

				System.out.print("\n메뉴 선택 >>");
				input = sc.nextInt();
				sc.nextLine(); // 입력버퍼의 개행문자 제거
				System.out.println();
				
				

				switch (input) {
				case 1: // 이름 변경
					System.out.print("변경할 이름 : ");
					dogName = sc.nextLine();
					break;
				case 2: // 종 변경
					System.out.print("종 변경: ");
					dogVar = sc.nextLine();
					break;
				case 3: // 성별 변경
					while(true) {
						System.out.print("성별 변경 (M/F): ");
						dogGender = sc.nextLine();
						if(dogGender.equals("M") || dogGender.equals("F"))
							break;
						else
							System.out.println("\n[(M/F)만 입력해주세요]\n");

					}
					break;
				case 4: // 나이 변경
					System.out.print("나이 변경: ");
					dogAge = sc.nextInt();
					sc.nextLine();
					break;
				case 5: // 소개 변경
					System.out.print("소개 변경: ");
					dogComment = boardView.inputContent();
					break;
				case 6: // 주의사항 변경
					System.out.print("주의사항 변경: ");
					dogAlert = boardView.inputContent();
					break;
				case 0 : break;
				default:
					System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
				}
				
			} catch (InputMismatchException e) {
					System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
					sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자열 제거
			}
				
				
		} while (input != 0);
				
				dogInfo.setDogName(dogName);
				dogInfo.setDogAge(dogAge);
				dogInfo.setDogVar(dogVar);
				dogInfo.setDogComment(dogComment);
				dogInfo.setDogAlert(dogAlert);
				dogInfo.setDogGender(dogGender);
				
				try {
					int result = service.updateDogInfo(MainView.loginMember.getMemberNo(), dogInfo);
					if (result > 0) {
						System.out.println("내 반려견 정보 수정 완료");
					} else
						System.out.println("내 반려견 정보 수정 실패");	

				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("\n<<내 반려견 정보 수정 중 오류 발생>>\n");
				}
		}
		
	
}
