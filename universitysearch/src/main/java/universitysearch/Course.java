package universitysearch;

public class Course {
private int id;
	
	private String courseName, courseDesc, courseCode;
	private int profID;

	public Course() {
	}
	
	public Course(String cName, String cDesc, String cCode, int pID) {
		this.courseName = cName;
		this.courseDesc = cDesc;
		this.courseCode = cCode;
		this.profID = pID;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String cName) {
		this.courseName = cName;
	}

	public String getCourseDesc() {
		return courseDesc;
	}
	public void setCourseDesc(String cDesc) {
		this.courseDesc = cDesc;
	}

	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String cCode) {
		this.courseCode = cCode;
	}

	public int getProfID() {
		return profID;
	}
	public void setProfID(int pID) {
		this.profID = pID;
	}
}
