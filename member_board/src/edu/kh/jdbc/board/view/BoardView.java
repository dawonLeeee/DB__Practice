package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.main.view.MainView;

public class BoardView {
	
	private Scanner sc = new Scanner(System.in);
	private BoardService boardService = new BoardService();
	private CommentService commentService = new CommentService();

	
	/**
	 * 게시판 기능 메뉴 화면
	 */
	public void boardMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("\n******게시판 기능******");
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회");
				System.out.println("3. 게시글 작성");
				System.out.println("4. 게시글 검색");
				System.out.println("0. 메인메뉴로 이동");
				
				System.out.print("\n메뉴 선택 >>");
				input = sc.nextInt();
				sc.nextLine();
				System.out.println();
				
				
				switch(input) {
				
				case 1 :  selectAllBoard(); break;
				case 2 :  selectBoard(); break;
				case 3 :  break;
				case 4 :  break;
				case 0 : System.out.println("[메인 메뉴로 이동합니다]\n"); break;
				default : System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
				
				
				}
			} catch(InputMismatchException e) {
				System.out.println("\\n<<입력 형식이 올바르지 않습니다>>\\n");
				e.printStackTrace();
				sc.nextLine();
			}
		} while(input != 0);
		
	}


	/**
	 * 게시글 목록 조회
	 */
	private void selectAllBoard() {
		
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
