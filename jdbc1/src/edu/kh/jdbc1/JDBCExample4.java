package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Emp2;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// 직급명, 급여를 입력받아 
		// 해당 직급에서 입력받은 급여보다 많이 받는 사원의
		// 이름, 직급명, 급여, 연봉 출력
		// 단, 조회 결과가 없으면 "조회 결과 없음" 출력
		// 조회 결과가 있으면 선동일 / 대표 / 8000000 / 96000000 이런식 출격
		
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh_ldw";
			String pw = "kh1234";
			
			System.out.print("직급명을 입력하세요 : ");
			String jobName = sc.nextLine();
			
			System.out.print("급여를 입력하세요 : ");
			int salary = sc.nextInt();
			sc.nextLine();
			
			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY, SALARY * 12 AS ANNAUAL_INCOME\r\n"
					+ " FROM EMPLOYEE\r\n"
					+ " NATURAL JOIN JOB\r\n"
					+ " WHERE JOB_NAME LIKE '%" + jobName + "%' \r\n"
					+ "	AND SALARY > " + salary;
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			System.out.println("\n>>>");
			List<Emp2> list = new ArrayList<>();
			
			while(rs.next()) {
				list.add(new Emp2(rs.getString("EMP_NAME"), rs.getString("JOB_NAME"), 
						rs.getInt("SALARY"), rs.getInt("SALARY") * 12));
			}
			if(list.isEmpty())
				System.out.println("조회 결과 없음");
			else
				for(Emp2 e : list)
					System.out.println(e);
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
