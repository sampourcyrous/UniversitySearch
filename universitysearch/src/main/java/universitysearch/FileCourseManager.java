package universitysearch;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class FileCourseManager extends DBManager {
	private static SessionFactory factory;
	
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	/* Method to add a file to the database */
	public Integer linkCourse(int fileID, int courseID, int userID){
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer linkID = null;
		//User user = new User(email, pass, fName, lName, isPr, isAd, emailVer);
		//String addUser = "INSERT INTO `universitysearch`.`users` (`email`, `password`, `first_name`, `last_name`) VALUES ('" + 
		//email + "', '"+ pass + "', '" + fName + "', '" + lName + ")" ; 
        
		try{
	         tx = session.beginTransaction();
	         FileCourse file_course = new FileCourse (fileID, courseID, userID);
	         linkID = (Integer) session.save(file_course); 
	         tx.commit();
		}catch (HibernateException e) {
	    	if (tx!=null)
	        	tx.rollback();
	        e.printStackTrace(); 
	 	}finally {
	        session.close(); 
	    }
		return linkID;
	}
}