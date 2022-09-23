package puppyWalk.schedule.vo;

import java.util.Objects;

public class Schedule {

	private int ScheduleNo;
	private String scheduleTime;
	private String serviceType;
	private int memberNo;

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

	
}
