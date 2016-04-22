package universitysearch;

public class FileCourse {
	private int id;
	private int fileID, courseID, userID;

	public FileCourse() {
	}
	
	public FileCourse(int fileID, int courseID, int userID) {
		this.fileID = fileID;
		this.courseID = courseID;
		this.userID = userID;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
