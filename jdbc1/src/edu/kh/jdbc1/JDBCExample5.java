package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import edu.kh.jdbc1.model.vo.Emp3;

public class JDBCExample5 {
	public static void main(String[] args) {
		
		// 입사일을 입력("2022-09-06")받아 
		// 입력받은 값보다 먼저 입사한 사람의 
		// 이름, 입사일, 성별(M/F) 조회
		
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh_ldw";
			String pw = "kh1234";
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
			
			System.out.print("입사일 입력 : ");
			String inputHireDate = sc.nextLine();
			String newHireDate = "";
			for(int i = 0; i < inputHireDate.length(); i++) {
				if(inputHireDate.charAt(i) - '0' <= 9 
						&& inputHireDate.charAt(i) - '0' >= 0) newHireDate += inputHireDate.charAt(i);
			}
			if(newHireDate.length() <= 6)
				newHireDate = "19" + newHireDate;
			
			System.out.println();
//			System.out.println(newHireDate);
			
			
			String sql = "SELECT EMP_NAME, TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"') AS \"HIREDATE\", \r\n"
					+ "	DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') AS \"SEX\"\r\n"
					+ "FROM EMPLOYEE e \r\n"
					+ "WHERE HIRE_DATE > '" + newHireDate + "'"
					+ "ORDER BY TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"')";
			rs = stmt.executeQuery(sql);
			
			List<Emp3> list = new ArrayList<>();
			while(rs.next()) {
				list.add(new Emp3(rs.getString("EMP_NAME"), rs.getString("HIREDATE"), rs.getString("SEX")));
			}
			
			if(list.isEmpty())
				System.out.println("데이터 없음");
			else
				for(Emp3 e : list)
					System.out.println(e);
			
			
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
