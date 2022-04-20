package model;

public class WebChat {
	private int no;
	private String groupId;
	private String userId;
	private String message;
	
	public WebChat(String[] msgArr) {
		this.groupId = msgArr[0];
		this.userId = msgArr[1];
		this.message = msgArr[2];
	}
	
	public WebChat() {}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "{\"no\":\"" + no + "\", \"groupId\":\"" + groupId + "\", \"userId\":\"" + userId + "\", \"message\":\""
				+ message + "\"}";
	}
	
	
}
