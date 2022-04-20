package model;

import java.util.Date;

public class Mentoring {
	private String mentoring_Id, mentor_Id, content, intro, title;
	private Date regDate;
	
	public Mentoring(String mentoring_Id, String mentor_Id, String content, String intro, Date regDate, String title) {
		super();
		this.mentoring_Id = mentoring_Id;
		this.mentor_Id = mentor_Id;
		this.content = content;
		this.intro = intro;
		this.regDate = regDate;
		this.title = title;
	}

	public Mentoring() {
		super();
	}

	public String getMentoring_Id() {
		return mentoring_Id;
	}

	public void setMentoring_Id(String mentoring_Id) {
		this.mentoring_Id = mentoring_Id;
	}

	public String getMentor_Id() {
		return mentor_Id;
	}

	public void setMentor_Id(String mentor_Id) {
		this.mentor_Id = mentor_Id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "mentoring [mentoring_Id=" + mentoring_Id + ", mentor_Id=" + mentor_Id + ", content=" + content
				+ ", intro=" + intro + ", regDate=" + regDate + "]";
	}
	
	
	
	
}
