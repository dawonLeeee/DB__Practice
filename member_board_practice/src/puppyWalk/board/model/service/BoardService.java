package puppyWalk.board.model.service;

import static puppyWalk.common.JDBCTemplete.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import puppyWalk.board.model.dao.BoardDAO;
import puppyWalk.board.vo.Board;
import puppyWalk.member.model.dao.MemberDAO;

public class BoardService {

	private BoardDAO dao = new BoardDAO();



	/** 내가 쓴 글 조회
	 * @param memberId
	 * @return List<Board> memberList
	 * @throws Exception
	 */
	public List<Board> selectMyBoard(String memberId) throws Exception{
		
		List<Board> memberList = new ArrayList<>();
		Connection conn = getConnection();
		memberList = dao.selectMyBoard(conn, memberId);
		
		if(!memberList.isEmpty()) commit(conn);
		else			rollback(conn);
		
		return memberList;
	}
}
