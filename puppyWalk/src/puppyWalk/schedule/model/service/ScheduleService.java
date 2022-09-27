package puppyWalk.schedule.model.service;

import static puppyWalk.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import puppyWalk.board.vo.Board;
import puppyWalk.schedule.model.dao.ScheduleDAO;
import puppyWalk.schedule.vo.Schedule;

public class ScheduleService {

	private ScheduleDAO dao = new ScheduleDAO();

	
	
	/** 전체 스케줄 검색
	 * @return
	 */
	public List<Schedule> selectAllSchedule(String serviceType) throws Exception {

		Connection conn = getConnection();
		List<Schedule> boardList = dao.selectAllSchedule(conn, serviceType);
		
		close(conn);
		return boardList;
	}



	/** 전체 스케줄 확인
	 * @return
	 * @throws Exception
	 */
	public List<Schedule> selectAllSchedule() throws Exception{

		Connection conn = getConnection();
		List<Schedule> boardList = dao.selectAllSchedule(conn);
		
		close(conn);
		return boardList;	
	}


 
	/** 파트너별 스케줄 조회
	 * @return
	 */
	public List<Schedule> selectScheduleByPartner(String partner) throws Exception {

		Connection conn = getConnection();
		List<Schedule> partnerList = dao.selectScheduleByPartner(conn, partner);
		
		close(conn);
		return partnerList;	
	}



	/** 날짜로 스케줄 검색
	 * @param scheduleTime
	 * @return
	 */
	public List<Schedule> searchScheduleByDate(String scheduleTime) throws Exception {

		Connection conn = getConnection();
		List<Schedule> partnerList = dao.searchScheduleByDate(conn, scheduleTime);
		
		close(conn);
		return partnerList;	
	}



	/** 시간대별 스케줄 검색
	 * @param scheduleTime
	 * @return
	 */
	public List<Schedule> searchScheduleByTime(int scheduleTime) throws Exception {

		Connection conn = getConnection();
		List<Schedule> partnerList = dao.searchScheduleByTime(conn, scheduleTime);
		
		close(conn);
		return partnerList;	
	}



	/** 번호로 스케줄 조회하기
	 * @param scheduleNo
	 * @return
	 */
	public List<Schedule> selectScheduleByNo(int scheduleNo) throws Exception {

		Connection conn = getConnection();
		List<Schedule> partnerList = dao.selectScheduleByNo(conn, scheduleNo);
		
		close(conn);
		return partnerList;	
	}



/** 스케줄 예약하기1(BOOKING테이블에 INSERT)
	 * @param memberNo
	 * @param scheduleNo
	 * @return
	 * @throws Exception
	 */
	public int bookSchedule(int memberNo, int scheduleNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.bookSchedule(conn, memberNo, scheduleNo);
		
		close(conn);
		return result;	
	}



	/** 내가 예약한 스케줄 확인
	 * @param memberNo
	 * @return
	 */
	public List<Schedule> selectMySchedule(int memberNo) throws Exception {

		Connection conn = getConnection();
		List<Schedule> scheduleList = dao.selectMySchedule(conn, memberNo);
		
		close(conn);
		return scheduleList;	
	}



	/** 취소할 수 있는 스케줄 목록 조회
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<Schedule> searchDeleteSchedule(int memberNo) throws Exception {

		Connection conn = getConnection();
		List<Schedule> scheduleList = dao.searchDeleteSchedule(conn, memberNo);
		
		close(conn);
		return scheduleList;	
	}



	/** 스케줄 취소
	 * @param scheduleNo
	 * @param memberNo
	 * @return
	 */
	public int deleteSchedule(int scheduleNo, int memberNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.deleteSchedule(conn, scheduleNo, memberNo);
		
		close(conn);
		return result;
	}



	/** 후기 작성할 수 있는 스케줄 목록 조회
	 * @param memberNo
	 * @return
	 */
	public List<Schedule> searchReviewSchedule(int memberNo) throws Exception {

		Connection conn = getConnection();
		List<Schedule> scheduleList = dao.searchReviewSchedule(conn, memberNo);
		
		close(conn);
		return scheduleList;	
	}


	
	
}
