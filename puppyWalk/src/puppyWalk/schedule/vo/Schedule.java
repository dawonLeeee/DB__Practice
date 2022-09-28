package puppyWalk.schedule.vo;

import java.util.List;
import java.util.Objects;

import puppyWalk.member.vo.Member;
import puppyWalk.partner.vo.Partner;

public class Schedule {

	private int ScheduleNo;
	private String scheduleTime;
	private String serviceType;
	private int memberNo;
	private String memberName;
	private String isBook;
	private List<Member> partnerInfoList; 

	
	
	public List<Member> getPartnerInfoList() {
		return partnerInfoList;
	}

	public void setPartnerInfoList(List<Member> partnerInfoList) {
		this.partnerInfoList = partnerInfoList;
	}

	public String getIsBook() {
		return isBook;
	}

	public void setIsBook(String isBook) {
		this.isBook = isBook;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Schedule() {
		// TODO Auto-generated constructor stub
	}

	public int getScheduleNo() {
		return ScheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		ScheduleNo = scheduleNo;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ScheduleNo);
	}

	@Override
	public boolean equals(Object obj) {
		return ScheduleNo == ((Schedule)obj).getScheduleNo();
	}

	@Override
	public String toString() {

		return "Schedule [스케줄번호 : " + ScheduleNo + " | 예약시간 : " + scheduleTime + " | 분류 : " + serviceType
				+ "\n 파트너 : " + memberName  + "]";
	}

	
}
