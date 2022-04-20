package model;

public class Member_Study_Info {
	private String id, member_study_id;
	private int type, no;
	
	public Member_Study_Info() {}

	public Member_Study_Info(String id, String member_study_id, int type, int no) {
		this.id = id;
		this.member_study_id = member_study_id;
		this.type = type;
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMember_study_id() {
		return member_study_id;
	}

	public void setMember_study_id(String member_study_id) {
		this.member_study_id = member_study_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	@Override
	public String toString() {
		return "Member_Study_Info [id=" + id + ", member_study_id=" + member_study_id + ", no=" + no + ", type=" + type + "]";
	}
	
}
