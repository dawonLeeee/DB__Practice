package edu.kh.emp.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import edu.kh.emp.model.vo.Employee;

// DAO(Data Access Object; 데이터 접근 객체)
// -> 데이터베이스에 접근(연결)하는 객체
public class EmployeeDAO {
	
	// JDBC 객체 참조변수로 필드 선언(class 내부에서 공통으로 사용 가능)
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	private PreparedStatement pstmt;
	// Statement클래스의 자식으로, 향상된 기능 제공
	// -> ?기호(placeholder/ 위치홀더)를 이용해서
	// SQL에 작성되어지는 리터럴을 동적으로 제어
	
	// SQL ?기호에 추가되는 값은
	// 숫자인 경우 ''없이 대입
	// 문자열인 경우 ''가 자동으로 추가되어 대입
	
	/* PreparedStatement 객체 사용 시 순서 :
		- SQL 작성 -> PreparedStatement 객체 생성(?가 포함된 SQL을 매개변수로 사용
		- ?에 알맞은 값 대입
		- SQL 수행 후 결과 반환
	
	*/
	
	// 메서드 안에 생성된 지역변수는 Stack영역에 생성되며, 변수가 비어있을 수 있음
	// 필드에 생성되는 전역변수는 Heap 영역에 생성되며, 변수가 비어있을 수 "없음"
	// -> JVM이 지정한 기본값으로 초기화. 참조형의 초기값은 null -> 별도 초기화 필요없음
	
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "kh_ldw";
	private String pw = "kh1234";
//	jdbc:oracle:thin:@localhost:1521:XE
	private String driver = "oracle.jdbc.driver.OracleDriver";
	
	
	

	/** 1. 사원 정보 추가 DAO
	 * @param emp
	 * @return result(INSERT 성공한 행의 갯수 반환)
	 */
	public int insertEmployee(Employee emp) {
		
		int result = 0;

		
		try {
			
			Class.forName(driver); 
			
			conn = DriverManager.getConnection(url, user, pw); 
			
			// ** DML 수행할 예정 **
			// 트랜잭션에 DML구문이 임시저장
			// -> 정상적인 DML인지 판별해서 개발자가 직접 COMMIT, ROLLBACK을 수행
			// 하지만, Connection 객체 생성시 AutoCommit이 활성화되어 있는 상태이기 때문에
			// 이를 해제하는 코드를 추가해야함!!!!!!!!!
			
			conn.setAutoCommit(false); // AutoCommit 비활성화
			// ->AutoCommit 비활성화 해도
			// conn.close() 구문이 수행되면 자동으로 커밋이 수행됨
			// --> close() 수행 전 트랜잭션 제어 코드를 작성해야함!
			
			String sql = "INSERT INTO EMPLOYEE "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL, DEFAULT)";
			// 퇴사여부 컬럼의 DEFALUT == 'N'
			
			// PreparedStatement 객체 생성(SQL 매개변수 필요)
			pstmt = conn.prepareStatement(sql);
			
			// ?(placehoder)에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpId());
	        pstmt.setString(2, emp.getEmpName());
	        pstmt.setString(3, emp.getEmpNo());
	        pstmt.setString(4, emp.getEmail());
	        pstmt.setString(5, emp.getPhone());
	        pstmt.setString(6, emp.getDeptCode());
	        pstmt.setString(7, emp.getJobCode());
	        pstmt.setString(8, emp.getSalLevel());
	        pstmt.setInt(9, emp.getSalary());
	        pstmt.setDouble(10, emp.getBonus());
	        pstmt.setInt(11, emp.getManagerId());
	         
	         // SQL 수행 후 결과 반환 받기
	        result = pstmt.executeUpdate();
	         // executeUpdate() : DML(INSERT, UPDATE, DELETE) 수행 후 결과 행 갯수 반환
	         // executeQuery() : SELECT 수행 후 ResultSet 반환
			
	        
			// *** 트랜잭션 제어 처리 ***
			// -> DML 성공 여부에 따라서 commit, rollback 제어
	        if(result > 0) conn.commit(); // DML 성공시 commit
	        else			conn.rollback(); // DML 실패시 rollback;
				
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	

	/** 2. 전체 사원정보 조회 DAO
	 * @return Employee
	 */
	public List<Employee> selectAll() {
		// 1. 결과 저장용 변수 선언
		List<Employee> empList = new ArrayList<>();
		
		try {
			Class.forName(driver); 
			// 오라클 jdbc 드라이버 객체 메모리 로드
			conn = DriverManager.getConnection(url, user, pw); 
			// 오라클 jdbc 드라이버 객체를 이용하여 DB 접속 방법 생성
			stmt = conn.createStatement(); 
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, NVL(DEPT_TITLE, '부서 없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)";
			rs = stmt.executeQuery(sql); //SQL 수행 후 결과 반환받음
			
			//3. 조회 결과를 얻어와 한 행씩 접근하여 Employee 객체 생성 후 List에 옮겨담음
			while(rs.next()) {
				// EMP_ID컬럼은 문자열 컬럼이지만 저장된 값들이 모두 숫자형태-> DB에서 자동 형변환
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				empList.add(new Employee(empId,  empName, empNo, email,  phone, departmentTitle, jobName,  salary));
				
			}
			
		} catch(ClassNotFoundException | SQLException e) {
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
		
		
		
		
		//2. 결과 반환
		
		
		return empList;
	
	}

	
	/** 3. 사번이 일치하는 사원 정보 조회 DAO
	 * @param inputeEmpId
	 * @return
	 */
	public Employee selectEmpId(int empId) {
		
		Employee emp = null;
		// 만약 조회 결과가 있으면 Employee 객체를 생성해서 emp에 대입(null이 아닌 상태)
		// 만약 조회 결과가 없으면 emp = null
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
		
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, NVL(DEPT_TITLE, '부서 없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
				+ "FROM EMPLOYEE\r\n"
				+ "LEFT JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE)\r\n"
				+ "JOIN JOB USING(JOB_CODE)\r\n"
				+ "WHERE EMP_ID = " + empId;
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql);
			
			// 조회 결과가 최대 1행인 경우 while 대신 if문 사용
			if(rs.next()) {	
				emp = new Employee(rs.getInt("EMP_ID"),
						rs.getString("EMP_NAME"),
						rs.getString("EMP_NO"), 
						rs.getString("EMAIL"),  
						rs.getString("PHONE"), 
						rs.getString("DEPT_TITLE"), 
						rs.getString("JOB_NAME"),
						rs.getInt("SALARY"));
				
			}
				
		} catch(Exception e) {
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
				
		return emp;
	}

	
	
	
	/**  4. 사번이 일치하는 사원 정보 수정 DAO
	 * @param emp
	 * @return
	 */
	public int updateEmployee(Employee emp) {
		int result = 0;
		
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, user, pw);
			conn.setAutoCommit(false);
			
			String sql = "UPDATE EMPLOYEE SET "
					+ "EMAIL = ?, PHONE = ?, SALARY = ? "
					+ "WHERE EMP_ID = ?";
			
			//PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate();
			
			if(result == 0) conn.rollback();
			else			conn.commit();

		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		return result;
	}

	

	/** 5. 사번이 일치하는 사원 정보 삭제 DAO
	 * @param empId
	 * @return
	 */
	public int deleteEmployee(int empId) {
		int result = 0;
		
		try {
			
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, user, pw);
			conn.setAutoCommit(false);
			
			String sql = "DELETE FROM EMPLOYEE "
					+ "WHERE EMP_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			
			
			result = pstmt.executeUpdate();
			if(result != 0) conn.commit();
			else 			conn.rollback();
				
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}

	
	/** 6. 입력 받은 부서와 일치 모든 사원 정보 조회 DAO
	 * @param departmentTitle
	 */
	public List<Employee> viewDepartmentImpl(String departmentTitle) {

		List<Employee> empList = new ArrayList<>();
		
		try {
			
			Class.forName(driver); 
			
			conn = DriverManager.getConnection(url, user, pw); 
			stmt = conn.createStatement(); 
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, NVL(DEPT_TITLE, '부서 없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE DEPT_TITLE = '" + departmentTitle + "'";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {	
				empList.add(new Employee(rs.getInt("EMP_ID"),
						rs.getString("EMP_NAME"),
						rs.getString("EMP_NO"), 
						rs.getString("EMAIL"),  
						rs.getString("PHONE"), 
						rs.getString("DEPT_TITLE"), 
						rs.getString("JOB_NAME"),
						rs.getInt("SALARY")));
			}
				
		} catch(Exception e) {
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
				
		return empList;
		
	}

	
	
	
	/**7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회 DAO
	 * @param inputSalary
	 */
	public List<Employee> viewImplBySalary(int inputSalary) {
		
		List<Employee> empList = new ArrayList<>();
		
		try {
			
			Class.forName(driver); 
			
			conn = DriverManager.getConnection(url, user, pw); 
			stmt = conn.createStatement(); 
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, NVL(DEPT_TITLE, '부서 없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE SALARY >= " + inputSalary;
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {	
				empList.add(new Employee(rs.getInt("EMP_ID"),
						rs.getString("EMP_NAME"),
						rs.getString("EMP_NO"), 
						rs.getString("EMAIL"),  
						rs.getString("PHONE"), 
						rs.getString("DEPT_TITLE"), 
						rs.getString("JOB_NAME"),
						rs.getInt("SALARY")));
			}
				
		} catch(Exception e) {
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
				
		return empList;
	}

	
	
	/**8. 부서별 급여 합 전체 조회 DAO
	 * @return
	 */
	public LinkedHashMap<String, Double> viewSalaryByDepartment() {
		LinkedHashMap<String, Double> salaryListByDept = new LinkedHashMap<>();
		
		try {
			
			Class.forName(driver); 
			
			conn = DriverManager.getConnection(url, user, pw); 
			stmt = conn.createStatement(); 
			String sql = "SELECT DEPT_TITLE, ROUND(AVG(SALARY), 1) SALARY\r\n"
			+ "FROM EMPLOYEE e \r\n"
			+ "JOIN DEPARTMENT  ON(DEPT_ID = DEPT_CODE)\r\n"
			+ "GROUP BY DEPT_TITLE";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {	
				salaryListByDept.put(rs.getString("DEPT_TITLE"), rs.getDouble("SALARY"));
			}
				
		} catch(Exception e) {
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
				
		return salaryListByDept;
		
	}






	/**9. 주민등록번호가 일치하는 사원 정보 조회
	 * @param empNo
	 * @return
	 */
	public Employee selectEmpNo(String empNo) {
		
		Employee emp = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, NVL(DEPT_TITLE, '부서 없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE EMP_NO = ?";
			// ? : placeholder
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값 대입하기
			// the first parameter is 1, the second is 2, ...x the parameter value
			pstmt.setString(1, empNo);
			
			//SQL 수행 후 결과 반환
			// PreparedStatement는 객체생성시 이미 SQL이 담겨져 있는 상태이므로
			// SQL 수행 시 매개변수로 전달할 필요가 없다
			// 실수로 SQL을 매개변수에 추가하면 ?에 작성했던 값이 모두 사라져 수행시 오류가 발생
			rs = pstmt.executeQuery();
			

			
			if(rs.next()) {
				emp = new Employee(rs.getInt("EMP_ID"),
						rs.getString("EMP_NAME"),
						rs.getString("EMP_NO"), 
						rs.getString("EMAIL"),  
						rs.getString("PHONE"), 
						rs.getString("DEPT_TITLE"), 
						rs.getString("JOB_NAME"),
						rs.getInt("SALARY"));
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return emp;
	}



	/** 
	 * 10. 직급별 급여 평균 조회
	 * @return
	 */
	public LinkedHashMap<String, Double> viewSalaryByjobCode() {
		LinkedHashMap<String, Double> salaryListByJob = new LinkedHashMap<>();
		try {
			
			Class.forName(driver); 
			
			conn = DriverManager.getConnection(url, user, pw); 
			stmt = conn.createStatement(); 
			String sql = "SELECT JOB_NAME, ROUND(AVG(SALARY), 1) SALARY\r\n"
					+ "FROM EMPLOYEE e \r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "GROUP BY JOB_NAME";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {	
				salaryListByJob.put(rs.getString("JOB_NAME") , rs.getDouble("SALARY"));
			}
				
		} catch(Exception e) {
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
		
		
		
		return salaryListByJob;
	}












}
