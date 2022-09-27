package puppyWalk.board.model.dao;



import static puppyWalk.common.JDBCTemplete.*;

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
	
	
	/** 게시판 목록조회(제목만)
	 * @param conn
	 * @return memberList
	 * @throws Exception
	 */
	public List<Board> selectAllBoardTitle(Connection conn, String checkBoardType) throws Exception {

		List<Board> boardList = new ArrayList<>();
		String sql;
		try {
			if(checkBoardType.equals("모두")) {
				sql = prop.getProperty("selectAllBoard1");
				pstmt = conn.prepareStatement(sql);
			}else {
				sql = prop.getProperty("selectAllBoard2");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, checkBoardType);
				
			}
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardType(rs.getString("BOARD_TYPE"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCommentCount(rs.getInt("COMMENT_COUNT"));
				
				boardList.add(board);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return boardList;
	}

	/** 게시글 상세 조회
	 * @param conn
	 * @param boardNo
	 * @param memberNo
	 * @return 게시글 정보를 담은 board
	 */
	public Board selectOneBoard(Connection conn, int boardNo, int memberNo) throws Exception{

		Board board = null;
		
		try {
			String sql = prop.getProperty("selectOneBoard");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				board = new Board();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setMemberNo(rs.getInt("MEMBER_NO"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCommentCount(rs.getInt("COMMENT_COUNT"));
				
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return board;
	}



	/** 조회수 증가
	 * @param conn
	 * @param boardNo
	 * @return 업데이트된 행의 갯수 반환
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int boardNo) throws Exception {

		int result = 0; 
		
		try {
			String sql = prop.getProperty("increaseReadCount");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
		
	}


	/** 게시글 수정
	 * @param conn
	 * @param board
	 * @return
	 */
	public int updateBoard(Connection conn, Board board) throws Exception {
			
		int result = 0; 
		
		try {
			String sql = prop.getProperty("updateBoard");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardNo());
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}


	/** 게시글 삭제
	 * @param conn
	 * @param memberNo
	 * @return
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


	/** 게시글 boardNo에 쓸 NEXTVAL 구하기
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int getNextVal(Connection conn)  throws Exception {
		
		int result = 0; 
		try {
			String sql = prop.getProperty("getNextVal");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
				result = rs.getInt(1);
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}


	/** 후기 작성
	 * @param conn
	 * @param board
	 * @return
	 */
	public int insertBoard(Connection conn, Board board) throws Exception {

		int result = 0; 
		try {
			String sql = prop.getProperty("insertBoard");
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, board.getBoardNo());
			pstmt.setString(2, board.getBoardType());
			pstmt.setString(3, board.getBoardTitle());
			pstmt.setString(4, board.getBoardContent());
			pstmt.setInt(5, board.getMemberNo());
			
			if(board.getScheduleNo() != 0)
				pstmt.setInt(6, board.getScheduleNo());
			else
				pstmt.setString(6, null);
			
			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}


	

		
}
