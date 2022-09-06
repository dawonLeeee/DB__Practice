package edu.kh.jdbc1.model.vo;

public class Emp3 {

	private String empName;
	private String hireDate;
	private String sex;
	
	public Emp3() {
		// TODO Auto-generated constructor stub
	}

	public Emp3(String empName, String hireDate, String sex) {
		super();
		this.empName = empName;
		this.hireDate = hireDate;
		this.sex = sex;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "이름 : " + empName + " / 고용일 : " + hireDate + " / 성별 : " + sex;
	}
	
	

}
