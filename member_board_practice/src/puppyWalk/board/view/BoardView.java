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
			case 5: selectOneBoard(); break;
			case 0: System.out.println("[메인 메뉴로 이동합니다]\n"); break;
			default: System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}
	




	// ------------------------------------------------------------	
	




	/** 게시판 목록조회
	 * @param loginMember
	 */
	public void selectAllBoard() {
		
		System.out.println("[게시판 목록조회]");
		List<Board> boardList = new ArrayList<>();

		try {
			boardList = service.selectAllBoard();
			for(Board board : boardList) {
				printOneBoard(board);
				
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 조회 중 오류 발생>>\n");
			e.printStackTrace();
		}
	}
	


	/**
	 * 내가 쓴 글 조회
	 */
	private void selectMyBoard() {
		
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
	
	
	/**
	 * 게시글 수정
	 */
	private void updateBoard() {

		System.out.println("[게시글 수정]");
		Board board = new Board();

		System.out.print("게시글 번호 : ");
		int  boardNo = sc.nextInt();

		try {
			board = service.selectBoard(boardNo, loginMember.getMemberId());
			printOneBoard(board);
			
			System.out.print("수정할 게시글 제목 : ");
			String boardTitle = sc.nextLine();
			
			System.out.print("수정할 게시글 내용 : ");
			String boardContent = sc.nextLine();
			
			if (board != null) {
				try {
					int result = service.updateBoard(boardNo, boardTitle, boardContent);
					if (result > 0)
						System.out.println("\n[게시글 수정 완료]\n");
					else
						System.out.println("\n[게시글 수정 실패]\n");
				} catch (Exception e) {
					System.out.println("\n[게시글 수정 중 오류 발생]\n");
					e.printStackTrace();
				}
				
			}
			else
				System.out.println("\n[게시글을 찾지 못했습니다]\n");
		} catch (Exception e) {
			System.out.println("\n[게시글을 찾지 못했습니다]\n");
			e.printStackTrace();
		}
	}

	/**
	 * 게시글 삭제
	 */
	private void deleteBoard() {
		

		System.out.println("[게시글 삭제]");
		System.out.print("게시글 번호 : ");
		int  boardNo = sc.nextInt();

		try {
			Board board = service.selectBoard(boardNo, loginMember.getMemberId());
			printOneBoard(board);

			
			if (board != null) {
				
				while(true) {
					System.out.print("정말 이 게시글을 삭제하겠습니까?(Y / N) : ");
					String isDelete = sc.next().toUpperCase();	
					
					if(isDelete.equals("Y")) {
						try {
							int result = service.deleteBoard(boardNo);
							if (result > 0)
								System.out.println("\n[게시글 삭제 완료]\n");
							else
								System.out.println("\n[게시글 삭제 실패]\n");
						} catch (Exception e) {
							System.out.println("\n[게시글 삭제 중 오류 발생]\n");
							e.printStackTrace();
						}
					} else 
						System.out.println("(Y/N)만 입력해주세요");
				}

			} else
				System.out.println("\n[게시글을 찾지 못했습니다]\n");
			
		} catch (Exception e) {
			System.out.println("\n[게시글을 찾지 못했습니다]\n");
			e.printStackTrace();
		}	
		
	}
	
	/**
	 * 게시글 번호를 입력받아 게시글 하나 보여주기(남의 게시글도)
	 */
	private void selectOneBoard() {
		// TODO Auto-generated method stub
		
	}


// ---------------------------------------------------------------------

	/** 게시글 출력 메서드
	 * @param boardList
	 */
	private void printBoardList(List<Board> boardList) {
		
		if (!boardList.isEmpty()) {
			for(Board board : boardList) {
				System.out.println("\n-------------------------------------");
				System.out.print("게시글 번호 : " + board.getBoardNo() + "  |  ");
				System.out.println("게시글 조회수 : " + board.getBoardContentViews());
				System.out.println("제목 : " + board.getBoardTitle());
				System.out.println("내용 : " + board.getBoardContent());
				System.out.println("게시글 등록일 : " + board.getEnrollDate());
				System.out.println("-------------------------------------\n");
			}

		} else
			System.out.println("\n[내가 작성한 글이 없습니다]\n");
		
	}

	/** 게시글 하나의 내용 출력 메서드
	 * @param board
	 */
	private void printOneBoard(Board board) {
		System.out.println("\n-------------------------------------");
		System.out.print("게시글 번호 : " + board.getBoardNo() + "  |  ");
		System.out.println("게시글 조회수 : " + board.getBoardContentViews());
		System.out.println("제목 : " + board.getBoardTitle());
		System.out.println("게시글 등록일 : " + board.getEnrollDate());
		System.out.println("-------------------------------------\n");
	}
	

}
