package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Emp;

public class JDBCExample3 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
//		sc.close();
		
		//부서명을 입력받아 같은 부서에 있는 사원의 
		// 사원명, 부서명, 급여 조회
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		System.out.print("부서명을 입력하세요 : ");
		String input = sc.nextLine();
		
		
		try {
			// JDBC 참조변수에 알맞은 객체 대입
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// OJDBC안의 라이브러리 안의 oracle.jdbc.driver패키지 안의 OracleDriver클래스
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String user = "kh_ldw";
			String pw = "kh1234";
			
			
			conn = DriverManager.getConnection(type + ip + port + sid, user, pw);
			// jdbc:oracle:thin:@localhost:1521:XE == url
			
			//Statement가 실행할 SQL
			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서 없음') AS DEPT_TITLE, SALARY"
					+ " FROM EMPLOYEE "
					+ " LEFT JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)"
					+ " WHERE NVL(DEPT_TITLE, '부서 없음') LIKE '%" + input + "%'";
			// 중요! java에서 작성되는 SQL에 문자열(String) 변수를 이어쓰기할 경우 		
			// ''(DB 문자열 리터럴)이 누락되지 않도록 신경써야 한다
			// 만약 '' 미작성시 String값은 컬럼명으로 인식되어, "부적합한 식별자" 오류 발생
			
			stmt = conn.createStatement(); // Statement 객체 생성
			// Statement 객체를 이용해서 sql을 DB에 전달하여 실행한 후 resultSet를 반환받아 rs변수에 대입
			rs = stmt.executeQuery(sql);
			
			// 조회 결과(rs)를 List에 옮겨 담기
			List<Emp> list = new ArrayList<>();
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				//Emp 객체를 생성하여 컬럼 값 받기
				list.add(new Emp(empName, deptTitle, salary));
			}
			// 만약 list에 추가된 Emp객체가 없다면 "조회 결과가 없습니다.", 있다면 순차적 출력
			if(list.isEmpty()) // isEmpty : 비어있으면 true
				System.out.println("조회 결과가 없습니다.");
			else
				for(Emp e : list)
					System.out.println(e);
			
			
		} catch (Exception e) { 
			// 예외 최상위 부모인 Exception을 catch문에 작성하여 발생하는 모든 예외를 처리하겠다
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
}