package edu.kh.jdbc.model.dao;

import static edu.kh.jdbc.common.JDBCTemplete.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplete;
import edu.kh.jdbc.model.vo.TestVO;

// DAO(Data Access Object) : 데이터가 저장된 DB에 접근하는 객체
//-> SQL 수행, 결과 반환 받는 기능을 수행
public class TestDAO {

	// JDBC 객체를 참조하기 위한 참조변수 선언
	//Connection은 Service에서 만들어서 가져올거라 빼고 만듬
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// XML로 SQL을 다룰 예정 -> Properties 객체 사용
	private Properties prop;
	
	
	// 기본 생성자
	public TestDAO() {
		// tstDAO 객체 생성시
		// test-query.xml파일의 내용을 읽어와 Properties 객체에 저장
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("test-query.xml"));
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 매개변수 생성자(testNo 전달받음)
	
	
	
	
	
	/** 1행 삽입 DAO
	 * @param conn
	 * @param vo1
	 * @return
	 */
	public int insert(Connection conn, TestVO vo1) throws SQLException{
		// throws SQLException 
		// -> 호출한 곳으로 발생한 SQLException을 던짐
		// -> 예외처리를 모아서 진행하기 위해서

		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성(test-query.xml에 작성된 SQL 얻어오기) -> prop이 저장하고 있음
			String sql = prop.getProperty("insert");
//			INSERT INTO TB_TEST
//			VALUES(?, ?, ?)	

			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// -> throws 예외처리
			
			
			//4. ?(위치홀더)에 알맞은 값을 셋팅하는 코드
			pstmt.setInt(1, vo1.getTestNo());
			pstmt.setString(2, vo1.getTestTitle());
			pstmt.setString(3, vo1.getTestContent());
			
			// 5. SQL(INSERT) 수행 후 결과 반환받기
			result = pstmt.executeUpdate(); // -> DML 수행, 반영된 행의 갯수(int) 반환
			// pstmt.executeQuery(); -> SELECT 수행, ResultSet 반환
			
			
		// pstmt를 닫기 위해 throws로 예외를 던졌음에도 try~catch구문을 사용한다
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환( close() )
			close(pstmt);
		}
		
		
		return result;
	}

}
