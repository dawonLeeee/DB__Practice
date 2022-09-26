package puppyWalk.member.model.service;

import static puppyWalk.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

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
	public int updateMyInfo(String memberId, String memberName) throws Exception {

		int result = 0;
		Connection conn = getConnection();
		result = dao.updateMyInfo(conn, memberId, memberName);
		
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
	public int updatePassword(String memberId, String memberPw, String newPw) throws Exception{

		Connection conn = getConnection();
		int result = dao.updatePassword(conn, memberId, memberPw, newPw);
		if(result >0) commit(conn);
		else			rollback(conn);
		
		close(conn);	
		
		return result;
	}
	
	

	

	
	/** 회원 탈퇴
	 * @param loginMember
	 * @throws Exception
	 */
	public void secession(Member loginMember) throws Exception{
		
		Connection conn = getConnection();
		int result = dao.secession(conn, loginMember);
		
		if(result > 0) commit(conn);
		else { rollback(conn); throw new Exception();}
		
		close(conn);
		
	}




	



	
}
