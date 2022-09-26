package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
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
				case 3 :  insertBoard(); break;
				case 4 :  searchBoard(); break;
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
	


	/** 게시글 상세 조회시 출력되는 서브메뉴
	 * @param board(상세조회된 게시글 + 작성자 번호 + 댓글 목록)
	 */
	private void subBoardMenu(Board board) {

		try {
			System.out.println("1. 댓글 등록");
			System.out.println("2. 댓글 수정");
			System.out.println("3. 댓글 삭제");
			
			//로그인한 회원과 게시글 작성자가 같은 경우에만 출력되는 메뉴
			if(MainView.loginMember.getMemberNo() == board.getMemberNo()) {
				System.out.println("4. 게시글 수정");
				System.out.println("5. 게시글 삭제");
			}
			
			
			
			System.out.println("0. 게시판 메뉴로 돌아가기");
			
			System.out.println("\n서브 메뉴 선택 : ");
			int input = sc.nextInt();
			
			// 로그인한 회원의 회원번호
			int memberNo = MainView.loginMember.getMemberNo();
			
			
			switch(input) {
				case 1: insertComment(board, memberNo); break;
				case 2: updateComment(board.getCommentList(), memberNo); break;
				case 3: deleteComment(board.getCommentList(), memberNo); break; 
				case 0: System.out.println("\n[메인 메뉴로 이동합니다]\n"); break;
				
				case 4: case 5: // 4또는 5 입력시 // 회원이 게시글 작성자인 경우
					if(MainView.loginMember.getMemberNo() == board.getMemberNo()) {
						if(input == 4) { // 게시글 수정 호출
							updateBoard(board.getBoardNo());
						} 
						if(input == 5) { //게시글 삭제 호출
							deleteBoard(board.getBoardNo());
							input = 0;
						}
						break;
					}
				default : System.out.println("\n<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}
			
			
			
			// 댓글 등록, 수정, 삭제 선택시
			// 각각의 서비스메서드 종료 후 다시 서브메뉴 메서드 호출
			if(input > 0) {
				// 게시글 상세조회

				try {
		               board = boardService.selectBoard(board.getBoardNo(), MainView.loginMember.getMemberNo());
		               printOneBoard(board);
		            }catch (Exception e) {
		               e.printStackTrace();
		            }
				subBoardMenu(board);
			}
			
		} catch(InputMismatchException e) {
			System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
			e.printStackTrace();
			sc.nextLine();
		}
		
	}



//////////////////////////////////////////////////////////////////////////////////////
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
	
	
	/** 댓글 등록
	 * @param board
	 * @param memberNo
	 */
	private void insertComment(Board board, int memberNo) {

		int boardNo = board.getBoardNo();
		
		try {
			System.out.println("\n[댓글 등록]\n");
			String content = inputContent();
			
			// DB INSERT시 필요한 값을 하나의 comment객체에 저장
			Comment comment = new Comment();
			comment.setCommentContent(content);
			comment.setBoardNo(boardNo);
			comment.setMemberNo(memberNo);
			
			
			int result = commentService.insertComment(comment);
			if(result > 0) { 
				board.setCommentCount(board.getCommentCount() + 1);
				System.out.println("\n[댓글 등록 성공]\n");
			}
			
			else	System.out.println("\n[댓글 등록 실패]\n");
			
		} catch(Exception e) {
			System.out.println("\n<<댓글 내용을 입력하지 않았습니다>>\n");
			e.printStackTrace();
		}
	}
	

	/** 댓글 수정
	 * @param commentList
	 * @param memberNo
	 */
	private void updateComment(List<Comment> commentList, int memberNo) {

		
		
				// 댓글 번호를 입력받아
				// 1. 해당 댓글이 commentList에 있는지 검사
				// 2. 있다면 해당 댓글이 로그인한 회원이 작성한 글인지 검사
		try {
			System.out.println("\n[댓글 수정]\n");
			
			System.out.print("수정할 댓글 번호 입력 : ");
			int commentNo = sc.nextInt();
			sc.nextLine();
			
			boolean flag = true;
			for(Comment c : commentList) {
				if(c.getCommentNo() == commentNo) { // 댓글 번호 일치
					flag = false;
					
					if(c.getMemberNo() == memberNo) { // 회원 번호 일치
						String content = inputContent();
						int result = commentService.updateComment(commentNo, content);
						
						if(result > 0) System.out.println("\n[댓글 수정 성공]\n");
						else System.out.println("\n[댓글 수정 실패]\n");
					} else {
						System.out.println("\n[자신의 댓글만 수정할 수 있습니다]\n");
					}
					break;
				}
			}
			if(flag) {
				System.out.println("\n[번호가 일치하는 댓글이 없습니다]\n");
			}
			
			
		} catch(Exception e) {
			System.out.println("\n<<댓글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	/** 댓글 삭제
	 * @param commentList
	 * @param memberNo
	 */
	private void deleteComment(List<Comment> commentList, int memberNo) {

		try {
			System.out.println("\n[댓글 삭제]\n");
			
			System.out.print("삭제할 댓글 번호 입력 : ");
			int commentNo = sc.nextInt();
			sc.nextLine();
			
			boolean flag = true;
			for(Comment c : commentList) {
				if(c.getCommentNo() == commentNo) { // 댓글 번호 일치
					flag = false;
					
					if(c.getMemberNo() == memberNo) { // 회원 번호 일치
						
						//정말 삭제? Y/N
						System.out.println("\n[정말 삭제하시겠습니까? (Y/N) : ]\n");
						String isDelete = sc.next().toUpperCase();
						if(isDelete.equals("Y")) {
							
							
							int result = commentService.deleteComment(commentNo);
							
							if(result > 0) System.out.println("\n[댓글 삭제 성공]\n");
							else System.out.println("\n[댓글 삭제 실패]\n");
							
						} else
							System.out.println("\n[취소되었습니다]\n");
						
					} else {
						System.out.println("\n[자신의 댓글만 삭제할 수 있습니다]\n");
					}
					break;
				}
			}
			if(flag) {
				System.out.println("\n[번호가 일치하는 댓글이 없습니다]\n");
			}
			
			
		} catch(Exception e) {
			System.out.println("\n<<댓글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	


	/**
	 * 게시글 등록
	 */
	private void insertBoard() {

		try {
			System.out.println("\n[게시글 등록]\n");

			System.out.print("제목 : ");
			String boardTitle = sc.nextLine();
			
			System.out.print("내용 : ");
			String boardContent = inputContent();
			
			Board board = new Board();
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setMemberNo(MainView.loginMember.getMemberNo());
			
			
			int result = boardService.insertBoard(board);
			// result에 0 또는 생성된 게시글 번호가 담김
			if(result > 0) { 
				System.out.println("\n[게시글 등록 성공]\n");
				Board b = boardService.selectBoard(result, MainView.loginMember.getMemberNo());
				printOneBoard(b);
			}
			
			else	System.out.println("\n[게시글 등록 실패]\n");
			
		} catch(Exception e) {
			System.out.println("\n<<게시글 삭제 중 예외 발생>>\n");
			e.printStackTrace();
		}	
		
	}
	
	/** 게시글 수정
	 * @param boardNo
	 */
	private void updateBoard(int boardNo) {

		try {
			// 제목, 내용 한번에 수정
			System.out.println("\n[게시글 수정]\n");
			sc.nextLine();
			
			System.out.print("수정할 제목 : ");
			String boardTitle = sc.next();
			
			System.out.print("수정할 내용 : ");
			String boardContent = inputContent();
			
			Board board = new Board();
			board.setBoardNo(boardNo);
			board.setBoardContent(boardContent);
			board.setBoardTitle(boardTitle);
			int result = boardService.updateBoard(board);
			
			if(result > 0)
				System.out.println("\n[게시글 수정 성공]\n");
			else
				System.out.println("\n[게시글 수정 실패]\n");
			
			
			
			
		} catch(Exception e) {
			System.out.println("\n<<게시글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	/** 게시글 삭제
	 * @param boardNo
	 */
	private void deleteBoard(int boardNo) {

		try {
			System.out.println("\n[게시글 삭제]\n");
			
			//정말 삭제? Y/N
			System.out.println("\n[정말 삭제하시겠습니까? (Y/N) : ]\n");
			String isDelete = sc.next().toUpperCase();
			if (isDelete.equals("Y")) {
				
				int result = boardService.deleteBoard(boardNo);

				if (result > 0)
					System.out.println("\n[게시글 삭제 성공]\n");
				else
					System.out.println("\n[게시글 삭제 실패]\n");

			} else
				System.out.println("\n[취소되었습니다]\n");

		} catch(Exception e) {
			System.out.println("\n<<게시글 삭제 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 게시글 검색
	 */
	private void searchBoard() {

		try {
			// 제목, 내용 한번에 수정
			System.out.println("\n[게시글 검색]\n");
			
			System.out.println("1) 제목");
			System.out.println("2) 내용");
			System.out.println("3) 제목 + 내용");
			System.out.println("4) 작성자");
			System.out.println();
			
			System.out.print("번호 선택 : ");
			int condition = sc.nextInt();
			sc.nextLine();
			
			if(condition >= 1 && condition <= 4) { //정상 입력
				System.out.print("검색할 내용 : ");
				String query = sc.nextLine();
				
				List<Board> boardList = boardService.searchBoard(condition, query);
				
				
				if(!boardList.isEmpty())
					printAllBoard(boardList);
				else
					System.out.println("\n[검색 결과가 없습니다]\n"); ////////////
				
			} else {
				System.out.println("\n[1~4번 사이의 숫자를 입력해주세요]\n");
			}

		} catch(Exception e) {
			System.out.println("\n<<게시글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}

	//출력 메서드///////////////////////////////////////////////////////////////////////////////////////
	
	
	/** 내용 입력받는 메서드
	 * @return 입력된 문자열
	 */
	private String inputContent() {
		String content = "";
		String input = null;
		System.out.println("입력 종료시 ($exit) 입력 : \n");
		
		while(true) {
			input = sc.nextLine();
			if(input.equals("$exit")) break;
			// 입력된 내용을 content에 누적
			content += "\n" + input ;
		};
		return content;
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
			List<Comment> commentList = board.getCommentList();

			System.out.println(boardNo + " | 작성자 : " + memberName + " | " + boardTitle);
			System.out.println(boardContent + "\n");
			System.out.print("작성일 : " + createDate + " | ");
			System.out.print("조회수 : " + readCount + " | ");
			System.out.println("댓글수 : " + commentList.size());
			System.out.println("------------------------------------------");
			System.out.println("------------------------------------------");
			System.out.println("[댓글 List]\n");
			for(Comment comment : commentList) {
				System.out.println("댓글 번호 : " + comment.getCommentNo() + "번");
				System.out.println("작성자 : " + comment.getMemberName());
				System.out.println("내용 : " + comment.getCommentContent());
				System.out.println("==================");
			}
			System.out.println("------------------------------------------");

			System.out.println();
			
			// 댓글 등록, 수정, 삭제
			// 게시글 수정/삭제 메뉴
			subBoardMenu(board);
			
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
				
				System.out.println("------------------------------------------");
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
