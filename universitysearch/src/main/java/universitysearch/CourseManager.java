package universitysearch;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseManager extends DBManager {
	private static SessionFactory factory;
	
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	/* Method to add a course to the database */
	public Course addCourse(String cName, String cDesc, String cCode, int pID){
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer courseId = null;

		Course course = null;

		try{
	         tx = session.beginTransaction();
	         course = new Course(cName, cDesc, cCode, pID);
	         courseId = (Integer) session.save(course);

			 course.setId(courseId);


	         tx.commit();
		}catch (HibernateException e) {
	    	if (tx!=null)
	        	tx.rollback();
	        e.printStackTrace(); 
	 	}finally {
	        session.close(); 
	    }
		return course;
	}

	public Course getCourseById(int id) throws Exception {
		Session session = factory.openSession();
		Transaction tx = null;
		Course course;

		try {
			Criteria criteria = session.createCriteria(Course.class);
			Criterion userValue = Restrictions.eq("id", id);
			criteria.add(userValue);
			course = (Course) criteria.uniqueResult();
			return course;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	public void deleteCourse(int courseId, int profId) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Delete file from db
			Course course = new Course();
			course.setProfID(profId);
			course.setId(courseId);
			session.delete(course);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public String getFollowingCourses(int userId) {
    	Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			// Get courses users is following
			Criteria criteria = session.createCriteria(UserCourse.class);
			Criterion userValue = Restrictions.eq("userID", userId);
			criteria.add(userValue);

			List<UserCourse> userCourseList = criteria.list();

			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			for (UserCourse uc : userCourseList) {
				arrayList.add(uc.getCourseID());
			}

			// Get full course information
			if(!arrayList.isEmpty()) {
				Criteria criteria2 = session.createCriteria(Course.class);
				Criterion courseValue = Restrictions.in("id", arrayList);
				criteria2.add(courseValue);
				List<Course> courseList = criteria2.list();
				
				// Check to see if course is found
				if (courseList.size() > 0) {
					return getJsonResultObj(courseList);
				}
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
    }

	public String getCoursesForProf(int profId) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Get full course information
			Criteria criteria = session.createCriteria(Course.class);
			Criterion profCourses = Restrictions.eq("profID", profId);

			criteria.add(profCourses);

			List<Course> courseList = criteria.list();

			// Check to see if course is found
			if (courseList.size() > 0) {
				return getJsonResultObj(courseList);
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public void followCourse(int courseId, int userId) throws Exception {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Delete file from db
			UserCourse userCourse = new UserCourse();
			userCourse.setCourseID(courseId);
			userCourse.setUserID(userId);
			
			session.save(userCourse);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			session.close();
		}
	}
	
	public void unFollowCourse(int courseId, int userId) throws Exception {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			Query query = session.createQuery("DELETE UserCourse WHERE userID=:userId AND courseID=:courseId");
			query.setInteger("userId", userId);
			query.setInteger("courseId", courseId);
			
			query.executeUpdate();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	public List<Course> getAllCourses() throws Exception {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Course> courses;
		try {
			tx = session.beginTransaction();
			courses = session.createCriteria(Course.class).list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			session.close();
		}
		return courses;
	}
	
    public String getJsonResultObj(List<Course> courses) {
        String res = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            res = mapper.writeValueAsString(courses);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

	
}
