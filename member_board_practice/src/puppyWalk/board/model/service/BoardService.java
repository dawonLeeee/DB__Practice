package puppyWalk.board.model.service;



import static puppyWalk.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import puppyWalk.board.model.dao.BoardDAO;
import puppyWalk.board.model.dao.CommentDAO;
import puppyWalk.board.vo.Board;
import puppyWalk.board.vo.Comment;
import puppyWalk.member.model.dao.MemberDAO;

public class BoardService {

	private BoardDAO dao = new BoardDAO();
	private CommentDAO commentDAO = new CommentDAO();


	/** 게시판 목록조회
	 * @return 게시글 정보를 담은 boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard() throws Exception {
		
		Connection conn = getConnection();
		List<Board> boardList = dao.selectAllBoard(conn);
		
		close(conn);
		return boardList;
	}
	
	
	



	/** 게시글 상세 조회
	 * @param boardNo
	 * @param memberNo
	 * @return 게시글 정보를 담은 board
	 * @throws Exception
	 */
	public Board selectBoard(int boardNo, int memberNo) throws Exception{
		
		Connection conn = getConnection();
		Board board = dao.selectBoard(conn, boardNo);
		
		if(board != null) {
			List<Comment> commentList = commentDAO.selectCommentList(conn, boardNo);
			board.setCommentList(commentList);
			if(board.getMemberNo() != memberNo) {
				int result = dao.increaseReadCount(conn, boardNo);
				
				if(result >0) {
					// 미리 조회된 board의 조회수를 
					// 증가된 DB의 조회수와 동일한 값을 가지도록 동기화
					commit(conn);
					board.setReadCount(board.getReadCount()+1);
				}
				else{
					rollback(conn);
				}
			}
		}
		close(conn);
		return board;
	}
	
	

//	/** 내가 쓴 글 조회
//	 * @param memberId
//	 * @return List<Board> memberList
//	 * @throws Exception
//	 */
//	public List<Board> selectMyBoard(String memberId) throws Exception{
//		
//		List<Board> memberList = new ArrayList<>();
//		Connection conn = getConnection();
//		memberList = dao.selectMyBoard(conn, memberId);
//		
//		if(!memberList.isEmpty()) commit(conn);
//		else			rollback(conn);
//		
//		return memberList;
//	}
//	/** 게시글 하나 보여주기
//	 * @param boardNo
//	 * @return board
//	 * @throws Exception
//	 */
//	public Board selectBoard(int boardNo, int memberNo) throws Exception {
//
//		Connection conn = getConnection();
//		Board board = dao.selectBoard(conn, boardNo);
//		
//		if(board != null) {
//			List<Comment> commentList = commentDAO.selectCommentList(conn, boardNo);
//			board.setCommentList(commentList);
//			if(board.getMemberNo() != memberNo) {
//				int result = dao.increaseReadCount(conn, boardNo);
//				
//				if(result >0) {
//					// 미리 조회된 board의 조회수를 
//					// 증가된 DB의 조회수와 동일한 값을 가지도록 동기화
//					commit(conn);
//					board.setReadCount(board.getReadCount()+1);
//				}
//				else{
//					rollback(conn);
//				}
//			}
//		}
//		close(conn);
//		return board;
//	}
//	}
//	
//
//	/** 게시글 수정
//	 * @param boardNo
//	 * @param boardContent 
//	 * @param boardTitle 
//	 * @return 수정된 행의 갯수 result
//	 * @throws Exception
//	 */
//	public int updateBoard(int boardNo, String boardTitle, String boardContent) throws Exception {
//		
//		Connection conn = getConnection();
//		int result = dao.updateBoard(conn, boardNo, boardTitle, boardContent);
//		
//		// 트랜잭션 제어
//		if(result > 0) commit(conn);
//		else			rollback(conn);
//
//		close(conn);
//		return result;
//	}
//
//
//
//	/** 게시글 삭제
//	 * @param boardNo
//	 * @return
//	 */
//	public int deleteBoard(int boardNo) throws Exception {
//
//		Connection conn = getConnection();
//		int result = dao.deleteBoard(conn, boardNo);
//		
//		// 트랜잭션 제어
//		if(result > 0) commit(conn);
//		else			rollback(conn);
//
//		close(conn);
//		return result;
//	}
//
//
//
//	/** 게시글 번호를 입력받아 게시글 하나 보여주기(남의 게시글도)
//	 * @param boardNo
//	 * @return
//	 */
//	public Board selectOneBoard(int boardNo) throws Exception {
//
//		Connection conn = getConnection();
//		Board board = dao.selectOneBoard(conn, boardNo);
//		
//		
//
//		close(conn);
//		return board;
//	}








}
