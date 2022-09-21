package puppyWalk.board.vo;

public class Board {
	
	private int boardNo;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private int boardContentViews;
	private String enrollDate;
	private String secession;
	
	public Board() {
		// TODO Auto-generated constructor stub
	}

	public Board(int boardNo, String memberId, String boardTitle, String boardContent, int boardContentViews,
			String enrollDate, String secession) {
		super();
		this.boardNo = boardNo;
		this.memberId = memberId;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardContentViews = boardContentViews;
		this.enrollDate = enrollDate;
		this.secession = secession;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public int getBoardContentViews() {
		return boardContentViews;
	}

	public void setBoardContentViews(int boardContentViews) {
		this.boardContentViews = boardContentViews;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getSecession() {
		return secession;
	}

	public void setSecession(String secession) {
		this.secession = secession;
	}
	
	 
}
