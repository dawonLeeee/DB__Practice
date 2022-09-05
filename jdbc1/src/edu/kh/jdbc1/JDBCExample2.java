package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {

	public static void main(String[] args) {
		/*
		 * JDBC : JAVA에서 DB에 접근할 수 있게 해 주는 JAVA programming API
		 * 
		 * 
		 * JDBC를 이용한 어플리케이션을 만들 때 필요한 것 : 
		 * 1. JDBC
		 * 2. oracle(DBMS)
		 * 3. Oracle에서 java어플리케이션과 연결할 때 JDBC를 상속받아 구현한 클래스 모음(ojdbc11.jar)
		 * 
		 */
		
		
		/*
		 * 1단계 : 객체 참조변수 선언
		 * 
		 * 2단계 : 참조변수에 알맞은 객체 대입
		 * 	1) DB연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
		 * 	2) 연결정보를 담은 Connection 생성
		 * 	3) sql 작성
		 * 	4) statement 객체 작성
		 * 	5) 생성된 statement 객체에 sql 실행한 후 결과(resultSet)를 rs변수에 저장
		 * 3단계 : sql을 수행해서 반환받은 결과를 한 행씩 접근해서 컬럼 값 가져오기
		 * 4단계 : 사용한 JDBC 자원 반납
		 */
		
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid =  ":XE";
			String user = "kh_ldw";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(type + ip + port + sid, user, pw);
			System.out.print("급여 입력 : ");
			int input = sc.nextInt();
			sc.nextLine();
			
			String sql = 
					"SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > " + input;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d\n", 
		 				empId, empName, salary);
			}
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
//		Scanner sc = new Scanner(System.in);
//		
//		//1단계 : JDBC객체 참조변수 생성
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		
//		
//		try {
//			// 2단계 : 참조변수에 알맞은 객체 대입
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			String type = "jdbc:oracle:thin:@";
//			String ip = "localhost";
//			String port = ":1521";
//			String sid = ":XE";
//			String user = "kh_ldw";
//			String pw = "kh1234";
//			
//			conn = DriverManager.getConnection(type + ip + port + sid, user, pw);
//			
//			//입력받은 급여보다 많이 받는(초과) 직원만 조회
//			System.out.print("급여 입력 >>");
//			int input = sc.nextInt();
//			sc.nextLine();
//			
//			String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > " + input;
//			// >뒤에 띄어쓰기 하나 넣어주기(에러 방지)
//			
//			stmt = conn.createStatement(); 
//			rs = stmt.executeQuery(sql);
//			
//			
//			// 3e단계 : sql을 수행해서 반환받은 값(ResultSet)을 한 행씩 접근해서 컬럼값 얻어오기
//			while(rs.next()) {
//				String empId = rs.getString("EMP_ID");
//				String empName = rs.getString("EMP_NAME");
//				int salary = rs.getInt("SALARY");
//				
//				System.out.printf("사번 : %s | 이름 : %s | 급여 : %d\n", 
//		 				empId, empName, salary);
//			}
//			
//		} catch (ClassNotFoundException e) {
//			System.out.println("2단계에서 오타");
//			
//		}  catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			
//				try {
//					if(rs != null) rs.close();
//					if(stmt != null) stmt.close();
//					if(conn != null) conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
	}
}
