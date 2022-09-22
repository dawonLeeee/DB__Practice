package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplete.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;

public class CommentDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public CommentDAO() {

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-query.xml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 댓글 가져오기
	 * @param conn
	 * @param boardNo
	 * @return 게시글 정보가 담긴 boardList
	 * @throws Exception
	 */
	public List<Comment> selectCommentList(Connection conn, int boardNo) throws Exception {

		List<Comment> commentList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectCommentList");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				for(Comment comment : commentList) {
					
					comment.setCommentNo(rs.getInt("COMMENT_NO"));
					comment.setCommentContent(rs.getString("COMMENT_CONTENT"));
					comment.setMemberNo(rs.getInt("MEMBER_NO"));
					comment.setMemberName(rs.getString("MEMBER_NM"));
					comment.setCreateDate(rs.getString("CREATE_DT"));
					
					commentList.add(comment);
				}
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return commentList;
		
	}
}
