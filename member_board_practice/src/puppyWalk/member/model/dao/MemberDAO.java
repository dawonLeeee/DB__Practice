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

import puppyWalk.member.vo.Member;
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
		close(stmt);
		
		return scheduleMap;
	}
	
	
	/** 내 정보 수정(이름)
	 * @param conn
	 * @param memberName
	 * @param memberGender
	 * @return
	 */
	public int updateMyInfo(Connection conn, String memberId, String memberName) throws Exception{

		String sql = prop.getProperty("updateMyInfo");
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberName);
		pstmt.setString(2, memberId);
		
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
	public int updatePassword(Connection conn, String memberId, String memberPw, String newPw) throws Exception{

		int result = 0;
		try {
			String sql = prop.getProperty("updatePassword");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPw);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	
	///////////////////////////////////////////////////////////////////

//	/** 회원 목록 조회(아이디, 이름, 성별)
//	 * @param conn
//	 * @return
//	 * @throws Exception
//	 */
//	public ArrayList<Member> lookMembers(Connection conn) throws Exception {
//		
//		ArrayList<Member> memberList = new ArrayList<>();
//		try {
//			String sql = prop.getProperty("lookMembers");
//			stmt = conn.createStatement();
//
//			rs = stmt.executeQuery(sql);
//			while (rs.next()) {
//				memberList.add(new Member(rs.getString("MEMBER_ID"), rs.getString("MEMBER_NM")));
//			}
//		} finally {
//			close(rs);
//			close(stmt);
//		}
//		
//		return memberList;
//	}


	
	

	
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



	

	
}
