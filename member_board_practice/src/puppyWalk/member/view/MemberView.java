package puppyWalk.member.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import puppyWalk.board.model.service.BoardService;
import puppyWalk.board.view.BoardView;
import puppyWalk.main.model.service.MainService;
import puppyWalk.main.view.MainView;
import puppyWalk.member.model.service.MemberService;
import puppyWalk.member.vo.Member;
import puppyWalk.schedule.vo.Schedule;

public class MemberView {

	private Scanner sc = new Scanner(System.in);
	private MemberService service = new MemberService();
	private BoardView boardView = new BoardView();
	private static Member loginMember = null;
	
	private int input;

	public void memberMenu(Member loginMember) {
		this.loginMember = loginMember;
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
			case 1: selectMyInfo(); break;
			case 2: updateMyInfo(); break;
			case 3: updatePassword(); break;
			case 4: secession(); break;
			case 0: System.out.println("[메인 메뉴로 이동합니다]\n"); break;
			default: System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}
	

	

	public void partnerMenu(Member loginMember) {

		this.loginMember = loginMember;
		do {
			System.out.println("\n\n******파트너 메뉴*******");
			System.out.println("1. 내 정보 조회");
			System.out.println("2. 내 정보 수정");
			System.out.println("3. 비밀번호 변경");
			System.out.println("4. 스케줄 등록하기");
			System.out.println("5. 회원 탈퇴");
			System.out.println("0. 메뉴 나가기");

			System.out.print("\n메뉴 선택 : ");
			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1: //selectMyInfo(); break;
			case 2: //updateMember(); break;
			case 3: updatePassword(); break;
			case 4: //secession(); break;
			case 5: //secession(); break;
			case 0: System.out.println("[메인 메뉴로 이동합니다]\n"); break;
			default: System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}

// ------------------------------------------------------------------------------------

	
	
	/**
	 * 내 정보 조회
	 */
	private void selectMyInfo() {

		System.out.println("\n[내 정보 조회]\n");
		
		System.out.println("===========================================");
		System.out.println("회원 번호 : " + loginMember.getMemberNo());
		System.out.println("아이디 : " + loginMember.getMemberId());
		System.out.println("이름 : " + loginMember.getMemberName());
		System.out.println("성별 : " + (loginMember.getMemberRRNumber().charAt(7) == 'M' ? "남" : "여"));
		System.out.println("가입일 : " + loginMember.getEnrollDate());
		System.out.println("===========================================");
		System.out.println("나의 예약 : ");
		try {
			Map<Schedule, String> scheduleMap = service.selectMySchedule(loginMember.getMemberNo());
			
			if(scheduleMap.isEmpty())
				System.out.println("\n[스케줄이 비어 있습니다]\n");
			else {
				for(Schedule schedule : scheduleMap.keySet()) {
					System.out.print("스케줄 번호 : " + schedule.getScheduleTime() + " | ");
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
		String memberName;

		System.out.print("변경할 이름 : ");
		memberName = sc.next();

		try {
			int result = service.updateMyInfo(loginMember.getMemberId(), memberName);
			if (result > 0) {
				loginMember.setMemberName(memberName);
				System.out.println("내 정보 수정 완료");
			}
			else
				System.out.println("내 정보 수정 실패");
		} catch (Exception e) {
			System.out.println("내 정보 수정 중 오류 발생");
			e.printStackTrace();
		}
	}
	



	/**
	 * 비밀번호 변경
	 */
	private void updatePassword() {

		int result = 0;

		System.out.println("loginMember.getMemberPw()" + loginMember.getMemberPw());
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
				
				result = service.updatePassword(loginMember.getMemberId(), memberPw, newPw1);
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
					service.secession(loginMember);
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

	

}
