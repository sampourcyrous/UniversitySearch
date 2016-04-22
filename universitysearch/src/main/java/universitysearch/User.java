package universitysearch;

public class User {

	private int id;

	private String email, password, firstName, lastName, hash, utorid, studentNumber, creationDate;
	private int isProf, isAdmin, active;

	public User() {
	}

	public User(String fName, String lName, String utorid, String studentNumber, String email, String pass) {
		this.firstName = fName;
		this.lastName = lName;
		this.utorid = utorid;
		this.studentNumber = studentNumber;
		this.email = email;
		this.password = pass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fName) {
		this.firstName = fName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lName) {
		this.lastName = lName;
	}

	public String getUtorid() {
		return utorid;
	}

	public void setUtorid(String utorid) {
		this.utorid = utorid;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public int getIsProf() {
		return isProf;
	}

	public void setIsProf(int IsProf) {
		this.isProf = IsProf;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
}
