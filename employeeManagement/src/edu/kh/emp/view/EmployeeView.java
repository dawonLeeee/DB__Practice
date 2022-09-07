package edu.kh.emp.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

// 화면용 클래스(입력_Scanner, 출력_print()) 담당
public class EmployeeView {
	
	// 여기에 전체 list 갯수를 담는 int형 변수 하나 선언하기-> DQL 확인용

	private Scanner sc = new Scanner(System.in);
	private EmployeeDAO dao = new EmployeeDAO();
	
	// 메인 메뉴
	public void displayMenu() {
		
		int input = 0;
		do {
			try {
				System.out.println("\n==============[ 사원 서비스 선택 ]==============");
				
				System.out.println("1. 새로운 사원 정보 추가");
				System.out.println("2. 전체 사원 정보 조회");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				System.out.println("6. 입력 받은 부서와 일치 모든 사원 정보 조회");
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				System.out.println("8. 부서별 급여 합 전체 조회");
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				System.out.println("0. 프로그램 종료");
				System.out.println();
				
				System.out.print("번호 선택 >> ");
				input = sc.nextInt();
				
				System.out.println();
				
				switch(input) {
				case 1 : //새로운 사원 정보 추가
					insertEmployee(); break;
					
				case 2 : //전체 사원 정보 조회
					selectAll(); break;
					
				case 3 : // 사번이 일치하는 사원 정보 조회
					selectEmpId(); 	break;
					
				case 4 : // 사번이 일치하는 사원 정보 수정
					updateEmployee(); break;
					
				case 5 : // 사번이 일치하는 사원 정보 삭제
					deleteEmployee(); break;
					
				case 6 : // 입력 받은 부서와 일치 모든 사원 정보 조회
					viewDepartmentImpl(); break;
					
				case 7 : // 입력 받은 급여 이상을 받는 모든 사원 정보 조회
					viewImplBySalary(); break;
					
				case 8 : // 부서별 급여 합 전체 조회
					viewSalaryByDepartment(); break;
					
				case 9 : // 사번이 일치하는 사원 정보 조회
					selectEmpNo(); 	break;
					
				case 0 : // 프로그램 종료
					System.out.println("==============[ 프로그램 종료 ]==============");
					break;
					
				default : 
					System.out.println("메뉴에 존재하는 번호만 입력하세요");
				}
				
				
			} catch(InputMismatchException e) {
				System.out.println("정수만 입력해주세요");
				input = -1; // do~while문 첫 번째 바퀴에서 잘못 입력하면 종료되는 상황 방지
				sc.nextLine(); // buffer에서 문자열 + enter 제거해서 무한반복 방지
			}

		} while(input != 0);
	}


	/**
	 * 1. 새로운 사원 정보 추가
	 */
	public void insertEmployee() {
		System.out.println("<새로운 사원 정보 추가>");
		
		int empId = inputEmpId();

		System.out.print("이름 : ");
		String empName = sc.nextLine();
		
		System.out.print("주민등록번호 : ");
		String empNo = sc.nextLine();
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(-제외) : ");
		String phone = sc.nextLine();
		
		System.out.print("부서코드(D1~D9) : ");
		String deptCode = sc.nextLine();
		
		System.out.print("직급코드(J1~J7) : ");
		String jobCode = sc.nextLine();
		
		System.out.print("급여등급(S1~S6) : ");
		String salLevel = sc.nextLine();
		
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		
		System.out.print("보너스 : ");
		double bonus = sc.nextDouble();
		
		System.out.print("사수번호 : ");
		int managerId = sc.nextInt();
		
		//입력받은 값을 
		// Employee 객체에 담아 DAO로 전달
		Employee emp = new Employee(empId, empName,empNo, email, phone,  deptCode,
				jobCode, salLevel, salary, bonus, managerId);
		
		int result = dao.insertEmployee(emp);
		// INSERT, UPDATE, DELETE같은 DML구문은 수행 후 테이블에 반영된 행의 갯수를 반환함
		// 조건이 잘못된 경우 반영된 행이 없으므로 0 반환
		
		if(result > 0) { // DML 구문 성공시
			System.out.println("사원 정보 추가 성공");
		} else { // DML 구문 실패시
			System.out.println("사원 정보 추가 실패");
		}
	}

	/**
	 * 2. 전체 사원 정보 조회
	 */
	public void selectAll() {
		System.out.println("<전체 사원 정보 조회>");
		
		List<Employee> empList = dao.selectAll();
		printAll(empList);
	}
	
	/**
	 * 3. 사번이 일치하는 사원 정보 조회
	 */
	public void selectEmpId() {
		System.out.println("<사번이 일치하는 사원 정보 조회>");
				
		Employee emp = dao.selectEmpId(inputEmpId());
		printOne(emp);
	}
	
	/**
	 * 4. 사번이 일치하는 사원 정보 수정
	 */
	private void updateEmployee() {
		System.out.println("<사번이 일치하는 사원 정보 수정>");
		
		int empId = inputEmpId();
		
		System.out.print("이메일 : ");
		String email = sc.next();
		
		System.out.print("전화번호(-제외) : ");
		String phone = sc.next();
		
		
		System.out.print("급여 : ");
		int salary = sc.nextInt(); 
		sc.nextLine();
		
		
		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setSalary(salary);
		
		int result = dao.updateEmployee(emp); // UPDATE(DML) -> 반영된 행의 갯수 반환(int형)
		
		if(result > 0) { // DML 구문 성공시
			System.out.println("사원 정보가 수정되었습니다");
		} else { // DML 구문 실패시
			System.out.println("일치하는 사번이 종료하지 않습니다");
		}
	}
	

	/**
	 * 5. 사번이 일치하는 사원 정보 삭제
	 */
	public void deleteEmployee() {
		System.out.println("<사번이 일치하는 사원 정보 삭제>");
		
		int empId = inputEmpId();
		
		System.out.print("정말 삭제하시겠습니까?(Y/N) : ");
		char input = sc.next().toUpperCase().charAt(0);
		if(input == 'Y') {
			// 삭제를 수행하는 DAO 호출
			// 성공 : "삭제되었습니다."
			// 실패 : "사번이 일치하는 사원이 존재하지 않습니다."
			int result = dao.deleteEmployee(empId);
			if(result > 0)
				System.out.println("삭제되었습니다.");
			else
				System.out.println("사번이 일치하는 사원이 존재하지 않습니다.");
		} else {
			System.out.println("사용자의 삭제 취소");
		}
	}
	
	
	/**
	 * 6. 입력 받은 부서와 일치 모든 사원 정보 조회
	 */
	
	public void viewDepartmentImpl() {
		System.out.println("<입력 받은 부서와 일치 모든 사원 정보 조회>");
		
		System.out.print("부서명 입력 : ");
		String departmentTitle = sc.next(); // ?????????????????????????
		
		List<Employee> empList = dao.viewDepartmentImpl(departmentTitle);
		printAll(empList);
	}
	
	/**
	 * 7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회
	 */
	public void viewImplBySalary() {
System.out.println("<입력 받은 급여 이상을 받는 모든 사원 정보 조회>");
		
		System.out.print("급여 입력 : ");
		int inputSalary = sc.nextInt(); 
		
		
		List<Employee> empList = dao.viewImplBySalary(inputSalary);
		printAll(empList);
		
	}
	
	/**
	 * 8. 부서별 급여 합 전체 조회
	 */
	public void viewSalaryByDepartment() {


		List<Employee> empList = dao.viewSalaryByDepartment();
		printDeptAndSalary(empList);
	}
	
	/**
	 * 주민등록번호가 일치하는 사원 정보 조회
	 */
	public void selectEmpNo() {
		System.out.println("<주민등록번호가 일치하는 사원 정보 조회>");
		
		sc.nextLine();
		String empNo = sc.nextLine();
		printOne(dao.selectEmpNo(empNo));
		
	}
// ---------------------------------------------------------------------
	
	/** 사원 정보 모두 출력
	 * @param empList
	 */
	public void printAll(List<Employee> empList) {
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			for(Employee emp : empList) { 
				System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
						emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
						emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			}
		}
	}
	
	/** 사원 1명 정보 출력
	 * @param emp
	 */
	public void printOne(Employee emp) {
		if(emp == null) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println(" 사번  |   이름  |   주민 등록 번호  |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			
		}
	}
	
	/** 사번을 입력받아 반환하는 메서드
	 * @return
	 */
	public int inputEmpId() {
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt(); 
		sc.nextLine(); // 버퍼에 남은 개행문자 제거
		return empId;
	}
	
	
	public void printDeptAndSalary(List<Employee> empList) {
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("| 부서 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			for(Employee emp : empList) { 
				System.out.printf(" %5s | %d\n",
						emp.getDepartmentTitle(), emp.getSalary());
			}
		}
	}

}
