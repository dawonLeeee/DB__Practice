package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.ArrayList;

import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.vo.Member;

// 데이터 가공, 트랜잭션 처리
public class MainService {

	private MainDAO dao = new MainDAO();

	/** 아이디 중복검사 서비스
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String memberId) throws Exception{
		
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. dao메서드 호출 후 결과 반환받기
		int result = dao.idDupCheck(conn, memberId);
		
		
		// 3. Connection 반환	(SELECT 구문은 트랜잭션 제어 필요X)
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

	public int contextfx() throws Exception{
		

		Connection conn = getConnection();
		int result = dao.contextfx();
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		return result;
	}

	/** 회원 목록 조회(아이디, 이름, 성별)
	 * @return member
	 */
	public ArrayList<Member> lookMembers() throws Exception{

		ArrayList<Member> memberList = null;
		Connection conn = getConnection();
		memberList = dao.lookMembers(conn);
		close(conn);
		return memberList;
	}
	
	/** 내 정보 수정(이름, 성별)
	 * @param memberName
	 * @param memberGender
	 * @return
	 */
	public int updateMember(String memberId, String memberName, String memberGender) throws Exception{

		int result = 0;
		Connection conn = getConnection();
		result = dao.updateMember(conn, memberId, memberName, memberGender);
		
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
