package puppyWalk.board.model.dao;

import static puppyWalk.common.JDBCTemplete.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import puppyWalk.board.vo.Board;
import puppyWalk.board.vo.Comment;

public class CommentDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;

	public CommentDAO() {

			try {
				prop = new Properties();
				prop.loadFromXML(new FileInputStream("comment-query.xml"));
				// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

			} catch (Exception e) {
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
				
				Comment comment = new Comment();

				comment.setCommentNo(rs.getInt("COMMENT_NO"));
				comment.setCommentContent(rs.getString("COMMENT_CONTENT"));
				comment.setMemberNo(rs.getInt("MEMBER_NO"));
				comment.setMemberName(rs.getString("MEMBER_NM"));
				comment.setCreateDate(rs.getString("CREATE_DT"));

				commentList.add(comment);
				
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return commentList;
		
	}
	

	/** 댓글 등록
	 * @param conn
	 * @param comment
	 * @return
	 */
	public int insertComment(Connection conn, Comment comment) throws Exception {

		int result = 0; 
		
		try {
			String sql = prop.getProperty("insertComment");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getCommentContent());
			pstmt.setInt(2, comment.getMemberNo());
			pstmt.setInt(3, comment.getBoardNo());
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}


	/** 댓글 수정
	 * @param conn
	 * @param commentNo
	 * @param content
	 * @return
	 */
	public int updateComment(Connection conn, int commentNo, String content) throws Exception {

		int result = 0; 
		
		try {
			String sql = prop.getProperty("updateComment");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, commentNo);
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	
	/** 댓글 삭제
	 * @param conn
	 * @param commentNo
	 * @param content
	 * @return
	 */
	public int deleteComment(Connection conn, int commentNo) throws Exception {

		int result = 0; 
		
		try {
			String sql = prop.getProperty("deleteComment");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNo);
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


		
}
