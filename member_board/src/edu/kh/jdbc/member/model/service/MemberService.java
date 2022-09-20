package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.vo.Member;

public class MemberService {

	private MemberDAO dao = new MemberDAO();

	/** 회원 목록 조회 서비스
	 * @return
	 * @throws Exception
	 */
	public List<Member> selectAll() throws Exception{

		Connection conn = getConnection();
		List<Member> memberList = dao.selectAll(conn);
		
		close(conn);
		return memberList;
	}

	public int updateMember(Member member) throws Exception{

		int result = 0;
		Connection conn = getConnection();
		
		result = dao.updateMember(conn, member);
		if(result >0) commit(conn);
		else		rollback(conn);
		close(conn);
		return result;
	}

	public int updatePassword(String currentPw, String newPw1, int memberNo) throws Exception {

		int result = 0;
		Connection conn = getConnection();
		result = dao.updatePassword(conn, currentPw, newPw1, memberNo);
		if(result >0) commit(conn);
		else	rollback(conn);
		close(conn);
		return result;
	}

	public int secession(String currentPw, int memberNo) throws Exception {
		
		int result = 0;
		Connection conn = getConnection();
		result = dao.updatePassword(conn, currentPw, memberNo);
		if(result >0) commit(conn);
		else	rollback(conn);
		close(conn);
		return result;
	}


	
}
