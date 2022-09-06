package edu.kh.jdbc1.model.vo;

import java.text.DecimalFormat;

public class Emp2 {
	private String empName;
	private String deptTitle;
	private int salary;
	private int annualIncome; // 연봉(연간 수입)
	
	public Emp2() {
		// TODO Auto-generated constructor stub
	}

	public Emp2(String empName, String deptTitle, int salary, int annualIncome) {
		super();
		this.empName = empName;
		this.deptTitle = deptTitle;
		this.salary = salary;
		this.annualIncome = annualIncome;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDeptTitle() {
		return deptTitle;
	}

	public void setDeptTitle(String deptTitle) {
		this.deptTitle = deptTitle;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}

	@Override
	public String toString() {
		DecimalFormat dcmFormat = new DecimalFormat("#,##0");
		
		return empName + " / " + deptTitle + " / " + dcmFormat.format(salary) + " / "
				+ dcmFormat.format(annualIncome);
	}

	
}
