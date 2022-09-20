package edu.kh.jdbc.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.view.MemberView;
import edu.kh.jdbc.member.vo.Member;


// 메인화면
public class MainView {

	private Scanner sc = new Scanner(System.in);
	private MainService service = new MainService();
	public static Member loginMember = null; // 로그인된 회원정보 저장한 객체를 참조하는 참조변수
	// 로그인x-> null, 로그인0 -> !null
	
	// 회원 기능 메뉴 객체 생성
	private MemberView memberView = new MemberView();

	/**
	 * 메인 메뉴 출력 메서드
	 */
	public void mainMenu() {

		int input = -1;

		do {
			try {
				// ctrl + shift + f : 화면 줄맞춤 정렬
				// ctrl + shift + p : 괄호 시작/끝 이동
				// 로그인 x화면
				if (loginMember == null) {
					System.out.println("\n\n******회원제 게시판 프로그램*******");
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("0. 프로그램 종료");

					System.out.print("\n메뉴 선택 >>");
					input = sc.nextInt();
					sc.nextLine(); // 입력버퍼의 개행문자 제거
					System.out.println();

					switch (input) {
					case 1:
						login();
						break;
					case 2:
						signUp();
						break;
					case 0:
						System.out.println("<<프로그램 종료>>");
						break;
					default:
						System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
					}

				} else { // 로그인 0
					
					
					System.out.println("\n\n******로그인 메뉴*******");
					System.out.println("1. 회원 기능");
					System.out.println("2. 게시판 기능");
					System.out.println("0. 로그아웃");
					System.out.println("99. 프로그램 종료");

					System.out.print("\n메뉴 선택 >>");
					input = sc.nextInt();
					sc.nextLine(); // 입력버퍼의 개행문자 제거
					System.out.println();

					switch (input) {
					case 1:
						memberView.memberMenu(loginMember);
						break;
					case 2:
						signUp();
						break;
					case 0:
						System.out.println("<<로그아웃>>");
						loginMember = null;
						break;
					case 99:
						System.out.println("<<프로그램 종료>>");
						input = 0;
						break;
					default:
						System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
					}
				}

			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
				sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자열 제거
			}

		} while (input != 0);

	}



	private void login() {

		System.out.println("[로그인]");

		System.out.print("\n아이디 입력 : ");
		String memberId = sc.next();

		System.out.print("\n비밀번호 입력 : ");
		String memberPw = sc.next();

		// 로그인 서비스 호출 후 조회 결과를 loginMember에 저장
		try {
			loginMember = service.login(memberId, memberPw);
			if (loginMember != null) {
				System.out.println("\n" + loginMember.getMemberName() + "님, 환영합니다!\n");
			}
			else // 로그인 실패시
				System.out.println("<<아이디가 없거나 비밀번호가 일치하지 않습니다>>");
		} catch(Exception e) {
			System.out.println("\n<<로그인 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}

	/**
	 * 회원가입 화면
	 */
	private void signUp() { // MainView안에서만 실행되는 기능이므로 ->private

		System.out.println("[회원가입]");

		String memberId = null;
		String memberPw1 = null;
		String memberPw2 = null;
		String memberName = null;
		String memberGender = null;

		try {

			// 아이디를 입력받아 중복이 아닐때까지 반복
			while (true) {
				System.out.print("\n아이디 입력 : ");
				memberId = sc.next();

				// 입력받은 아이디를 매개변수로 전달하여
				// 중복여부를 검사하는 서비스 호출 후 결과 반환받기(1/0)

				int result = service.idDupCheck(memberId);
				if (result == 0) { // 중복이 아닌 경우
					System.out.println("[사용 가능한 아이디 입니다.]\n");
					break;
				} else {
					System.out.println("[이미 사용중인 아이디 입니다]\n");
				}
			}

			// 비밀번호와 비밀번호 확인이 일치할때까지 무한반복
			while (true) {
				System.out.print("\n비밀번호 입력 : ");
				memberPw1 = sc.next();
				System.out.print("비밀번호 확인 : ");
				memberPw2 = sc.next();

				if (!memberPw1.equals(memberPw2)) // 일치x
					System.out.println("[비밀번호가 일치하지 않습니다]");
				else {
					System.out.println("[사용 가능한 비밀번호입니다]");
					break;
				}
			}

			System.out.print("\n이름 입력 : ");
			memberName = sc.next();

			while (true) {
				System.out.print("\n성별 입력 : ");
				memberGender = sc.next().toUpperCase(); // 대문자 변경

				if (memberGender.equals("M") || memberGender.equals("F"))
					break;
				else
					System.out.println("[(M/F)만 입력해주세요]\n");
			}

			// 하나의 VO에 담아서 service 호출 후 결과 반환받기
			Member member = new Member(memberId, memberPw1, memberName, memberGender);
			int result = service.signUp(member);

			// 서비스 처리 결고에 따른 출력 화면 제어
			if (result > 0)
				System.out.println("******회원가입 성공******");
			else
				System.out.println("<<회원 가입 실패>>");

		} catch (Exception e) {
			System.out.println("<<회원가입 중 예외 발생>>");
			e.printStackTrace();
		}

	}

}
