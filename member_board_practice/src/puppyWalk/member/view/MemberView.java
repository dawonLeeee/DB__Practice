package puppyWalk.member.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import puppyWalk.board.model.service.BoardService;
import puppyWalk.board.view.BoardView;
import puppyWalk.main.model.service.MainService;
import puppyWalk.main.view.MainView;
import puppyWalk.member.model.service.MemberService;
import puppyWalk.member.vo.Member;

public class MemberView {

	private Scanner sc = new Scanner(System.in);
	private MemberService service = new MemberService();
	private BoardView boardView = new BoardView();
	private Member loginMember = null;
	private int input;

	public void memberMenu(Member loginMember) {
		this.loginMember = loginMember;
		do {
			System.out.println("\n\n******회원 기능*******");
			System.out.println("1. 내 정보 조회");
			System.out.println("2. 내 정보 수정(이름)");
			System.out.println("3. 비밀번호 변경");
			System.out.println("4. 회원 탈퇴");
			System.out.println("0. 메뉴 나가기");

			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1: selectMyInfo(); break;
			case 2: updateMember(); break;
			case 3: updatePassword(); break;
			case 4: secession(); break;
			case 0: System.out.println("[메인 메뉴로 이동합니다]\n"); break;
			default: System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}

// ------------------------------------------------------------------------------------

	private void selectMyInfo() {

		int myInfo = -1;
		do {
			try {

				System.out.println("\n\n******내 정보 조회*******");
				System.out.println("1. 내 정보 확인");
				System.out.println("2. 내가 쓴 글 조회");
				System.out.println("3. 내가 쓴 댓글 조회");
				System.out.println("4. 내 판매상품 확인");
				System.out.println("5. 내 찜 목록 확인");
				System.out.println("0. 상위메뉴로 나가기");

				System.out.print("\n메뉴 선택 >>");
				input = sc.nextInt();
				sc.nextLine(); // 입력버퍼의 개행문자 제거
				System.out.println();

				switch (myInfo) {
				case 1:
					selectMyInfo();
					break;
				case 2:
					boardView.selectMyContext(loginMember);
					break;
				case 3:
					boardView.selectMyContextReply(loginMember);
					break;
				case 4:
					selectMySells();
					break;
				case 5:
					selectMyWish();
					break;
				case 0:
					break;
				default:
					System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
				}

			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
				sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자열 제거
			}

		} while (myInfo != 0);

	}

	/**
	 * 내 정보 수정(이름, 성별)
	 */
	private void updateMember() {

		System.out.println("[내 정보 수정]");
		String memberName;
		String memberGender;

		System.out.print("변경할 이름 : ");
		memberName = sc.next();

		try {
			int result = service.updateMember(loginMember.getMemberId(), memberName);
			if (result > 0)
				System.out.println("내 정보 수정 완료");
			else
				System.out.println("내 정보 수정 실패");
		} catch (Exception e) {
			System.out.println("내 정보 수정 중 오류 발생");
			e.printStackTrace();
		}
	}

	private void updatePassword() {

		int result = 0;

		System.out.print("현재 비밀번호 입력 : ");
		String memberPw = sc.next();

		try {

			if (memberPw.equals(loginMember.getMemberPw())) {

				System.out.print("새 비밀번호 입력 : ");
				String newPw = sc.next();
				result = service.updatePw(loginMember.getMemberId(), newPw);
				if (result > 0)
					System.out.println("[비밀번호 변경 완료]");
				else
					System.out.println("[비밀번호 변경 실패]");

			} else
				System.out.println("[현재 비밀번호와 일치하지 않습니다]");

		} catch (Exception e) {
			System.out.println("비밀번호 수정 중 오류 발생");
			e.printStackTrace();
		}

	}

	/**
	 * 회원 탈퇴
	 */
	private void secession() {

		System.out.println("[회원 탈퇴]");
		System.out.print("정말로 탈퇴하겠습니까?(Y/N) : ");
		String isSecession = sc.next().toUpperCase();
		if (isSecession.equals("Y")) {

			try {
				service.secession(loginMember);
				System.out.println("[회원 탈퇴 완료]");
			} catch (Exception e) {
				System.out.println("회원 탈퇴 중 오류 발생");
				e.printStackTrace();
			}
		}
	}

}
