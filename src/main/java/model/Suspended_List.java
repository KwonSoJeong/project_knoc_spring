package model;

import java.util.Date;

public class Suspended_List {
	
	private String id;
	private Date regDate, dueDate;
	private int accCnt;
	private String status;
	
	public Suspended_List() {}

	public Suspended_List(String id, Date regDate, Date dueDate, int accCnt, String status) {
		this.id = id;
		this.regDate = regDate;
		this.dueDate = dueDate;
		this.accCnt = accCnt;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getAccCnt() {
		return accCnt;
	}

	public void setAccCnt(int accCnt) {
		this.accCnt = accCnt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"regDate\":\"" + regDate + "\", \"dueDate\":\"" + dueDate + "\", \"accCnt\":\""
				+ accCnt + "\", \"status\":\"" + status + "\"}";
	}
	
	

}
