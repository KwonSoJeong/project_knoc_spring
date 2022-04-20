package model;

import java.util.Date;

public class Study {
	private String study_Id, title, leader_Id, content;
	private Date regDate;
	private int process;
	
	public Study(String study_Id, String title, String leader_Id, String content, Date regDate, int process) {
		super();
		this.study_Id = study_Id;
		this.title = title;
		this.leader_Id = leader_Id;
		this.content = content;
		this.regDate = regDate;
		this.process = process;
	}

	public Study() {
		super();
	}

	public String getStudy_Id() {
		return study_Id;
	}

	public void setStudy_Id(String study_Id) {
		this.study_Id = study_Id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLeader_Id() {
		return leader_Id;
	}

	public void setLeader_Id(String leader_Id) {
		this.leader_Id = leader_Id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	@Override
	public String toString() {
		return "Study [study_Id=" + study_Id + ", title=" + title + ", leader_Id=" + leader_Id + ", content=" + content
				+ ", regDate=" + regDate + ", process=" + process + "]";
	}
	
	
	

}
