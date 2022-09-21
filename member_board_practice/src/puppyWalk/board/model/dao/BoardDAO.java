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
				prop.loadFromXML(new FileInputStream("main-query.xml"));
				// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/** 내가 쓴 글 조회
	 * @param conn
	 * @param memberId
	 * @return List<Board> memberList
	 * @throws Exception
	 */
	public List<Board> selectMyBoard(Connection conn, String memberId) throws Exception{
		
		List<Board> memberList = new ArrayList<>();
		String sql = prop.getProperty("updatePw");
		stmt = conn.createStatement();

		rs = stmt.executeQuery(sql);
		if(rs.next()) {
			
		}
		
		close(stmt);
		
		
		
		
		
		return memberList;
	}
		
}
