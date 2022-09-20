package edu.kh.jdbc.member.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.main.view.MainView;
import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.vo.Member;

/*
 * 상하분할 : ctrl + shift + -
 * 좌우분할 : ctrl + shift + [
 */

/** 회원 메뉴 입/출력 클래스
 * @author User
 *
 */
public class MemberView {

	// 회원 관련 서비스를 제공하는 객체 생성
	private MemberService service = new MemberService();
	private Scanner sc = new Scanner(System.in);
	private static Member loginMember = null;
	int input = -1;
	
	/**
	 * 회원 기능 메뉴
	 * @param loginMember(로그인된 회원 정보)
	 */
	public void memberMenu(Member loginMember) {
		
		// 전달받은 loginMember 정보를 필드에 저장
		this.loginMember = loginMember;
		do {
			try {
				
				System.out.println("\n******회원 기능******");
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 회원 목록 조회");
				System.out.println("3. 내 정보 수정");
				System.out.println("4. 비밀번호 변경");
				System.out.println("5. 회원 탈퇴");
				System.out.println("0. 메인메뉴로 이동");
				
				System.out.print("\n메뉴 선택 >>");
				input = sc.nextInt();
				System.out.println();
				
				
				switch(input) {
				
				case 1 : selectMyInfo(); break;
				case 2 : selectAll(); break;
				case 3 : updateMember(); break;
				case 4 : updatePassword(); break;
				case 5 : secession(); break;
				case 0 : System.out.println("[메인 메뉴로 이동합니다]\n"); break;
				default : System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
				
				
				}
				
				
				
				
				
				
			} catch(InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
				sc.nextLine();
			}
		} while(input != 0);
	}





	/**
	 * 내 정보 조회
	 */
	private void selectMyInfo() {
		
		System.out.println("\n[내 정보 조회]\n");
		
		System.out.println("회원 번호 : " + loginMember.getMemberNo());
		System.out.println("아이디 : " + loginMember.getMemberId());
		System.out.println("이름 : " + loginMember.getMemberName());
		System.out.println("성별 : " + (loginMember.getMemberGender() == "M"? "남" : "여"));
		System.out.println("가입일 : " + loginMember.getEnrollDate());
	}
	
	/**
	 * 회원 목록 조회
	 */
	private void selectAll() {
		
		System.out.println("\n[회원 목록 조회]\n");	
		// DB에서 회원 목록 조회(탈퇴 회원 미포함)
		// 가입일 내림차순
		try {
			List<Member> memberList = service.selectAll();
			printMembers(memberList);
		} catch(Exception e) {
			System.out.println("<<회원 목록 조회 중 예외 발생>>");
			e.printStackTrace();
		}
		
	}

	/**
	 * 내 정보 수정
	 */
	private void updateMember() {
		
		System.out.println("\n[내 정보 수정]\n");	
		
		try {
			System.out.print("변경할 이름 : ");
			String memberName = sc.next();
			String memberGender;
			
			while(true) {
				System.out.print("변경할 성별(M / F) : ");
				memberGender = sc.next().toUpperCase();
				if(memberGender.equals("M") || memberGender.equals("F")) break;
				else	System.out.println("<< (M / F)만 입력해주세요 >>");
			}
			
			Member member = new Member();
			member.setMemberNo(loginMember.getMemberNo());
			member.setMemberName(memberName);
			member.setMemberGender(memberGender);
			
			int result = service.updateMember(member);
			if(result > 0) {
				// loginMember에 저장된 값과 DB에 저장된 값을 동기화하는 작업이 필요하다
				loginMember.setMemberName(memberName);
				loginMember.setMemberGender(memberGender);
				
				System.out.println("[내 정보 수정 완료]");
				}
			else		System.out.println("[내 정보 수정되지 않음]");
		} catch(Exception e) {
			System.out.println("<<회원정보 수정 중 예외 발생>>");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 비밀번호 변경
	 */
	private void updatePassword() {
		
		System.out.println("\n[비밀번호 변경]\n");	
		String newPw1;
		try {
			System.out.print("현재 비밀번호 : ");
			String currentPw = sc.next();
			
			while(true) {
				System.out.print("새 비밀번호 : ");
				newPw1 = sc.next();
				
				System.out.print("비밀번호 확인 : ");
				String newPw2 = sc.next();
				
				if(newPw1.equals(newPw2)) break;
				else System.out.println("[새 비밀번호가 일치하지 않습니다]");
			}
			

			
			int result = service.updatePassword(currentPw, newPw1, loginMember.getMemberNo());
			if(result > 0) System.out.println("\n[비밀번호 변경 완료]\n");
			else	System.out.println("\n[비밀번호가 일치하지 않습니다]\n");
			
		} catch(Exception e) {
			System.out.println("<<비밀번호 변경 중 예외 발생>>");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 회원 탈퇴
	 */
	private void secession() {

		System.out.println("\n[회원 탈퇴]\n");	
		
		
		try {
			System.out.print("현재 비밀번호 입력 : ");
			String currentPw = sc.next();
			
			while(true) {
				System.out.print("정말 탈퇴하시겠습니까? (Y/N) : ");
				String str = sc.next().toUpperCase();
				
				if(str.equals("Y")) { // 서비스 호출 후 결과 반환받기
					int result = service.secession(currentPw, loginMember.getMemberNo());
					if(result > 0) {
						System.out.println("\n[회원 탈퇴 완료]\n"); 
						input = 0;
						MainView.loginMember = null;
						break;
					}
					else {
						System.out.println("\n[비밀번호가 일치하지 않습니다]\n");
						break;
					}
					

				} else if(str.equals("N")) {
					System.out.println("\n[취소되었습니다]\n");
					break;
				} else {
					System.out.println("\n[잘못 입력하셨습니다. (Y/N)만 입력해주세요]\n");
				}
			}
			
			
		} catch(Exception e) {
			System.out.println("<<회원 탈퇴 중 예외 발생>>");
		}
		
	}


	private void printMembers(List<Member> memberList) {
		
		if(!memberList.isEmpty()) {
			for(Member member: memberList) {
				System.out.printf("[아이디 : %10s ] [이름 : %5s ] [성별 : %s ]\n", 
						member.getMemberId(), member.getMemberName(), member.getMemberGender());
			}
			System.out.println();
		}
		else
			System.out.println("\n[조회할 회원이 없습니다]\n");

	}
}
