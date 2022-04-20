package model;

public class WishList {
	private int no;
	private String id, class_id;
	
	public WishList() {}
	
	public WishList(int no, String id, String class_id) {
		this.no = no;
		this.id = id;
		this.class_id = class_id;
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

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	@Override
	public String toString() {
		return "{\"no\":\"" + no + "\", \"id\":\"" + id + "\", \"class_id\":\"" + class_id + "\"}";
	}
	
}
