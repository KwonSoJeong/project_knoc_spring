package model;

import java.util.Date;

public class Report {
	
	private int no;
	private String id, report_Id;
	private Date regDate;
	private String reason;
	
	public Report() {}

	public Report(int no, String id, String report_Id, Date regDate, String reason) {
		this.no = no;
		this.id = id;
		this.report_Id = report_Id;
		this.regDate = regDate;
		this.reason = reason;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReport_Id() {
		return report_Id;
	}

	public void setReport_Id(String report_Id) {
		this.report_Id = report_Id;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "{\"no\":\"" + no + "\", \"id\":\"" + id + "\", \"report_Id\":\"" + report_Id + "\", \"regDate\":\""
				+ regDate + "\", \"reason\":\"" + reason + "\"}";
	}

	
	


	
	
	

}
