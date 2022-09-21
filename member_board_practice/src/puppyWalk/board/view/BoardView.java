package puppyWalk.board.view;

import static puppyWalk.common.JDBCTemplete.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import puppyWalk.board.model.service.BoardService;
import puppyWalk.board.vo.Board;
import puppyWalk.member.vo.Member;

public class BoardView {

	private BoardService service = new BoardService();
	private Scanner sc = new Scanner(System.in);
	private Member loginMember = null;
	private int input = -1;
	
	
	public void mainMenu(Member loginMember) {
		this.loginMember = loginMember;
		
		do {
			System.out.println("\n\n******게시판 기능*******");
			System.out.println("1. 게시판 목록조회");
			System.out.println("2. 게시글 상세조회");
			System.out.println("3. 게시글 수정");
			System.out.println("4. 게시글 삭제");
			System.out.println("5. 게시글 검색");
			System.out.println("0. 메뉴 나가기");

			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1: selectAllBoard(); break;
			case 2: selectMyBoard(); break;
			case 3: updateBoard(); break;
			case 4: deleteBoard(); break;
			case 5: searchBoard(); break;
			case 0: System.out.println("[메인 메뉴로 이동합니다]\n"); break;
			default: System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}
	
	
	
// ------------------------------------------------------------	
	/** 내가 쓴 글 조회
	 * @param loginMember
	 */
	public void selectMyBoard() {
		
		System.out.println("[내가 쓴 글 조회]");
		List<Board> boardList = new ArrayList<>();

		try {
			boardList = service.selectMyBoard(loginMember.getMemberId());
			printBoardList(boardList);
			
		} catch (Exception e) {
			System.out.println("\n<<내가 작성한 글 조회 중 오류 발생>>\n");
			e.printStackTrace();
		}
	}


	private void printBoardList(List<Board> boardList) {
		
		if (!boardList.isEmpty()) {
			
			
			
		} else
			System.out.println("\n[내가 작성한 글이 없습니다]\n");
		
	}


	

}
