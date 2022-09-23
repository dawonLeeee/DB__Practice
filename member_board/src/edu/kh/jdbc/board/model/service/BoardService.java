package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;

public class BoardService {
	
	private BoardDAO dao = new BoardDAO();
	private CommentDAO commentDAO = new CommentDAO();
	
	
	
	
	/** 게시글 목록 조회
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




	/** 게시글 수정
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public int updateBoard(Board board) throws Exception {

		Connection conn = getConnection();
		int result = dao.updateBoard(conn, board);
		
		if(result > 0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		return result;
	}



 
	/** 게시글 삭제
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int deleteBoard(int boardNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.deleteBoard(conn, boardNo);
		
		if(result > 0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		return result;
	}
}
