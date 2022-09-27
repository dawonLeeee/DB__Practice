package puppyWalk.main.model.service;

import static puppyWalk.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.ArrayList;

import puppyWalk.main.model.dao.MainDAO;
import puppyWalk.member.vo.Member;

// 데이터 가공, 트랜잭션 처리
public class MainService {

	private MainDAO dao = new MainDAO();

	/** 아이디 중복검사 서비스
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String memberId) throws Exception{
		
		
		Connection conn = getConnection();
		int result = dao.idDupCheck(conn, memberId);
		close(conn);
		return result;
	}

	/** 회원가입 service
	 * @param member
	 * @return 1 / 0
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception{

		Connection conn = getConnection();
		int result = dao.signUp(conn, member);
		
		// 트랜잭션 제어
		if(result > 0) commit(conn);
		else			rollback(conn);

		close(conn);
		return result;
	}

	/** 로그인 service
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(String memberId, String memberPw) throws Exception{
		
		
		Connection conn = getConnection();
		Member loginMember = dao.login(conn, memberId, memberPw);
		
		
		close(conn);
		return loginMember;
	}

	/** 회원 탈퇴
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public int dropMember(int memberNo) throws Exception{

		Connection conn = getConnection();
		int result = dao.dropMember(conn, memberNo);
		
		// 트랜잭션 제어
		if(result > 0) commit(conn);
		else			rollback(conn);

		close(conn);
		return result;
	}




	
	



	


	
	
}
