package puppyWalk.member.model.service;

import static puppyWalk.common.JDBCTemplete.*;

import java.sql.Connection;
import puppyWalk.member.model.dao.MemberDAO;
import puppyWalk.member.vo.Member;

public class MemberService {

	private MemberDAO dao = new MemberDAO();
	/** 내 정보 수정(이름, 성별)
	 * @param memberName
	 * @param memberGender
	 * @return
	 */
	public int updateMember(String memberId, String memberName) throws Exception{ 

		int result = 0;
		Connection conn = getConnection();
		result = dao.updateMember(conn, memberId, memberName);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
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

	public int updatePw(String memberId, String newPw) throws Exception{

		Connection conn = getConnection();
		int result = dao.updatePw(conn, memberId, newPw);
		if(result >0) commit(conn);
		else			rollback(conn);
		
		close(conn);	
		
		return result;
	}
}
