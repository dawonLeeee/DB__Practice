package puppyWalk.member.model.dao;

import static puppyWalk.common.JDBCTemplete.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import puppyWalk.member.vo.Member;

public class MemberDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;

	public MemberDAO() {

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

		} catch (Exception e) {
			e.printStackTrace();
		}
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
			memberList.add(new Member(rs.getString("MEMBER_ID"), rs.getString("MEMBER_NM")));
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
	public int updateMember(Connection conn, String memberId, String memberName) throws Exception{

		String sql = prop.getProperty("updateMember");
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberName);
		pstmt.setString(2, memberId);
		
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
