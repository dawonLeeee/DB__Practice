package puppyWalk.board.view;

import static puppyWalk.common.JDBCTemplete.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import puppyWalk.board.model.service.BoardService;
import puppyWalk.board.model.service.CommentService;
import puppyWalk.board.vo.Board;
import puppyWalk.main.view.MainView;
import puppyWalk.member.vo.Member;

public class BoardView {

	private BoardService boardService = new BoardService();
	private CommentService commentService = new CommentService();
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
			case 2: selectBoard(); break;
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
	 */
	public void selectAllBoard() {
		
		System.out.println("[게시글 목록 조회]\n");
		
		try {
			List<Board> boardList = boardService.selectAllBoard();
			printAllBoard(boardList);
			
		} catch(Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 게시글 상세 조회(+ 댓글 기능)
	 */
	private void selectBoard() {

		System.out.println("[게시글 상세 조회]\n");
		
		System.out.print("게시글 번호 입력 : ");
		int boardNo = sc.nextInt();
		System.out.println();
		
		try {
			Board board = boardService.selectBoard(boardNo, MainView.loginMember.getMemberNo());
			// 내가 내 글 조회하면 조회수 증가가 안 일어나도록 설정하기 위해 필요
			printOneBoard(board);
			
		} catch(Exception e) {
			System.out.println("\n<<게시글 상세 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	
	
//	/**
//	 * 내가 쓴 글 조회
//	 */
//	private void selectBoard() {
//		
//		System.out.println("[게시글 상세 조회]\n");
//		
//		System.out.print("게시글 번호 입력 : ");
//		int boardNo = sc.nextInt();
//		System.out.println();
//		
//		try {
//			Board board = boardService.selectBoard(boardNo, MainView.loginMember.getMemberNo());
//			// 내가 내 글 조회하면 조회수 증가가 안 일어나도록 설정하기 위해 필요
//			printOneBoard(board);
//			
//		} catch(Exception e) {
//			System.out.println("\n<<게시글 상세 조회 중 예외 발생>>\n");
//			e.printStackTrace();
//		}
//	}
//	
//	
//	/**
//	 * 게시글 수정
//	 */
//	private void updateBoard() {
//
//		System.out.println("[게시글 수정]");
//		Board board = new Board();
//
//		System.out.print("게시글 번호 : ");
//		int  boardNo = sc.nextInt();
//
//		try {
//			board = boardservice.selectBoard(boardNo, loginMember.getMemberId());
//			printOneBoard(board);
//			
//			System.out.print("수정할 게시글 제목 : ");
//			String boardTitle = sc.nextLine();
//			
//			System.out.print("수정할 게시글 내용 : ");
//			String boardContent = sc.nextLine();
//			
//			if (board != null) {
//				try {
//					int result = boardservice.updateBoard(boardNo, boardTitle, boardContent);
//					if (result > 0)
//						System.out.println("\n[게시글 수정 완료]\n");
//					else
//						System.out.println("\n[게시글 수정 실패]\n");
//				} catch (Exception e) {
//					System.out.println("\n[게시글 수정 중 오류 발생]\n");
//					e.printStackTrace();
//				}
//				
//			}
//			else
//				System.out.println("\n[게시글을 찾지 못했습니다]\n");
//		} catch (Exception e) {
//			System.out.println("\n[게시글을 찾지 못했습니다]\n");
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 게시글 삭제
//	 */
//	private void deleteBoard() {
//		
//
//		System.out.println("[게시글 삭제]");
//		System.out.print("게시글 번호 : ");
//		int  boardNo = sc.nextInt();
//
//		try {
//			Board board = boardservice.selectOneBoard(boardNo, loginMember.getMemberId());
//			printOneBoard(board);
//
//			
//			if (board != null) {
//				
//				while(true) {
//					System.out.print("정말 이 게시글을 삭제하겠습니까?(Y / N) : ");
//					String isDelete = sc.next().toUpperCase();	
//					
//					if(isDelete.equals("Y")) {
//						try {
//							int result = boardservice.deleteBoard(boardNo);
//							if (result > 0)
//								System.out.println("\n[게시글 삭제 완료]\n");
//							else
//								System.out.println("\n[게시글 삭제 실패]\n");
//						} catch (Exception e) {
//							System.out.println("\n[게시글 삭제 중 오류 발생]\n");
//							e.printStackTrace();
//						}
//					} else 
//						System.out.println("(Y/N)만 입력해주세요");
//				}
//
//			} else
//				System.out.println("\n[게시글을 찾지 못했습니다]\n");
//			
//		} catch (Exception e) {
//			System.out.println("\n[게시글을 찾지 못했습니다]\n");
//			e.printStackTrace();
//		}	
//		
//	}
//	
//	/**
//	 * 게시글 번호를 입력받아 게시글 하나 보여주기(남의 게시글도)
//	 */
//	private void selectOneBoard() {
//
//		System.out.print("게시글 번호 입력 : ");
//		int boardNo = sc.nextInt();
//		try {
//			Board board = boardservice.selectOneBoard(boardNo);
//
//			if (board != null) {
//				printOneBoard(board);
//			} else
//				System.out.println("\n[게시글을 찾지 못했습니다]\n");
//			
//		} catch (Exception e) {
//			System.out.println("\n[게시글을 찾지 못했습니다]\n");
//			e.printStackTrace();
//		}	
//		
//	}


// ---------------------------------------------------------------------

	/** 게시글 하나를 출력하는 메서드
	 * @param boardList
	 */
	private void printOneBoard(Board board) {

		if(board != null) {
			
			int boardNo = board.getBoardNo();
			String boardTitle = board.getBoardTitle();
			String boardContent = board.getBoardContent();
			String createDate = board.getCreateDate();
			String memberName = board.getMemberName();
			int readCount = board.getReadCount();
			int commentCount = board.getCommentCount();

			System.out.println(boardNo + " | 작성자 : " + memberName + " | " + boardTitle);
			System.out.println(boardContent + "\n");
			System.out.print("작성일 : " + createDate + " | ");
			System.out.print("조회수 : " + readCount + " | ");
			System.out.println("댓글수 : " + commentCount);
			System.out.println("------------------------------------------");

			System.out.println();
			
		} else {
			System.out.println("\n[게시글이 존재하지 않습니다]\n");
		}
	}


	/** 게시글 리스트를 출력하는 메서드
	 * @param boardList
	 */
	private void printAllBoard(List<Board> boardList) {
		
		if(! boardList.isEmpty()) {
			for(Board board : boardList) {
				int boardNo = board.getBoardNo();
				String boardTitle = board.getBoardTitle();
				String boardContent = board.getBoardContent();
				String createDate = board.getCreateDate();
				int readCount = board.getReadCount();
				int commentCount = board.getCommentCount();
				
				System.out.println(boardNo + " | " + boardTitle);
				System.out.println(boardContent + "\n");
				System.out.print("작성일 : " + createDate + " | ");
				System.out.print("조회수 : " + readCount  + " | ");
				System.out.println("댓글수 : " + commentCount);
				System.out.println("------------------------------------------");

				System.out.println();
			}
		} else {
			System.out.println("\n[게시글이 존재하지 않습니다]\n");
		}
		
	}

}
