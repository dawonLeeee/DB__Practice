package puppyWalk.member.model.dao;

import static puppyWalk.common.JDBCTemplete.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import puppyWalk.dog.vo.Dog;
import puppyWalk.member.vo.Member;
import puppyWalk.partner.vo.Partner;
import puppyWalk.schedule.vo.Schedule;

public class MemberDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;

	public MemberDAO() {

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("member-query.xml"));
			// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/** 내 정보 조회
	 * @param conn
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Map<Schedule, String> selectMySchedule(Connection conn, int memberNo) throws Exception {

		
		
		Map<Schedule, String> scheduleMap = null;
		String sql = prop.getProperty("selectMySchedule");
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberNo);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			scheduleMap = new HashMap<>();
			Schedule schedule = new Schedule();
			schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
			schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
			schedule.setServiceType(rs.getString("SERVICE_TYPE"));
			String partnerName = rs.getString("MEMBER_NM");
			
			scheduleMap.put(schedule, partnerName);

		}
		
		close(rs);
		close(pstmt);
		
		return scheduleMap;
	}
	
	
	/** 내 정보 수정(이름)
	 * @param conn
	 * @param memberName
	 * @param memberGender
	 * @return
	 */
	public int updateMyInfo(Connection conn, int memberNo, String memberName) throws Exception{

		String sql = prop.getProperty("updateMyInfo");
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberName);
		pstmt.setInt(2, memberNo);
		
		int result = pstmt.executeUpdate();
		
		close(pstmt);
		
		return result;
	}
	
	
	/** 비밀번호 변경
	 * @param conn
	 * @param memberId
	 * @param newPw
	 * @return
	 * @throws Exception
	 */
	public int updatePassword(Connection conn, int memberNo, String memberPw, String newPw) throws Exception{

		int result = 0;
		try {
			String sql = prop.getProperty("updatePassword");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setInt(2, memberNo);
			pstmt.setString(3, memberPw);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	

	
	/** 회원 탈퇴
	 * @param conn
	 * @param loginMember
	 * @return
	 * @throws Exception
	 */
	public int secession(Connection conn, Member loginMember) throws Exception{
		
		int result = 0;
		try {
			String sql = prop.getProperty("secession");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loginMember.getMemberNo());
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		return result;
	}



	/** 파트너가 작성한 예약 조회
	 * @param conn
	 * @param memberNo
	 * @return
	 */
	public List<Schedule> selectPartnerSchedule(Connection conn, int memberNo, String num) throws Exception{

		List<Schedule> scheduleList = new ArrayList<>();
		String sql = prop.getProperty("selectPartnerSchedule" + num);
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberNo);
		
		

		rs = pstmt.executeQuery();
		while(rs.next()) {
			
			Schedule schedule = new Schedule();
			schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
			schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
			schedule.setServiceType(rs.getString("SERVICE_TYPE"));
			schedule.setIsBook(rs.getString("ISBOOK"));
			schedule.setMemberName(rs.getString("MEMBER_NM"));
			
			scheduleList.add(schedule);

		}
		
		close(rs);
		close(pstmt);
		
		return scheduleList;
	}



	/** 파트너 정보 소개
	 * @param conn
	 * @param memberNo
	 * @return
	 */
	public Member selectPartnerInfo(Connection conn, int memberNo) throws Exception{

		Member partnerInfo = null;
		String sql = prop.getProperty("selectPartnerInfo");
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberNo);
		
		rs = pstmt.executeQuery();
		if(rs.next()) {
			
			partnerInfo = new Member();
			partnerInfo.setPartnerIntro(rs.getString("PARTNER_INTRO"));
			partnerInfo.setPartnerGrade(rs.getInt("PARTNER_GRADE"));
			

		}
		
		close(rs);
		close(pstmt);
		
		return partnerInfo;
	}



	/** 반려견 정보 조회
	 * @param conn
	 * @param dogNo
	 * @return
	 * @throws Exception
	 */
	public Dog selectDogInfo(Connection conn, int dogNo) throws Exception {

		Dog dogInfo = null;
		String sql = prop.getProperty("selectDogInfo");
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, dogNo);
		
		rs = pstmt.executeQuery();
		if(rs.next()) {
			
			dogInfo = new Dog();
			dogInfo.setDogName((rs.getString("DOG_NAME")== null? "정보 없음": rs.getString("DOG_NAME")));
			dogInfo.setDogGender((rs.getString("DOG_GENDER")== null? "정보 없음": rs.getString("DOG_GENDER")));
			dogInfo.setDogAge(rs.getInt("DOG_AGE"));
			dogInfo.setDogComment((rs.getString("DOG_COMMENT")== null? "정보 없음": rs.getString("DOG_COMMENT")));
			dogInfo.setDogAlert((rs.getString("DOG_ALERT")== null? "정보 없음": rs.getString("DOG_ALERT")));
			dogInfo.setDogVar((rs.getString("DOG_VAR")== null? "정보 없음": rs.getString("DOG_VAR")));

		}
		
		close(rs);
		close(pstmt);
		
		return dogInfo;
	}



	/** 반려견 정보 수정
	 * @param conn
	 * @param memberNo
	 * @param dogInfo
	 * @return
	 * @throws Exception
	 */
	public int updateDogInfo(Connection conn, int memberNo, Dog dogInfo) throws Exception {

		int result = 0;
		try {
			
			String sql = prop.getProperty("updateDogInfo");
			pstmt = conn.prepareStatement(sql);
			
			if(!dogInfo.getDogName().equals("")) {
				pstmt.setString(1, dogInfo.getDogName());
			} else
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			
			if(!dogInfo.getDogGender().equals("")) {
				pstmt.setString(2, dogInfo.getDogGender());
			} else
				pstmt.setNull(2, java.sql.Types.CHAR);
			
			if(!dogInfo.getDogComment().equals("")) {
				pstmt.setString(4, dogInfo.getDogComment());
			} else
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			
			if(!dogInfo.getDogAlert().equals("")) {
				pstmt.setString(5, dogInfo.getDogAlert());
			} else
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			
			if(!dogInfo.getDogVar().equals("")) {
				pstmt.setString(6, dogInfo.getDogVar());
			} else
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			

			pstmt.setInt(3, dogInfo.getDogAge());
			pstmt.setInt(7, memberNo);
			
			
			
			
			
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}



	/** 파트너 소개 수정
	 * @param conn
	 * @param memberNo
	 * @param partnerIntro
	 * @return
	 * @throws Exception
	 */
	public int updatePartnerInfo(Connection conn, int memberNo, String partnerIntro) throws Exception {

		int result = 0;
		try {
			String sql = prop.getProperty("updatePartnerInfo");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, partnerIntro);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		return result;
	}



	/** 파트너-스케줄 등록
	 * @param conn
	 * @param memberNo
	 * @param mySchedule
	 * @return
	 */
	public int updateSchedule(Connection conn, int memberNo, Schedule mySchedule) throws Exception {

		int result = 0;
		
		boolean isFuture = isFuture(conn, mySchedule);
		
		if(isFuture) {
			try {
				String sql = prop.getProperty("updateSchedule");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mySchedule.getScheduleTime());
				pstmt.setString(2, mySchedule.getServiceType());
				pstmt.setInt(3, memberNo);
				
				result = pstmt.executeUpdate();
				
				
			} finally {
				close(pstmt);
			}
		}
		return result;
	}
	
	public boolean isFuture(Connection conn, Schedule mySchedule) throws Exception  {
		
		boolean result = false;
		
		try {
			String sql = prop.getProperty("isPast");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mySchedule.getScheduleTime());
			
			
			rs = pstmt.executeQuery();
			if(rs.next())
				result = true;
			
			
		} finally {
			close(pstmt);
		}
		return result;
		
	}



	

	
}
