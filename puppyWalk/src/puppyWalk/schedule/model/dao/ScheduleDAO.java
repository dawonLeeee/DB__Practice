package puppyWalk.schedule.model.dao;

import static puppyWalk.common.JDBCTemplete.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import puppyWalk.board.vo.Board;
import puppyWalk.schedule.vo.Schedule;

public class ScheduleDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	
	public ScheduleDAO() {
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("schedule-query.xml"));
			// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 전체 스케줄 조회
	 * @param conn
	 * @return
	 */
	public List<Schedule> selectAllSchedule(Connection conn, String serviceType) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectAllSchedule1");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, serviceType);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}

	public List<Schedule> selectAllSchedule(Connection conn) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectAllSchedule2");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}

	/** 파트너별 스케줄 조회
	 * @param conn
	 * @return
	 */
	public List<Schedule> selectScheduleByPartner(Connection conn, String partner) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectScheduleByPartner");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, partner);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}

	/** 날짜로 스케줄 검색
	 * @param conn
	 * @param scheduleTime
	 * @return
	 */
	public List<Schedule> searchScheduleByDate(Connection conn, String scheduleTime) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("searchScheduleByDate");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, scheduleTime);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}

	/** 시간대별 스케줄 검색
	 * @param conn
	 * @param scheduleTime
	 * @return
	 */
	public List<Schedule> searchScheduleByTime(Connection conn, int scheduleTime)  throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("searchScheduleByTime");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, scheduleTime);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}

	/** 번호로 스케줄 조회하기
	 * @param conn
	 * @param scheduleNo
	 * @return
	 */
	public List<Schedule> selectScheduleByNo(Connection conn, int scheduleNo) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectScheduleByNo");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, scheduleNo);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}



	/** 스케줄 예약하기
	 * @param conn
	 * @param scheduleNo
	 * @return
	 */
	public int bookSchedule(Connection conn, int memberNo, int scheduleNo) throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("bookSchedule");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, scheduleNo);
			rs = pstmt.executeQuery();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 내가 예약한 스케줄 확인
	 * @param conn
	 * @param memberNo
	 * @return
	 */
	public List<Schedule> selectMySchedule(Connection conn, int memberNo) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectMySchedule");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}

	/** 취소할 수 있는 스케줄 목록 조회
	 * @param conn
	 * @param memberNo
	 * @return
	 */
	public List<Schedule> searchDeleteSchedule(Connection conn, int memberNo) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("searchDeleteSchedule");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}

	/** 스케줄 취소
	 * @param conn
	 * @param scheduleNo
	 * @param memberNo
	 * @return
	 */
	public int deleteSchedule(Connection conn, int scheduleNo, int memberNo) throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("deleteSchedule");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, scheduleNo);
			pstmt.setInt(2, memberNo);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	
	/** 후기 작성할 수 있는 스케줄 목록 조회
	 * @param conn
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<Schedule> searchReviewSchedule(Connection conn, int memberNo) throws Exception {

		List<Schedule> scheduleList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("searchReviewSchedule");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setScheduleNo(rs.getInt("SCHEDULE_NO"));
				schedule.setScheduleTime(rs.getString("SCHEDULE_TIME"));
				schedule.setServiceType(rs.getString("SERVICE_TYPE"));
				schedule.setMemberName(rs.getString("MEMBER_NM"));
				schedule.setIsBook(rs.getString("ISBOOK"));
				
				scheduleList.add(schedule);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return scheduleList;
	}
	
	

}
