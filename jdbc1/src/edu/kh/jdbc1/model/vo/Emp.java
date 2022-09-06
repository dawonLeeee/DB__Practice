package edu.kh.jdbc1.model.vo;

//VO(Value Object) : 값 저장용 객체(저장된 값을 읽는 용도로 사용)
// -> 비슷한 단어로 DTO(Data Transfer Object) : 데이터를 교환하는 용도의 객체
// -> (값을 읽고 쓰는 용도)

// VO, DTO 모두 값을 저장하는 용도
// 실무에서는 두 단어가 거의 비슷한 뜻으로 사용됨


//DB에서 조회된 사원 한 명(1행)의 정보를 저장하는 객체
public class Emp {
	private String empName;
	private String deptTitle;
	private int salary;
	
	public Emp() {
		// TODO Auto-generated constructor stub
	}
	
	public Emp(String emp_name, String dept_title, int salary) {
		super();
		this.empName = emp_name;
		this.deptTitle = dept_title;
		this.salary = salary;
	}

	public String getEmp_name() {
		return empName;
	}

	public void setEmp_name(String emp_name) {
		this.empName = emp_name;
	}

	public String getDept_title() {
		return deptTitle;
	}

	public void setDept_title(String dept_title) {
		this.deptTitle = dept_title;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "이름 : " + empName + " | 부서명 : " + deptTitle + " | 급여 : " + salary;
	}
	
	
	
	
}
