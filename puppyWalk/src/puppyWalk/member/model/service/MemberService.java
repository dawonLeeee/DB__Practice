package puppyWalk.member.model.service;

import static puppyWalk.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import puppyWalk.dog.vo.Dog;
import puppyWalk.member.model.dao.MemberDAO;
import puppyWalk.member.vo.Member;
import puppyWalk.schedule.vo.Schedule;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();
	
	public Map<Schedule, String> selectMySchedule(int memberNo) throws Exception {
		
		Connection conn = getConnection();
		Map<Schedule, String> scheduleMap = dao.selectMySchedule(conn, memberNo);
		
		close(conn);
		
		return scheduleMap;
	}
	
	
	/** 내 정보 수정(이름)
	 * @param memberId
	 * @param memberName
	 * @return
	 * @throws Exception
	 */
	public int updateMyInfo(int memberNo, String memberName) throws Exception {

		int result = 0;
		Connection conn = getConnection();
		result = dao.updateMyInfo(conn, memberNo, memberName);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		return result;
	}
	

	
	/** 비밀번호 변경
	 * @param memberId
	 * @param newPw
	 * @return
	 * @throws Exception
	 */
	public int updatePassword(int memberNo, String memberPw, String newPw) throws Exception{

		Connection conn = getConnection();
		int result = dao.updatePassword(conn, memberNo, memberPw, newPw);
		if(result >0) commit(conn);
		else			rollback(conn);
		
		close(conn);	
		
		return result;
	}
	
	

	

	
	/** 회원 탈퇴
	 * @param loginMember
	 * @throws Exception
	 */
	public int secession(Member loginMember) throws Exception{
		
		Connection conn = getConnection();
		int result = dao.secession(conn, loginMember);
		
		if(result > 0) commit(conn);
		else { rollback(conn); throw new Exception();}
		
		close(conn);
		
		return result;
	}


	/** 파트너가 작성한 예약 조회(미래)
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<Schedule> selectPartnerSchedule(int memberNo, String num) throws Exception {

		Connection conn = getConnection();
		List<Schedule> scheduleList = dao.selectPartnerSchedule(conn, memberNo, num);
		
		close(conn);
		
		return scheduleList;
	}


	/** 파트너 정보 소개
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Member selectPartnerInfo(int memberNo) throws Exception {

		Connection conn = getConnection();
		Member partnerInfo = dao.selectPartnerInfo(conn, memberNo);
		
		close(conn);
		
		return partnerInfo;
	}


	/** 파트너 소개 수정
	 * @param memberId
	 * @param partnerIntro
	 * @return
	 */
	public int updatePartnerInfo(int memberNo, String partnerIntro) throws Exception {

		Connection conn = getConnection();
		int result = dao.updatePartnerInfo(conn, memberNo, partnerIntro);
		if(result >0) commit(conn);
		else			rollback(conn);
		
		close(conn);	
		
		return result;
	}


	/** 반려견 정보 조회
	 * @return
	 * @throws Exception
	 */
	public Dog selectDogInfo(int dogNo) throws Exception{

		Connection conn = getConnection();
		Dog dogInfo = dao.selectDogInfo(conn, dogNo);
		
		close(conn);
		
		return dogInfo;
	}


	/** 반려견 정보 수정
	 * @param memberNo
	 * @param dogInfo
	 * @return
	 */
	public int updateDogInfo(int memberNo, Dog dogInfo) throws Exception  {

		Connection conn = getConnection();
		int result = dao.updateDogInfo(conn, memberNo, dogInfo);
		if(result >0) commit(conn);
		else			rollback(conn);
		
		close(conn);	
		
		return result;
	}


	/** 파트너-스케줄 등록
	 * @param memberNo
	 * @param mySchedule
	 * @return
	 * @throws Exception
	 */
	public int updateSchedule(int memberNo, Schedule mySchedule) throws Exception {

		Connection conn = getConnection();
		int result = dao.updateSchedule(conn, memberNo, mySchedule);
		if(result >0) commit(conn);
		else			rollback(conn);
		
		close(conn);	
		
		return result;
	}




	



	
}
