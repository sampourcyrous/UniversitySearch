package universitysearch;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@Path("/API")
public class EndPointManager {

	@GET
	@Path("/test/{param}")
	public Response getMsg(@PathParam("param") String input) throws SQLException {

		// String output = "Jersey say : " + columnName;
		// String tableOption = "read";
		// can me read, write, modify
		// String tableName = "users";
		// can be any table name
		SessionFactory factory = DBManager.getSessionFactory();
		/*
		 * UserManager UM = new UserManager(); UM.setFactory(factory); Integer
		 * empID1 = UM.addUser(input, "test", "test", "test", 0, 0, 0,
		 * "HASH TEST");
		 */
		return Response.status(200).entity(input).build();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addUser(User user) {
		// Set variables to the values from user registration
		String fName = user.getFirstName();
		String lName = user.getLastName();
		String utorid = user.getUtorid();
		String studentNumber = user.getStudentNumber();
		String email = user.getEmail();
		String pass = user.getPassword();

		// Aquire DB connection and add user
		SessionFactory factory = DBManager.getSessionFactory();

		UserManager UM = new UserManager();
		UM.setFactory(factory);
		Integer userID = UM.registerUser(fName, lName, utorid, studentNumber, email, pass);

		return "Your account has been made, please verify it by clicking the activation link that has been send to your email.";
	}

	@POST
	@Path("/fileUpload/{courseId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFile(@FormDataParam("file") InputStream fileInputStream,
							@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
							@FormDataParam("tags") JSONArray tags, @FormDataParam("courseCode") String courseCode,
							@FormDataParam("courseDesc") String courseDesc,
							@Context HttpServletRequest req, @PathParam("courseId") int courseId) {

		JSONObject jsonObject;
		try {
			HttpSession session = req.getSession();
			int userId = (Integer) session.getAttribute("userId");
			FileUpload fileUpload = new FileUpload();
			int id = fileUpload.saveFile(fileInputStream, contentDispositionHeader, userId, courseId, tags, courseCode, courseDesc);
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			return Response.status(200).entity(jsonObject).build();
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(500).entity("An error has occurred. Please try again").build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(501).entity("An error has occurred saving the file. Please try again").build();
		}
	}

	@GET
	@Path("/activate/email/{email}/hash/{hash}")
	@Produces(MediaType.TEXT_PLAIN)
	public String activateUser(@PathParam("email") String email, @PathParam("hash") String hash) {
		// Aquire DB connection and add user
		SessionFactory factory = DBManager.getSessionFactory();

		UserManager UM = new UserManager();
		UM.setFactory(factory);
		UM.activateUser(email, hash);

		return "Your account has been activated, you can now login";
	}

	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signInUser(User user, @Context HttpServletRequest request) {
		String email = user.getEmail();
		String password = user.getPassword();

		// Aquire DB connection and add user
		SessionFactory factory = DBManager.getSessionFactory();

		UserManager UM = new UserManager();
		UM.setFactory(factory);
		User userInfo = UM.signInUser(email, password);

		if(userInfo == null) {
			return Response.status(403).entity("Incorrect username or password. Please check if you have activated your account.").build();

		} else {
			HttpSession jsessid = request.getSession(true);
			jsessid.setAttribute("userId", userInfo.getId());
			jsessid.setAttribute("isProf", userInfo.getIsProf());
			jsessid.setAttribute("loggedIn", true);

            //setting session to expiry in 30 mins
			jsessid.setMaxInactiveInterval(30*60);

			JSONObject jsonObject = null;

			try {
				jsonObject = new JSONObject();
				jsonObject.put("firstName", userInfo.getFirstName());
				jsonObject.put("lastName", userInfo.getLastName());
				jsonObject.put("isProf", userInfo.getIsProf());
				jsonObject.put("email", userInfo.getEmail());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return Response.ok(jsonObject).build();
		}
	}

	@POST
	@Path("/signOut")
	public Response signOutUser(@Context HttpServletRequest request) {
		HttpSession httpSession = request.getSession(false);
		if(httpSession != null){
			httpSession.invalidate();
        }

		return Response.ok().build();
	}

	@GET
	@Path("/search/{searchterm}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("searchterm") String searchTerm) {
		SessionFactory sessionFactory = DBManager.getSessionFactory();

		MasterSearch masterSearch = new MasterSearch();
		masterSearch.setFactory(sessionFactory);

		String jsonResp = masterSearch.searchCoursesAndFiles(searchTerm);

		return Response.status(200).entity(jsonResp).build();
	}

	@GET
	@Path("/following")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();

		CourseManager CM = new CourseManager();
		CM.setFactory(factory);

		String jsonResp = "";
		Integer sessionUserId = (Integer)jsessid.getAttribute("userId");

		// Get courses user is following
		if(sessionUserId != null) {
			jsonResp = CM.getFollowingCourses(sessionUserId);
		}

		return Response.status(200).entity(jsonResp).build();
	}

	@GET
	@Path("/file/{fileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("fileId") String fileId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		// Get file info
		FileManager FM = new FileManager();
		FM.setFactory(factory);
		String jsonResp = FM.getFileInfo(fileId);

		return Response.status(200).entity(jsonResp).build();
	}

	@POST
	@Path("/addTags/{fileId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addTags (@PathParam("fileId") int fileId, @FormDataParam("tags") JSONArray tags) {
		try {
			SessionFactory sessionFactory = DBManager.getSessionFactory();
			FileManager fm = new FileManager();
			fm.setFactory(sessionFactory);
			fm.addTags(tags, fileId);

		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(500).entity("").build();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/getTags/{fileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags(@PathParam("fileId") int fileId) {

		SessionFactory sessionFactory = DBManager.getSessionFactory();
		FileManager fm = new FileManager();
		fm.setFactory(sessionFactory);

		List<Tags> tags = fm.getTags(fileId);
		return Response.ok(tags).build();

	}

	@POST
	@Path("/deleteFile/{fileId}")
	public Response deleteFile(@PathParam("fileId") String fileId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		int isProf = (Integer)jsessid.getAttribute("isProf");
		if(isProf == 1) {
			// Get file info
			FileManager FM = new FileManager();
			FM.setFactory(factory);

			FM.deleteFile(fileId);
			return Response.status(200).build();
		} else {
			return Response.status(401).build();
		}



	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addCourse")
	public Response addCourse(Course course, @Context HttpServletRequest request) {
		String courseCode = course.getCourseCode();
		String courseDesc = course.getCourseDesc();
		String courseName = course.getCourseName();

		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		int isProf = (Integer)jsessid.getAttribute("isProf");

		String response = "You are not authorized to add a course";
		Course courseCreated;
		if(isProf == 1) {
			// Get file info
			CourseManager CM = new CourseManager();
			CM.setFactory(factory);

			Integer sessionUserId = (Integer)jsessid.getAttribute("userId");

			courseCreated = CM.addCourse(courseName, courseDesc, courseCode, sessionUserId);

			response = "Added course successfully";
			return Response.status(200).entity(courseCreated).build();
		}
		return Response.status(500).build();
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deleteCourse/{courseId}")
	public Response deleteCourse(@PathParam("courseId") int courseId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		int isProf = (Integer)jsessid.getAttribute("isProf");

		String response = "You are not authorized to delete this course";

		if(isProf == 1) {
			CourseManager CM = new CourseManager();
			CM.setFactory(factory);

			Integer sessionUserId = (Integer)jsessid.getAttribute("userId");

			CM.deleteCourse(courseId, sessionUserId);

			response = "Deleted course successfully";
		}


		return Response.status(200).entity(response).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/coursesForProf")
	public Response getCoursesForProf(@Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		int isProf = (Integer)jsessid.getAttribute("isProf");

		String response = "";

		if(isProf == 1) {
			CourseManager CM = new CourseManager();
			CM.setFactory(factory);

			Integer sessionUserId = (Integer)jsessid.getAttribute("userId");

			response = CM.getCoursesForProf(sessionUserId);
		}

		return Response.status(200).entity(response).build();
	}
	
	@POST
	@Path("/followCourse/{courseId}")
	public Response followCourses(@PathParam("courseId") int courseId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		Integer sessionUserId = (Integer)jsessid.getAttribute("userId");
		
		CourseManager CM = new CourseManager();
		CM.setFactory(factory);
		
		try {
			CM.followCourse(courseId, sessionUserId);
			return Response.status(200).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/unFollowCourse/{courseId}")
	public Response unFollowCourses(@PathParam("courseId") int courseId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		Integer sessionUserId = (Integer)jsessid.getAttribute("userId");
		
		CourseManager CM = new CourseManager();
		CM.setFactory(factory);
		
		try {
			CM.unFollowCourse(courseId, sessionUserId);
			return Response.status(200).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/approve/{fileId}")
	public Response approveFile(@PathParam("fileId") int fileId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		int isProf = (Integer)jsessid.getAttribute("isProf");
		Integer sessionUserId = (Integer)jsessid.getAttribute("userId");

		if(isProf == 1) {
			FileManager FM = new FileManager();
			FM.setFactory(factory);

			try {
				FM.approveFile(fileId, sessionUserId);
				return Response.status(200).build();
			} catch (Exception e) {
				return Response.status(401).build();
			}
		}

		return Response.status(500).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/isApproved/{fileId}")
	public Response isApproved(@PathParam("fileId") int fileId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		FileManager FM = new FileManager();
		FM.setFactory(factory);

		try {
			int isApproved = FM.isApproved(fileId);
			
			JSONObject jsonObject = null;

			try {
				jsonObject = new JSONObject();
				jsonObject.put("isApproved", isApproved);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return Response.status(200).entity(jsonObject).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/course/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseById(@PathParam("id") int id) {
		SessionFactory factory = DBManager.getSessionFactory();
		CourseManager CM = new CourseManager();
		CM.setFactory(factory);
		Course course;
		try {
			course = CM.getCourseById(id);
			if (course == null) {
				throw new Exception();
			}
			return Response.ok(course).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/filesForCourse/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFilesForCourse(@PathParam("id") int id) {
		SessionFactory factory = DBManager.getSessionFactory();
		FileManager fm = new FileManager();
		fm.setFactory(factory);

		try {
			List<File> files = fm.getFilesForCourse(id);
			return Response.status(200).entity(files).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/notifications")
	public Response getNotifications(@Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		int isProf = (Integer)jsessid.getAttribute("isProf");
		Integer sessionUserId = (Integer)jsessid.getAttribute("userId");

		FileManager FM = new FileManager();
		FM.setFactory(factory);

		try {
			String result = FM.getNotifications(sessionUserId);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/courses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourses() {
		SessionFactory factory = DBManager.getSessionFactory();
		CourseManager cm = new CourseManager();
		cm.setFactory(factory);

		try {
			List<Course> files = cm.getAllCourses();
			return Response.status(200).entity(files).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/removeNotification/{fileId}")
	public Response removeNotification(@PathParam("fileId") int fileId, @Context HttpServletRequest request) {
		SessionFactory factory = DBManager.getSessionFactory();

		HttpSession jsessid = request.getSession();
		Integer sessionUserId = (Integer)jsessid.getAttribute("userId");
		
		FileManager FM = new FileManager();
		FM.setFactory(factory);
		
		try {
			FM.removeNotification(fileId, sessionUserId);
			return Response.status(200).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}

}