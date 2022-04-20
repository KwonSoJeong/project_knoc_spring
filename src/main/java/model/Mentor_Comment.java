package model;

import java.util.Date;

public class Mentor_Comment {
	private String comment_Id;
	private String content , refId, writer;
	private int  refLevel, refStep;
	private Date regDate;
	
	public Mentor_Comment() {}
	
	public Mentor_Comment(String comment_Id, String content, String refId, int refLevel, int refStep, Date regDate, String writer) {
		this.comment_Id = comment_Id;
		this.content = content;
		this.refId = refId;
		this.refLevel = refLevel;
		this.refStep = refStep;
		this.regDate = regDate;
		this.writer = writer;
	}

	public String getComment_Id() {
		return comment_Id;
	}

	public void setComment_Id(String comment_Id) {
		this.comment_Id = comment_Id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public int getRefLevel() {
		return refLevel;
	}

	public void setRefLevel(int refLevel) {
		this.refLevel = refLevel;
	}

	public int getRefStep() {
		return refStep;
	}

	public void setRefStep(int refStep) {
		this.refStep = refStep;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "Mentor_Comment [comment_Id=" + comment_Id + ", content=" + content + ", refId=" + refId + ", writer="
				+ writer + ", refLevel=" + refLevel + ", refStep=" + refStep + ", regDate=" + regDate + "]";
	}

	
	
}
