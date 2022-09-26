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

public class CommentService {

	private CommentDAO dao = new CommentDAO();


	
	/** 댓글 등록
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public int insertComment(Comment comment) throws Exception {
		
		Connection conn = getConnection();
		int result = dao.insertComment(conn, comment);
		
		// 트랜잭션 제어
		if(result > 0) commit(conn);
		else			rollback(conn);

		close(conn);
		return result;
	}



	/** 댓글 수정
	 * @param comment
	 * @param content
	 * @return
	 */
	public int updateComment(int commentNo, String content) throws Exception {

		Connection conn = getConnection();
		int result = dao.updateComment(conn, commentNo, content);
		
		// 트랜잭션 제어
		if(result > 0) commit(conn);
		else			rollback(conn);

		close(conn);
		return result;
	}

	/** 댓글 삭제
	 * @param comment
	 * @param content
	 * @return
	 */
	public int deleteComment(int commentNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.deleteComment(conn, commentNo);
		
		// 트랜잭션 제어
		if(result > 0) commit(conn);
		else			rollback(conn);

		close(conn);
		return result;
	}






}
