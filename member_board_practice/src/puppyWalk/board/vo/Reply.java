package puppyWalk.board.vo;

public class Reply {

	private int boardNo;
	private String boardReply;
	
	public Reply() {
		// TODO Auto-generated constructor stub
	}

	public Reply(int boardNo, String boardReply) {
		super();
		this.boardNo = boardNo;
		this.boardReply = boardReply;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardReply() {
		return boardReply;
	}

	public void setBoardReply(String boardReply) {
		this.boardReply = boardReply;
	}

	@Override
	public String toString() {
		return "Reply [boardNo=" + boardNo + ", boardReply=" + boardReply + "]";
	}
	
	
}
