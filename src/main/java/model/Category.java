package model;

public class Category {
	private String category_Id, category_Name;

	public Category(String category_Id, String category_Name) {
		super();
		this.category_Id = category_Id;
		this.category_Name = category_Name;
	}

	public Category() {
		super();
	}

	public String getCategory_Id() {
		return category_Id;
	}

	public void setCategory_Id(String category_Id) {
		this.category_Id = category_Id;
	}

	public String getCategory_Name() {
		return category_Name;
	}

	public void setCategory_Name(String category_Name) {
		this.category_Name = category_Name;
	}

	@Override
	public String toString() {
		return "Category [category_Id=" + category_Id + ", category_Name=" + category_Name + "]";
	}
	
	
}
