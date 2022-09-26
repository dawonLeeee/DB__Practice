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



	/** 스케줄 예약하기1
	 * @param scheduleNo
	 * @return
	 * @throws Exception
	 */
	public int bookSchedule1(int scheduleNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.bookSchedule1(conn, scheduleNo);
		
		close(conn);
		return result;	
	}



	public int bookSchedule2(int memberNo, int scheduleNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.bookSchedule2(conn, memberNo, scheduleNo);
		
		close(conn);
		return result;	
	}


	
	
}
