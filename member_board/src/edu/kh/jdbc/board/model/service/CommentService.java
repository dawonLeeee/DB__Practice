package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplete.*;

import java.sql.Connection;

import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Comment;

public class CommentService {

	private CommentDAO dao = new CommentDAO();

	
	/** 댓글 등록 서비스
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public int insertComment(Comment comment) throws Exception {

		Connection conn = getConnection();
		int result = dao.insertComment(conn, comment);
		
		if(result > 0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		return result;
	}


	/** 댓글 수정
	 * @param commentNo
	 * @param content
	 * @return 댓글 수정되었는지 여부(1/0)
	 */
	public int updateComment(int commentNo, String content) throws Exception {

		Connection conn = getConnection();
		int result = dao.updateComment(conn, commentNo, content);
		
		if(result > 0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		return result;
	}


	/** 댓글 삭제
	 * @param commentNo
	 * @param content
	 * @return 댓글 삭제되었는지 여부(1/0)
	 */
	public int deleteComment(int commentNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.deleteComment(conn, commentNo);
		
		if(result > 0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		return result;
	}
}
