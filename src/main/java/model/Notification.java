package model;

import java.util.Date;

public class Notification {
	
	private int no;
	private String noti_Code, noti_Content, readChk;
	private Date noti_Date;
	private String from_Id, to_Id;
	
	public Notification() {}
	
	public Notification(int no, String noti_Code, String noti_Content, String readChk, Date noti_Date, String from_Id,
			String to_Id) {
		this.no = no;
		this.noti_Code = noti_Code;
		this.noti_Content = noti_Content;
		this.readChk = readChk;
		this.noti_Date = noti_Date;
		this.from_Id = from_Id;
		this.to_Id = to_Id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getNoti_Code() {
		return noti_Code;
	}

	public void setNoti_Code(String noti_Code) {
		this.noti_Code = noti_Code;
	}

	public String getNoti_Content() {
		return noti_Content;
	}

	public void setNoti_Content(String noti_Content) {
		this.noti_Content = noti_Content;
	}

	public String getReadChk() {
		return readChk;
	}

	public void setReadChk(String readChk) {
		this.readChk = readChk;
	}

	public Date getNoti_Date() {
		return noti_Date;
	}

	public void setNoti_Date(Date noti_Date) {
		this.noti_Date = noti_Date;
	}

	public String getFrom_Id() {
		return from_Id;
	}

	public void setFrom_Id(String from_Id) {
		this.from_Id = from_Id;
	}

	public String getTo_Id() {
		return to_Id;
	}

	public void setTo_Id(String to_Id) {
		this.to_Id = to_Id;
	}

	@Override
	public String toString() {
		return "{\"no\":\"" + no + "\", \"noti_Code\":\"" + noti_Code + "\", \"noti_Content\":\"" + noti_Content
				+ "\", \"readChk\":\"" + readChk + "\", \"noti_Date\":\"" + noti_Date + "\", \"from_Id\":\"" + from_Id
				+ "\", \"to_Id\":\"" + to_Id + "\"}";
	}
	
	
	

}
