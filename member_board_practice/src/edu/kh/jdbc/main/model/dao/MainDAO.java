package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplete.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplete;
import edu.kh.jdbc.member.vo.Member;

// DAO(Data Access Object) : DB연결용 객체
public class MainDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;

	public MainDAO() {

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int idDupCheck(Connection conn, String memberId) throws Exception {
		// 1. 결과 저장용 변수
		int result = 0;

		try {
			// 2. sql 얻어오기
			String sql = prop.getProperty("idDupCheck");

			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// 4. placeholder에 알맞은 값 셋팅
			pstmt.setString(1, memberId);

			// 5. SQL 수행 후 결과 반환받기
			rs = pstmt.executeQuery();

			// 6. 조회 결과 반환받기
			// 1행 조회->if // 여러 행 조회->while
			if (rs.next()) {
				// result = rs.getInt("COUNT(*)"); // 이렇게 해도 되고(개발 편의성 고려)
				result = rs.getInt(1); // 컬럼 순서 // 이렇게 해도 됨
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return result;
	}

	public int signUp(Connection conn, Member member) throws Exception {

		int result = 0;

		try {

			String sql = prop.getProperty("signUp");
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/** 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {

		// 1. 결과 저장용 변수 선언
		Member loginMember = null;
		try {
			// 2. SQL 얻어오기
			String sql = prop.getProperty("login");
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				loginMember = new Member();
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberId(memberId);
				loginMember.setMemberName(rs.getString("MEMBER_NM"));
				loginMember.setMemberGender(rs.getString("MEMBER_GENDER"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DATE"));	
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return loginMember;
	}

	public int contextfx() throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("contextfx");
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}



	/** 회원 목록 조회(아이디, 이름, 성별)
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Member> lookMembers(Connection conn) throws Exception {
		
		ArrayList<Member> memberList = new ArrayList<>();
		String sql = prop.getProperty("lookMembers");
		stmt = conn.createStatement();
		
		rs = stmt.executeQuery(sql);
		while(rs.next()) {
			memberList.add(new Member(rs.getString("MEMBER_ID"), rs.getString("MEMBER_NM"), rs.getString("MEMBER_GENDER")));
		}
		
		close(rs);
		close(stmt);
		
		
		return memberList;
	}

	/** 내 정보 수정(이름, 성별)
	 * @param conn
	 * @param memberName
	 * @param memberGender
	 * @return
	 */
	public int updateMember(Connection conn, String memberId, String memberName, String memberGender) throws Exception{

		String sql = prop.getProperty("updateMember");
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberName);
		pstmt.setString(2, memberGender);
		pstmt.setString(3, memberId);
		
		int result = pstmt.executeUpdate();
		
		close(pstmt);
		
		return result;
	}
	
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

	public int updatePw(Connection conn, String memberId, String newPw) throws Exception{

		String sql = prop.getProperty("updatePw");
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, newPw);
		pstmt.setString(2, memberId);
		int result = pstmt.executeUpdate();
		
		close(pstmt);
		
		return result;
	}
}
