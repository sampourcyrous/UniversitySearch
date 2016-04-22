package universitysearch;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.hibernate.*;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserManager extends DBManager {
	private static SessionFactory factory;

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * Add user to database
	 * 
	 * @param fName First Name
	 * @param lName Last Name
	 * @param utorid
	 * @param studentNumber
	 * @param email
	 * @param pass
	 */
	public Integer registerUser(String fName, String lName, String utorid, String studentNumber, String email,
			String pass) {
		// Generate user object from user input
		User user = new User(fName, lName, utorid, studentNumber, email, generateHash(pass));

		// Generate email hash for verification link
		String hash = generateHash();

		user.setHash(hash);
		Integer userID = addUser(user);

		ClientResponse x = sendEmailActivationLink(email, hash);
		
		return userID;
	}

	/**
	 * Generate 32 character hash between 1-1000 This hash is used in
	 * verification link for user registration
	 * 
	 * @return random hash
	 */
	private String generateHash() {
		String random = Integer.toString((int) (Math.random() * 1000));

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(random.getBytes(Charset.forName("UTF-8")), 0, random.length());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		return new BigInteger(1, md.digest()).toString(16);
	}

	/**
	 * Generate 32 character hash between 1-1000 This hash is used in
	 * verification link for user registration
	 * 
	 * @return random hash
	 */
	private String generateHash(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes(Charset.forName("UTF-8")), 0, str.length());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		return new BigInteger(1, md.digest()).toString(16);
	}

	/**
	 * Send email to user in order to activate account
	 * 
	 * @param email
	 * @param hash
	 * @return
	 */
	private ClientResponse sendEmailActivationLink(String email, String hash) {
		String message = "";
		try {
			message = getEmailMessage(email, hash);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", "key-a49503cc806520f40a0d7fda93f0db02"));
		WebResource webResource = client.resource(
				"https://api.mailgun.net/v3/sandboxd510e265a8d14ebe98f08606a1f19327.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Chocolate Thunder <mailgun@sandboxd510e265a8d14ebe98f08606a1f19327.mailgun.org>");
		formData.add("to", email);
		formData.add("subject", "Activate Your Account");
		formData.add("text", message);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}

	/**
	 * Generate email message with user activation link
	 * 
	 * @param email users' email
	 * @param hash hash verification code
	 * @return email message to send to user
	 * @throws UnsupportedEncodingException
	 */
	private String getEmailMessage(String email, String hash) throws UnsupportedEncodingException {
		StringBuilder msg = new StringBuilder();

		msg.append("Thanks for signing up!");
		msg.append("\n");
		msg.append("Your account has been created, you can login after you have activated your account by pressing the url below.");

		msg.append("\n");
		msg.append("\n");

		msg.append("------------------------");
		msg.append("\n");
		msg.append("Username: " + email);
		msg.append("\n");
		msg.append("------------------------");

		msg.append("\n");
		msg.append("\n");

		msg.append("Please click this link to activate your account:");
		msg.append("\n");
		msg.append(System.getenv("OPENSHIFT_GEAR_DNS") + "/#/activate/email/" + URLEncoder.encode(email, "UTF-8") + "/hash/" + hash);

		return msg.toString();
	}

	/**
	 * Set the active to 1 in database
	 * 
	 * @param email
	 * @param hash
	 */
	public void activateUser(String email, String hash) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);

			Criterion emailValue = Restrictions.eq("email", email);
			Criterion hashValue = Restrictions.eq("hash", hash);
			Criterion activeValue = Restrictions.eq("active", 0);

			// Create object of Conjunction
			Conjunction objConjunction = Restrictions.conjunction();

			// Add multiple condition separated by AND clause within brackets.
			objConjunction.add(emailValue);
			objConjunction.add(hashValue);
			objConjunction.add(activeValue);

			criteria.add(objConjunction);

			List<?> userList = criteria.list();

			// Check to see if user is found
			if (userList.size() > 0) {
				User user = (User) userList.get(0);
				user.setActive(1);
				session.update(user);
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * Add user to database
	 * 
	 * @param user object based on user input
	 * @param hash used to generate user validation link
	 * @return user id of newly added user
	 */
	private Integer addUser(User user) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userID = null;

		try {
			tx = session.beginTransaction();
			userID = (Integer) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return userID;
	}

	public User signInUser(String username, String password) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);

			Criterion emailValue = Restrictions.eq("email", username);
			Criterion passwordValue = Restrictions.eq("password", password);
			Criterion activeValue = Restrictions.eq("active", 1);

			// Create object of Conjunction
			Conjunction objConjunction = Restrictions.conjunction();

			// Add multiple condition separated by AND clause within brackets.
			objConjunction.add(emailValue);
			objConjunction.add(passwordValue);
			objConjunction.add(activeValue);

			criteria.add(objConjunction);

			List<?> userList = criteria.list();

			// Check to see if user is found
			if (userList.size() > 0) {
				User user = (User) userList.get(0);
				return user;
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
	
    public void removeUser(int userID) {
      //Delete File from database based on entered ID
      Session session = factory.openSession();
      Transaction tx = null;
      try{
          //SomeEntity ent = session.get(SomeEntity.class, '1234');
          //session.delete(ent);
          tx = session.beginTransaction();
          User user = new User();
          user.setId(userID);
          session.delete(user); 
          tx.commit();
      }catch (HibernateException e) {
          if (tx!=null)
              tx.rollback();
          e.printStackTrace(); 
      }finally {
          session.close(); 
      }
  }
  
  public void modifyUser(User user) {
    // Set elements to null that you do not want updated, or for long/ints set to -1
    // userID is required
    Session session = factory.openSession();
    Transaction tx = null;
    try{
        tx = session.beginTransaction();
        User userUp = (User) session.load(User.class, user.getId());
        // This point file is loaded from DB
        
        if (user.getFirstName() != null) {
          userUp.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
          userUp.setLastName(user.getLastName());
        }
        if (user.getUtorid() != null) {
          userUp.setUtorid(user.getUtorid());
        }
        if (user.getStudentNumber() != null) {
          userUp.setStudentNumber(user.getStudentNumber());
        }
        if (user.getEmail() != null) {
          userUp.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
          userUp.setPassword(user.getPassword());
        }
        
        session.update(userUp); 
        tx.commit();
    }catch (HibernateException e) {
        if (tx!=null)
            tx.rollback();
        e.printStackTrace(); 
    }finally {
        session.close(); 
    }
}
}
