package puppyWalk.member.vo;

import java.util.List;

import puppyWalk.schedule.vo.Schedule;
//VO(Value Object) : 값을 저장하는 용도의 객체
// JDBC에서는 테이블의 한 행의 조회결과 또는 삽입, 수정을 위한 데이터를 저장하는 용도의 객체
public class Member {

	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberRRNumber; // 주민등록번호
	private String enrollDate; // DB에서 꺼내올때 TO_CHAR 쓸거라서 String으로 꺼내옴
	private String partnerFlag;
	private String secession;
	private int scheduleNo;
	private List<Schedule> scheduleList;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberRRNumber() {
		return memberRRNumber;
	}

	public void setMemberRRNumber(String memberRRNumber) {
		this.memberRRNumber = memberRRNumber;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getPartnerFlag() {
		return partnerFlag;
	}

	public void setPartnerFlag(String partnerFlag) {
		this.partnerFlag = partnerFlag;
	}

	public String getSecession() {
		return secession;
	}

	public void setSecession(String secession) {
		this.secession = secession;
	}

	public int getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public List<Schedule> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}

	 
	
	
}
