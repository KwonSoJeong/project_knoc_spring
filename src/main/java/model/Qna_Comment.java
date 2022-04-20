package model;

import java.util.Date;

public class Qna_Comment {
	private String comment_Id, title, content, refId, writer;
	private Date regDate;
	
	public Qna_Comment() {}
	
	public Qna_Comment(String comment_Id, String title, String content, String refId, Date regDate, String writer) {
		this.comment_Id = comment_Id;
		this.title = title;
		this.content = content;
		this.refId = refId;
		this.regDate = regDate;
		this.writer = writer;
	}

	public String getComment_Id() {
		return comment_Id;
	}

	public void setComment_Id(String comment_Id) {
		this.comment_Id = comment_Id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		return "Qna_Comment [comment_Id=" + comment_Id + ", title=" + title + ", content=" + content + ", refId="
				+ refId + ", writer=" + writer + ", regDate=" + regDate + "]";
	}

	
	
}
