package model;

public class Mentoring_Rating {
	private String mentoring_Id;
	private double rating;
	
	public Mentoring_Rating(String mentoring_Id, double rating) {
		super();
		this.mentoring_Id = mentoring_Id;
		this.rating = rating;
	}
	public Mentoring_Rating() {
		super();
	}

	public String getMentoring_Id() {
		return mentoring_Id;
	}
	public void setMentoring_Id(String mentoring_Id) {
		this.mentoring_Id = mentoring_Id;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		return "{\"mentoring_Id\":\"" + mentoring_Id + "\", \"rating\":\"" + rating + "\"}";
	}
	
	

}
