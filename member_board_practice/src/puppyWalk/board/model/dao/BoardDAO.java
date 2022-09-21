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

public class BoardDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;

	public BoardDAO() {

			try {
				prop = new Properties();
				prop.loadFromXML(new FileInputStream("board-query.xml"));
				// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	/** 게시판 목록조회
	 * @param conn
	 * @return memberList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception {

		List<Board> memberList = new ArrayList<>();
		try {
			String sql = prop.getProperty("selectAllBoard");
			stmt = conn.createStatement();
	
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContentViews(rs.getInt("BOARD_CONTENT_VIEWS"));
				board.setEnrollDate(rs.getString("ENROLL_DATE"));
			}
		} finally {
			close(stmt);
		}
		return memberList;
	}

	/** 내가 쓴 글 조회
	 * @param conn
	 * @param memberId
	 * @return List<Board> memberList
	 * @throws Exception
	 */
	public List<Board> selectMyBoard(Connection conn, String memberId) throws Exception{
		
		List<Board> memberList = new ArrayList<>();
		try {
			String sql = prop.getProperty("selectMyBoard");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
	
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setBoardContentViews(rs.getInt("BOARD_CONTENT_VIEWS"));
				board.setEnrollDate(rs.getString("ENROLL_DATE"));
			}
		} finally {
			close(pstmt);
			close(rs);
		}

		return memberList;
	}


	/** 게시글 하나 보여주기
	 * @param conn
	 * @param boardNo
	 * @param memberId
	 * @return
	 */
	public Board selectBoard(Connection conn, int boardNo, String memberId) throws Exception {

		Board board = null;
		try {
			String sql = prop.getProperty("selectBoard");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, memberId);
	
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new Board();
				board.setBoardNo(boardNo);
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setBoardContentViews(rs.getInt("BOARD_CONTENT_VIEWS"));
				board.setEnrollDate(rs.getString("ENROLL_DATE"));
			}
		} finally {
			close(pstmt);
			close(rs);
		}

		return board;
	}


	/** 게시글 수정
	 * @param conn
	 * @param boardNo
	 * @param boardContent 
	 * @param boardTitle 
	 * @return 수정된 행의 갯수 result
	 */
	public int updateBoard(Connection conn, int boardNo, String boardTitle, String boardContent) throws Exception {

		int result = 0;
		try {
			String sql = prop.getProperty("updateBoard");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		return result;
	}


	/** 게시글 삭제
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int deleteBoard(Connection conn, int boardNo) throws Exception {

		int result = 0;
		try {
			String sql = prop.getProperty("deleteBoard");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		return result;
	}


		
}
