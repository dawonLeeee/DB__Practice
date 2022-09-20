package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplete.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

public class MemberDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MemberDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("member-query.xml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<Member> selectAll(Connection conn) throws Exception{

		// 결과 저장용 변수 선언
		List<Member> memberList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAll");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String memberId = rs.getString("MEMBER_ID");
				String memberName = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				
				Member member = new Member();
				member.setMemberId(memberId);
				member.setMemberName(memberName);
				member.setMemberGender(memberGender);
				
				memberList.add(member);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		
		return memberList;
	}

	public int updateMember(Connection conn, Member member) throws Exception{

		
		int result = 0; 
		
		try {
			String sql = prop.getProperty("updateMember");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getMemberGender());
			pstmt.setInt(3, member.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updatePassword(Connection conn, String currentPw, String newPw1, int memberNo) throws Exception {

		String sql = prop.getProperty("updatePassword");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPw1);
			pstmt.setInt(2, memberNo);
			pstmt.setString(3, currentPw);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int updatePassword(Connection conn, String currentPw, int memberNo) throws Exception {
		
		String sql = prop.getProperty("secession");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, currentPw);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}
}
