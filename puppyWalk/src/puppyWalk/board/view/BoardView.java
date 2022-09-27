package puppyWalk.board.view;

import static puppyWalk.common.JDBCTemplete.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import puppyWalk.board.model.service.BoardService;
import puppyWalk.board.model.service.CommentService;
import puppyWalk.board.vo.Board;
import puppyWalk.board.vo.Comment;
import puppyWalk.main.view.MainView;
import puppyWalk.schedule.model.service.ScheduleService;
import puppyWalk.schedule.view.ScheduleView;
import puppyWalk.schedule.vo.Schedule;

public class BoardView {

	private BoardService boardService = new BoardService();
	private ScheduleService scheduleService = new ScheduleService();
	private CommentService commentService = new CommentService();
	private Scanner sc = new Scanner(System.in);
	private int input = -1;

	public void mainMenu() {


		do {
			System.out.println("\n\n******게시판 기능*******");
			System.out.println("1. 게시판 목록조회"); // ok
			System.out.println("2. 게시글 상세조회"); // ok
			System.out.println("3. 게시글 작성"); //ok
			System.out.println("0. 메뉴 나가기");

			System.out.print("번호 선택 : ");
			input = sc.nextInt();
			sc.nextLine();

			switch (input) {
			case 1:
				selectAllBoardTitleMenu();
				break;
			case 2:
				selectOneBoard();
				break;
			case 3: insertBoard(); break;
			case 0:
				System.out.println("[메인 메뉴로 이동합니다]\n");
				break;
			default:
				System.out.println("<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}

		} while (input != 0);
	}

	/**
	 * 게시글 등록
	 */
	public void insertBoard() {
		
		System.out.println("\n[게시글 등록]\n");
		
		int memberNo = MainView.loginMember.getMemberNo();
		Board board = new Board();
		
		
		
		while(true) {
			System.out.print("스케줄 불러오기(Y/N) : ");
			
			String isSchedule = sc.nextLine().toUpperCase();
			if(isSchedule.equals("Y")) {
				
				insertReviewBoard();
				break;
			} 
			if(isSchedule.equals("N")) break;
			else
				System.out.println("\n[(Y/N)만 입력해주세요]\n");		
		}
		
		

		System.out.print("\n제목 입력 : ");
		String boardTitle = sc.nextLine();
		System.out.println();
		
		System.out.print("게시글 타입 입력(훈련/산책/기타) : ");
		String boardType = sc.nextLine();
		while(true) {
			if(boardType.equals("훈련") ||
					boardType.equals("산책") ||
					boardType.equals("기타")) break;
			else
				System.out.println("\n[(훈련/산책/기타)만 입력해주세요]\n");				
		}
		System.out.println();
		
		
		
		
		System.out.print("내용 입력 : ");
		String boardContent = inputContent();
		System.out.println();
		
		
		//NEXTVAL 구하기
		int boardNo = 0;
		try {
			boardNo = boardService.getNextVal();
		} catch(Exception e) {
			System.out.println("\n<<후기 작성 중 예외가 발생했습니다>>\n");
			e.printStackTrace();
		}
		

		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setBoardType(boardType);
		board.setMemberNo(memberNo);
		board.setBoardNo(boardNo);
		
		

		try {
			int result = boardService.insertBoard(board);
			if (result > 0) { // insert 성공
				Board oneBoard = new Board();
				oneBoard = boardService.selectOneBoard(boardNo, memberNo);
				printOneBoard(oneBoard);
			
			} else
				System.out.println("\n[댓글 등록 실패]\n");

		} catch (Exception e) {
			System.out.println("\n<<후기 작성 중 예외가 발생했습니다>>\n");
			e.printStackTrace();
		}
		
	}

	/** 게시글 상세조회 선택시 나오는 메뉴
	 * @param board
	 */
	private void subBoardMenu(Board board) {
		
		try {
			System.out.println("1. 댓글 등록"); // ok
			System.out.println("2. 댓글 수정"); // ok
			System.out.println("3. 댓글 삭제"); // ok
			if(MainView.loginMember.getMemberNo() == board.getMemberNo()) {
				System.out.println("4. 게시글 수정"); // ok
				System.out.println("5. 게시글 삭제"); //ok
			}
			System.out.println("0. 게시판 메뉴로 돌아가기");
			
			System.out.println("\n서브 메뉴 선택 : ");
			int input = sc.nextInt();
			int memberNo = MainView.loginMember.getMemberNo();
			
			switch(input) {
			case 1 : insertComment(board, memberNo); break;
			case 2 : updateComment(board.getCommentList(), memberNo); break;
			case 3 : deleteComment(board.getCommentList(), memberNo); break;
			case 4 : case 5 : 
				if(MainView.loginMember.getMemberNo() == board.getMemberNo()) {
					if(input == 4) updateBoard(board.getBoardNo());
					if(input == 5) deleteBoard(board.getBoardNo());
				} else break;
				
			case 0 : break;
			default : System.out.println("\n<<메뉴에 작성된 번호만 입력해주세요>>\n");
			}
		} catch(InputMismatchException e) {
			System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
			e.printStackTrace();
			sc.nextLine();
		}
		

	}
	
	


	/**
	 * 댓글 등록
	 * 
	 * @param boardNo
	 * @param memberNo
	 */
	private void insertComment(Board board, int memberNo) {

		System.out.println("\n[댓글 등록]\n");
		String content = inputContent();

		Comment comment = new Comment();
		comment.setCommentContent(content);
		comment.setBoardNo(board.getBoardNo());
		comment.setMemberNo(memberNo);

		try {
			int result = commentService.insertComment(comment);
			if (result > 0) {

			} else
				System.out.println("\n[댓글 등록 실패]\n");

		} catch (Exception e) {
			System.out.println("\n<<댓글 내용을 입력하지 않았습니다>>\n");
			e.printStackTrace();
		}
	}

	/**
	 * 댓글 수정
	 * 
	 * @param commentList
	 * @param memberNo
	 */
	private void updateComment(List<Comment> commentList, int memberNo) {

		System.out.println("\n[댓글 수정]\n");
		System.out.print("수정할 댓글 번호를 입력하세요 : ");
		int commentNo = sc.nextInt();

		
		String content = inputContent();

		Comment comment = new Comment();
		comment.setCommentContent(content);
		comment.setMemberNo(memberNo);
		
		try {
			boolean flag = true;
			for (Comment c : commentList) {
				if (c.getCommentNo() == commentNo) {
					flag = false;

					if (c.getMemberNo() == memberNo) {
						try {
							int result = commentService.updateComment(commentNo, content);
							if (result > 0)
								System.out.println("\n[댓글 수정 완료]\n");
							else
								System.out.println("\n[댓글 수정 실패]\n");
						} catch (Exception e) {
							System.out.println("\n<<댓글 수정 중 오류 발생>>\n");
							e.printStackTrace();
						}
					} else {
						System.out.println("\n[내가 작성한 댓글이 아닙니다]\n");
					}
					break;
				}
			}
			if (flag)
				System.out.println("\n[수정하고자 하는 댓글이 댓글 목록에 없습니다]\n");

		} catch (Exception e) {
			System.out.println("\n<<댓글 내용을 입력하지 않았습니다>>\n");
			e.printStackTrace();
		}
	}

	/** 댓글 삭제
	 * @param commentList
	 * @param memberNo
	 */
	private void deleteComment(List<Comment> commentList, int memberNo) {
		
		System.out.println("\n[댓글 삭제]\n");
		System.out.print("삭제할 댓글 번호를 입력하세요 : ");
		int commentNo = sc.nextInt();


		
			boolean flag = true;
			for (Comment c : commentList) {
				if (c.getCommentNo() == commentNo) {
					flag = false;

					if (c.getMemberNo() == memberNo) {
						try {
							int result = commentService.deleteComment(commentNo);
							if (result > 0)
								System.out.println("\n[댓글 삭제 완료]\n");
							else
								System.out.println("\n[댓글 삭제 실패]\n");
						} catch (Exception e) {
							System.out.println("\n<<댓글 삭제 중 오류 발생>>\n");
							e.printStackTrace();
						}
					} else {
						System.out.println("\n[내가 작성한 댓글이 아닙니다]\n");
					}
					break;
				}
			}
			if (flag)
				System.out.println("\n[삭제하고자 하는 댓글이 댓글 목록에 없습니다]\n");

		
	}
	
	
	/** 게시글 수정
	 * @param boardNo
	 */
	private void updateBoard(int boardNo) {
		
		System.out.println("\n[게시글 수정]\n");
		

		System.out.println("\n[게시글 제목 수정하기 : ]\n");
		String boardTitle = inputContent();
		
		System.out.println("\n[게시글 내용 수정하기 : ]\n");
		String boardContent = inputContent();

		Board board = new Board();
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setBoardNo(boardNo);
		
		try {
			int result = boardService.updateBoard(board);
			if (result > 0)
				System.out.println("\n[게시글 수정 완료]\n");
			else
				System.out.println("\n[게시글 수정 실패]\n");
		} catch (Exception e) {
			System.out.println("\n<<게시글 수정 중 오류 발생>>\n");
			e.printStackTrace();
		}
	}
	
	
	/** 게시글 삭제
	 * @param boardNo
	 */
	private void deleteBoard(int boardNo) {
		
		System.out.println("\n[게시글 수정]\n");
		
		System.out.println("\n정말로 삭제하시겠습니까?(Y / N) : \n");
		String isDelete = sc.next();
		while(true) {
			if(isDelete.equals("Y")) {
				try {
					int result = boardService.deleteBoard(boardNo);
					if (result > 0)
						System.out.println("\n[게시글 수정 완료]\n");
					else
						System.out.println("\n[게시글 수정 실패]\n");
				} catch (Exception e) {
					System.out.println("\n<<게시글 수정 중 오류 발생>>\n");
					e.printStackTrace();
				}
			} else if(isDelete.equals("N")) {
				System.out.println("\n[게시글 삭제 취소]\n");
				break;
			} else
				System.out.println("\n[(Y / N)만 입력해주세요]\n");
		}
	}
	// ------------------------------------------------------------

	/**
	 * 게시판 목록조회(제목)
	 */
	private void selectAllBoardTitleMenu() {

		System.out.println("\n[게시판 목록조회]\n");

		System.out.println("1. 훈련/산책/기타 모두 보기");
		System.out.println("2. 훈련 후기만 보기");
		System.out.println("3. 산책 후기만 보기");
		System.out.println("4. 기타 게시글만 보기");

		System.out.print("번호 선택 : ");
		int input = sc.nextInt();

		switch (input) {
		case 1:
			selectAllBoardTitle("모두");
			break;
		case 2:
			selectAllBoardTitle("훈련");
			break;
		case 3:
			selectAllBoardTitle("산책");
			break;
		case 4:
			selectAllBoardTitle("기타");
			break;
		}
	}

	/**
	 * 게시판 목록조회
	 */
	private void selectAllBoardTitle(String checkBoardType) {

		System.out.println("\n[게시판 목록조회]\n");

		try {
			List<Board> boardList = boardService.selectAllBoardTitle(checkBoardType);
			printAllBoard(boardList);

		} catch (Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}

	/**
	 * 게시글 상세 조회
	 */
	private void selectOneBoard() {

		System.out.println("\n[게시글 상세 조회]\n");

		System.out.print("게시글 번호를 입력하세요 : ");
		int boardNo = sc.nextInt();
		System.out.println();

		try {
			Board board = boardService.selectOneBoard(boardNo, MainView.loginMember.getMemberNo());
			printOneBoard(board);

			subBoardMenu(board);

		} catch (Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}

	}



	/**
	 * 예약 후 후기 작성
	 */
	public void insertReviewBoard() {

		System.out.println("\n[후기 게시글 등록]\n");
		
		int memberNo = MainView.loginMember.getMemberNo();
		
		try {
			List<Schedule> scheduleList = scheduleService.searchReviewSchedule(memberNo);
			System.out.print("\n[후기 작성 가능한 스케줄 목록]\n");
			ScheduleView.printAllSchedule(scheduleList);
			
			List<Integer> scheduleNumList = new ArrayList<>();
			for(Schedule s : scheduleList) {
				scheduleNumList.add(s.getScheduleNo());
				}
			
			System.out.print("\n[후기를 작성할 스케줄 번호 입력 : ]");
			int scheduleNo = sc.nextInt();
			sc.nextLine();
			
			
			List<Schedule> oneScheduleList = new ArrayList<>();
			try {
				oneScheduleList = scheduleService.selectScheduleByNo(scheduleNo);
				if(oneScheduleList.isEmpty()) {
					System.out.println("\n[후기작성이 불가능한 스케줄 번호입니다]");
				}else {
					System.out.print("제목 입력 : ");
					String boardTitle = sc.nextLine();
					System.out.println();
					
					
					System.out.print("내용 입력 : ");
					String boardContent = "";
					boardContent += oneScheduleList.toString();
					
					while(true) { // 내용입력 가이드
						System.out.println("\n[내용입력 가이드 이용? (Y / N) : ]");
						String isGuide = sc.nextLine();
						if(isGuide.equals("Y")){
							System.out.println("\n[반려견의 컨디션은 좋았나요? : ]");
							boardContent += "[Q. 반려견의 컨디션은 좋았나요? : ]\nA : " + inputContent();
							
							System.out.println("\n[파트너는 친절했나요? : ]");
							boardContent += "[Q. 파트너는 친절했나요? : ]\nA : " + inputContent();
							
							System.out.println("\n[산책/훈련이 전반적으로 만족스러웠나요? : ]");
							boardContent += "[Q. 산책/훈련이 전반적으로 만족스러웠나요? : ]\nA : " + inputContent();
							
							System.out.println("\n[기타 남기고 싶은 말은? : ]");
							boardContent += "[Q. 기타 남기고 싶은 말은? : ]\nA : " + inputContent();
							break;
						} else if(isGuide.equals("N")) {
							boardContent += inputContent();
							break;
						}
						else	System.out.println("\n[(Y / N)만 입력해주세요]\n");
					}
					
					String serviceType = "";
					for(Schedule s : scheduleList) {
						if(s.getScheduleNo() == scheduleNo) {
							serviceType = s.getServiceType();
							break;
						}
					}

					//NEXTVAL 구하기
					int boardNo = 0;
					try {
						boardNo = boardService.getNextVal();
					} catch(Exception e) {
						System.out.println("\n<<후기 작성 중 예외가 발생했습니다>>\n");
						e.printStackTrace();
					}
					
					Board board = new Board();
					board.setBoardNo(boardNo);
					board.setBoardTitle(boardTitle);
					board.setBoardContent(boardContent);
					board.setBoardType(serviceType);
					board.setMemberNo(memberNo);
					board.setScheduleNo(scheduleNo);
					

					try {
						int result = boardService.insertBoard(board);
						if (result > 0) {
							Board oneBoard = new Board();
							oneBoard = boardService.selectOneBoard(boardNo, memberNo);
							printOneBoard(oneBoard);
							
							System.out.println("=======재귀호출 할꺼야??????========");
							System.out.println("=======재귀호출 할꺼야??????========");
							System.out.println("=======재귀호출 할꺼야??????========");
							System.out.println("=======재귀호출 할꺼야??????========");
							System.out.println("=======재귀호출 할꺼야??????========");
							System.out.println("=======재귀호출 할꺼야??????========");
							
						} else
							System.out.println("\n[게시글 등록 실패]\n");

					} catch (Exception e) {
						System.out.println("\n<<게시글 내용을 입력하지 않았습니다>>\n");
						e.printStackTrace();
					}
				}
			} catch(Exception e) {
				System.out.println("\n<<후기 작성 중 예외가 발생했습니다>>\n");
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
			
			
			
			
				
				
				
				
			
				
			
			
		} catch (Exception e) {
			System.out.println("\n<<후기 작성 중 예외가 발생했습니다>>\n");
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 게시글 하나를 출력하는 메서드
	 * 
	 * @param boardList
	 */
	private void printOneBoard(Board board) {

		if (board != null) {

			int boardNo = board.getBoardNo();
			String boardTitle = board.getBoardTitle();
			String boardContent = board.getBoardContent();
			String createDate = board.getCreateDate();
			String memberName = board.getMemberName();
			int readCount = board.getReadCount();
			int commentCount = board.getCommentCount();
			int scheduleNo = board.getScheduleNo();
			List<Comment> commentList = board.getCommentList();

			System.out.println(boardNo + " | 작성자 : " + memberName + " | " + boardTitle);
			System.out.println(boardContent + "\n");
			System.out.print("스케줄번호 : " + scheduleNo + " | ");
			System.out.print("작성일 : " + createDate + " | ");
			System.out.print("조회수 : " + readCount + " | ");
			System.out.println("댓글수 : " + commentCount);
			System.out.println("------------------------------------------");
			System.out.println("[댓글 List]\n");
			for (Comment comment : commentList) {
				System.out.println("==================");
				System.out.println("댓글 번호 : " + comment.getCommentNo() + "번");
				System.out.println("작성자 : " + comment.getMemberName());
				System.out.println("내용 : " + comment.getCommentContent());

			}
			System.out.println("-----------------------------------------------------");
			System.out.println("-----------------------------------------------------");

			System.out.println();

		} else {
			System.out.println("\n[게시글이 존재하지 않습니다]\n");
		}
	}

	/**
	 * 게시글 하나를 출력하는 메서드
	 * 
	 * @return 입력된 문자열
	 */
	private String inputContent() {
		String content = "";
		String input = null;
		System.out.println("입력 종료시 ($exit) 입력 : \n");
		while (true) {
			input = sc.nextLine();
			if (input.equals("$exit"))
				break;
			// 입력된 내용을 content에 누적
			content += "\n" + input;
		}

		return content;
	}

// ---------------------------------------------------------------------

	/**
	 * 게시글 제목을 출력하는 메서드(content 없음)
	 * 
	 * @param boardList
	 */
	private void printAllBoard(List<Board> boardList) {

		if (!boardList.isEmpty()) {
			for (Board board : boardList) {
				int boardNo = board.getBoardNo();
				String boardType = board.getBoardType();
				String boardTitle = board.getBoardTitle();
				String createDate = board.getCreateDate();
				int readCount = board.getReadCount();
				int commentCount = board.getCommentCount();
				String memberName = board.getMemberName();

				System.out.println(boardNo + " | " + boardType + " | " + boardTitle);
				System.out.print("작성자 : " + memberName + " | 작성일 : " + createDate + " | ");
				System.out.print("조회수 : " + readCount + " | ");
				System.out.println("댓글수 : " + commentCount);
				System.out.println("-----------------------------------------------------");

				System.out.println();
			}
		} else {
			System.out.println("\n[게시글이 존재하지 않습니다]\n");
		}

	}

	

}
