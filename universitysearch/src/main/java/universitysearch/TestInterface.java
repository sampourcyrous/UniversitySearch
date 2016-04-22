package universitysearch;

import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.sql.SQLException;
import static org.junit.Assert.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;
import com.sun.jersey.core.header.FormDataContentDisposition;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class TestInterface {
  //SessionFactory factory = DBManager.getSessionFactory();
    
  @Test
  public void testCourse() {
    Course mockCourse = Mockito.mock(Course.class);
    
    Course courseTest = new Course("Software Engineering", "How to Design Software", "CSC301", 3);
    courseTest.setId(8);
    
    assertEquals(courseTest.getId(), 8);
    assertEquals(courseTest.getCourseName(), "Software Engineering");
    assertEquals(courseTest.getCourseDesc(), "How to Design Software");
    assertEquals(courseTest.getCourseCode(), "CSC301");
    assertEquals(courseTest.getProfID(), 3);
    
    when(mockCourse.getId()).thenReturn(5);
    when(mockCourse.getCourseName()).thenReturn("Capstone Design");
    when(mockCourse.getCourseDesc()).thenReturn("Video Game Design");
    when(mockCourse.getCourseCode()).thenReturn("CSC490");
    when(mockCourse.getProfID()).thenReturn(4);
    
    assertEquals(mockCourse.getId(), 5);
    assertEquals(mockCourse.getCourseName(), "Capstone Design");
    assertEquals(mockCourse.getCourseDesc(), "Video Game Design");
    assertEquals(mockCourse.getCourseCode(), "CSC490");
    assertEquals(mockCourse.getProfID(), 4);
    
    verify(mockCourse, times(1)).getId();
    verify(mockCourse, times(1)).getCourseName();
    verify(mockCourse, times(1)).getCourseDesc();
    verify(mockCourse, times(1)).getCourseCode();
    verify(mockCourse, times(1)).getProfID();
  }

  @Test
  public void testCourseManager() {
    CourseManager mockCM = Mockito.mock(CourseManager.class);
    Course courseTest = new Course();
    
    when(mockCM.addCourse(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(courseTest);
    assertEquals(mockCM.addCourse(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()), courseTest);
    verify(mockCM, times(1)).addCourse(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt());
    
    when(mockCM.getFollowingCourses(Mockito.anyInt())).thenReturn("course");
    assertEquals(mockCM.getFollowingCourses(Mockito.anyInt()), "course");
    verify(mockCM, times(1)).getFollowingCourses(Mockito.anyInt());
    
    when(mockCM.getCoursesForProf(Mockito.anyInt())).thenReturn("course");
    assertEquals(mockCM.getCoursesForProf(Mockito.anyInt()), "course");
    verify(mockCM, times(1)).getCoursesForProf(Mockito.anyInt());
    
    when(mockCM.getJsonResultObj(Mockito.anyList())).thenReturn("test");
    assertEquals(mockCM.getJsonResultObj(Mockito.anyList()), "test");
    verify(mockCM, times(1)).getJsonResultObj(Mockito.anyList());
    
    try {
      when(mockCM.getCourseById(Mockito.anyInt())).thenReturn(courseTest);
      assertEquals(mockCM.getCourseById(Mockito.anyInt()), courseTest);
      verify(mockCM, times(1)).getCourseById(Mockito.anyInt());
      
      when(mockCM.getAllCourses()).thenReturn(null);
      assertEquals(mockCM.getAllCourses(), null);
      verify(mockCM, times(1)).getAllCourses();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /* Not sure if we need to test DBManager
  @Test
  public void testDBManager() {
    DBManager mockDB = Mockito.mock(DBManager.class);
    
    
    when(mockDB.getSessionFactory()).thenReturn(null);
    
    assertEquals(mockDB.getSessionFactory(), null);
  }*/
  
  @Test
  public void testEndPointManager() {
    EndPointManager mockEM = Mockito.mock(EndPointManager.class);
    
    try {
      when(mockEM.getMsg(Mockito.anyString())).thenReturn(null);
      assertEquals(mockEM.getMsg(Mockito.anyString()), null);
      verify(mockEM, times(1)).getMsg(Mockito.anyString());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    User testUser = new User();
    
    when(mockEM.addUser(testUser)).thenReturn(null);
    assertEquals(mockEM.addUser(testUser), null);   
    verify(mockEM, times(1)).addUser(testUser);
    
    
    when(mockEM.addFile((InputStream) Mockito.anyObject(), (FormDataContentDisposition) Mockito.anyObject(), (JSONArray) Mockito.anyObject(), Mockito.anyString(), (HttpServletRequest) Mockito.anyObject(), Mockito.anyInt())).thenReturn(null);
    assertEquals(mockEM.addFile((InputStream) Mockito.anyObject(), (FormDataContentDisposition) Mockito.anyObject(), (JSONArray) Mockito.anyObject(), Mockito.anyString(), (HttpServletRequest) Mockito.anyObject(), Mockito.anyInt()), null);   
    verify(mockEM, times(1)).addFile((InputStream) Mockito.anyObject(), (FormDataContentDisposition) Mockito.anyObject(), (JSONArray) Mockito.anyObject(), Mockito.anyString(), (HttpServletRequest) Mockito.anyObject(), Mockito.anyInt());
    
    when(mockEM.activateUser(Mockito.anyString(), Mockito.anyString())).thenReturn("Your account has been activated, you can now login");
    assertEquals(mockEM.activateUser(Mockito.anyString(), Mockito.anyString()), "Your account has been activated, you can now login");
    verify(mockEM, times(1)).activateUser(Mockito.anyString(), Mockito.anyString());
    
    when(mockEM.signInUser((User) Mockito.anyObject(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.signInUser((User) Mockito.anyObject(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).signInUser((User) Mockito.anyObject(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.signOutUser((HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.signOutUser((HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).signOutUser((HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.search(Mockito.anyString())).thenReturn(null);
    assertEquals(mockEM.search(Mockito.anyString()), null);
    verify(mockEM, times(1)).search(Mockito.anyString());
    
    when(mockEM.search((HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.search((HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).search((HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.search(Mockito.anyString(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.search(Mockito.anyString(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).search(Mockito.anyString(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.addTags(Mockito.anyInt(), (JSONArray) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.addTags(Mockito.anyInt(), (JSONArray) Mockito.anyObject()), null);
    verify(mockEM, times(1)).addTags(Mockito.anyInt(), (JSONArray) Mockito.anyObject());
    
    when(mockEM.getTags(Mockito.anyInt())).thenReturn(null);
    assertEquals(mockEM.getTags(Mockito.anyInt()), null);
    verify(mockEM, times(1)).getTags(Mockito.anyInt());
    
    when(mockEM.deleteFile(Mockito.anyString(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.deleteFile(Mockito.anyString(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).deleteFile(Mockito.anyString(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.addCourse((Course) Mockito.anyObject(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.addCourse((Course) Mockito.anyObject(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).addCourse((Course) Mockito.anyObject(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.deleteCourse(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.deleteCourse(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).deleteCourse(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.getCoursesForProf((HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.getCoursesForProf((HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).getCoursesForProf((HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.followCourses(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.followCourses(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).followCourses(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.unFollowCourses(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.unFollowCourses(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).unFollowCourses(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.approveFile(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.approveFile(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).approveFile(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.isApproved(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.isApproved(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).isApproved(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.getCourseById(Mockito.anyInt())).thenReturn(null);
    assertEquals(mockEM.getCourseById(Mockito.anyInt()), null);
    verify(mockEM, times(1)).getCourseById(Mockito.anyInt());
    
    when(mockEM.getFilesForCourse(Mockito.anyInt())).thenReturn(null);
    assertEquals(mockEM.getFilesForCourse(Mockito.anyInt()), null);
    verify(mockEM, times(1)).getFilesForCourse(Mockito.anyInt());
    
    when(mockEM.getNotifications((HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.getNotifications((HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).getNotifications((HttpServletRequest) Mockito.anyObject());
    
    when(mockEM.getCourses()).thenReturn(null);
    assertEquals(mockEM.getCourses(), null);
    verify(mockEM, times(1)).getCourses();
    
    when(mockEM.removeNotification(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject())).thenReturn(null);
    assertEquals(mockEM.removeNotification(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject()), null);
    verify(mockEM, times(1)).removeNotification(Mockito.anyInt(), (HttpServletRequest) Mockito.anyObject());
  }

  @Test
  public void testFile() {
    File mockFile = Mockito.mock(File.class);
    
    File testFile = new File("file1.pdf", "/test/", "fileDesc", "fileHash", 16, 1, "timeHash", 3);
    testFile.setId(30);
    testFile.setBlurb("testBlurb");
    
    assertEquals(testFile.getId(), 30);
    assertEquals(testFile.getFileName(), "file1.pdf");
    assertEquals(testFile.getFilePath(), "/test/");
    assertEquals(testFile.getFileDesc(), "fileDesc");
    assertEquals(testFile.getFileHash(), "fileHash");
    assertEquals(testFile.getFileSize(), 16);
    assertEquals(testFile.getFileOwner(), 1);
    assertEquals(testFile.getTimeHash(), "timeHash");
    assertEquals(testFile.getBlurb(), "testBlurb");
    assertEquals(testFile.getCourseId(), 3);
    
    when(mockFile.getId()).thenReturn(20);
    when(mockFile.getFileName()).thenReturn("file4.txt");
    when(mockFile.getFilePath()).thenReturn("/test5/");
    when(mockFile.getFileDesc()).thenReturn("file description");
    when(mockFile.getFileHash()).thenReturn("123sad");
    when(mockFile.getFileSize()).thenReturn((long) 18);
    when(mockFile.getFileOwner()).thenReturn(5);
    when(mockFile.getTimeHash()).thenReturn("346sahd");
    when(mockFile.getBlurb()).thenReturn("little blurb of file");
    when(mockFile.getCourseId()).thenReturn(15);
    
    assertEquals(mockFile.getId(), 20);
    assertEquals(mockFile.getFileName(), "file4.txt");
    assertEquals(mockFile.getFilePath(), "/test5/");
    assertEquals(mockFile.getFileDesc(), "file description");
    assertEquals(mockFile.getFileHash(), "123sad");
    assertEquals(mockFile.getFileSize(), (long) 18);
    assertEquals(mockFile.getFileOwner(), 5);
    assertEquals(mockFile.getTimeHash(), "346sahd");
    assertEquals(mockFile.getBlurb(), "little blurb of file");
    assertEquals(mockFile.getCourseId(), 15);
    
    verify(mockFile, times(1)).getId();    
    verify(mockFile, times(1)).getFileName(); 
    verify(mockFile, times(1)).getFilePath(); 
    verify(mockFile, times(1)).getFileDesc(); 
    verify(mockFile, times(1)).getFileHash(); 
    verify(mockFile, times(1)).getFileSize(); 
    verify(mockFile, times(1)).getFileOwner(); 
    verify(mockFile, times(1)).getTimeHash(); 
    verify(mockFile, times(1)).getBlurb(); 
    verify(mockFile, times(1)).getCourseId(); 
  }
  
  @Test
  public void testFileManager() {
    FileManager mockFM = Mockito.mock(FileManager.class);
    try {
      when(mockFM.addFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt(), (JSONArray) Mockito.anyObject())).thenReturn((Integer) 0);
      assertEquals(mockFM.addFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt(), (JSONArray) Mockito.anyObject()), (Integer) 0);
      verify(mockFM, times(1)).addFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt(), (JSONArray) Mockito.anyObject());
      
      when(mockFM.getTags(Mockito.anyInt())).thenReturn(null);
      assertEquals(mockFM.getTags(Mockito.anyInt()), null);
      verify(mockFM, times(1)).getTags(Mockito.anyInt());
      
      when(mockFM.getFileInfo(Mockito.anyString())).thenReturn(null);
      assertEquals(mockFM.getFileInfo(Mockito.anyString()), null);
      verify(mockFM, times(1)).getFileInfo(Mockito.anyString());
      
      when(mockFM.getFilesForCourse(Mockito.anyInt())).thenReturn(null);
      assertEquals(mockFM.getFilesForCourse(Mockito.anyInt()), null);
      verify(mockFM, times(1)).getFilesForCourse(Mockito.anyInt());
      
      when(mockFM.isApproved(Mockito.anyInt())).thenReturn(1);
      assertEquals(mockFM.isApproved(Mockito.anyInt()), 1);
      verify(mockFM, times(1)).isApproved(Mockito.anyInt());
      
      when(mockFM.getNotifications(Mockito.anyInt())).thenReturn("test");
      assertEquals(mockFM.getNotifications(Mockito.anyInt()), "test");
      verify(mockFM, times(1)).getNotifications(Mockito.anyInt());
      
      when(mockFM.removeNotification(Mockito.anyInt(), Mockito.anyInt())).thenReturn("test");
      assertEquals(mockFM.removeNotification(Mockito.anyInt(), Mockito.anyInt()), "test");
      verify(mockFM, times(1)).removeNotification(Mockito.anyInt(), Mockito.anyInt());
      
      File testFile = new File();
      when(mockFM.getJsonResultObj(testFile)).thenReturn("test");
      assertEquals(mockFM.getJsonResultObj(testFile), "test");
      verify(mockFM, times(1)).getJsonResultObj(testFile);
      
      when(mockFM.getJsonResultObj(Mockito.anyList())).thenReturn("test");
      assertEquals(mockFM.getJsonResultObj(Mockito.anyList()), "test");
      verify(mockFM, times(1)).getJsonResultObj(Mockito.anyList());
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
    
  @Test 
  public void TestMasterSearch() {
    MasterSearch mockMR = Mockito.mock(MasterSearch.class);
    
    SearchResult testSR = new SearchResult();
    
    when(mockMR.searchCoursesAndFiles(Mockito.anyString())).thenReturn("test");
    when(mockMR.getJsonResultObj(testSR)).thenReturn("");
  }
  
  @Test
  public void TestSearchResult() {
    SearchResult mockSR = Mockito.mock(SearchResult.class);
    
    File testFile = new File();
    Course testCourse = new Course();
    SearchResult testSR = new SearchResult();
    ArrayList<File> files = new ArrayList<File>();
    ArrayList<Course> courses = new ArrayList<Course>();
    
    files.add(testFile);
    courses.add(testCourse);
    
    testSR.addFile(testFile);
    testSR.addCourse(testCourse);
    
    assertEquals(testSR.getFiles(), files);
    assertEquals(testSR.getCourses(), courses);
    
    testSR.setFiles(null);
    testSR.setCourses(null);
    
    assertEquals(testSR.getFiles(), null);
    assertEquals(testSR.getCourses(), null);
        
    when(mockSR.getFiles()).thenReturn(null);
    when(mockSR.getCourses()).thenReturn(null);
    
    assertEquals(mockSR.getFiles(), null);
    assertEquals(mockSR.getCourses(), null);
    
    verify(mockSR, times(1)).getFiles();
    verify(mockSR, times(1)).getCourses();
  }
  
  @Test
  public void TestTags() {
    Tags mockTag = Mockito.mock(Tags.class);

    Tags testTag = new Tags("tag", 5);
    testTag.setId(20);
    
    assertEquals(testTag.getId(), 20);
    assertEquals(testTag.getText(), "tag");
    assertEquals(testTag.getFileId(), 5);
    
    when(mockTag.getId()).thenReturn(2);
    when(mockTag.getText()).thenReturn("test");
    when(mockTag.getFileId()).thenReturn(4);
    
    assertEquals(mockTag.getId(), 2);
    assertEquals(mockTag.getText(), "test");
    assertEquals(mockTag.getFileId(), 4);
    
    verify(mockTag, times(1)).getId();
    verify(mockTag, times(1)).getText();
    verify(mockTag, times(1)).getFileId();
  }
  
  @Test
  public void testUser() {
    User mockUser = Mockito.mock(User.class);
    
    User testUser = new User("Bob", "Dave", "bobD4", "987654321", "bob.dave@mail.utoronto.ca", "password123");
    testUser.setId(40);
    testUser.setHash("TestHash");
    testUser.setCreationDate("2016-05-16 23:03:02");
    testUser.setIsProf(0);
    testUser.setIsAdmin(0);
    testUser.setActive(0);
    
    assertEquals(testUser.getId(), 40);
    assertEquals(testUser.getFirstName(), "Bob");
    assertEquals(testUser.getLastName(), "Dave");
    assertEquals(testUser.getUtorid(), "bobD4");
    assertEquals(testUser.getStudentNumber(), "987654321");
    assertEquals(testUser.getEmail(), "bob.dave@mail.utoronto.ca");
    assertEquals(testUser.getPassword(), "password123");
    assertEquals(testUser.getIsProf(), 0);
    assertEquals(testUser.getIsAdmin(), 0);
    assertEquals(testUser.getActive(), 0);
    assertEquals(testUser.getHash(), "TestHash");
    assertEquals(testUser.getCreationDate(), "2016-05-16 23:03:02");
    
    when(mockUser.getId()).thenReturn(2);
    when(mockUser.getFirstName()).thenReturn("Alice");
    when(mockUser.getLastName()).thenReturn("Jane");
    when(mockUser.getUtorid()).thenReturn("janeA6");
    when(mockUser.getStudentNumber()).thenReturn("123456789");
    when(mockUser.getEmail()).thenReturn("alice.jane@mail.utoronto.ca");
    when(mockUser.getPassword()).thenReturn("password");
    when(mockUser.getIsProf()).thenReturn(1);
    when(mockUser.getIsAdmin()).thenReturn(0);
    when(mockUser.getActive()).thenReturn(1);
    when(mockUser.getHash()).thenReturn("testhash");
    when(mockUser.getCreationDate()).thenReturn("2016-03-16 15:00:00");
    
    assertEquals(mockUser.getId(), 2);
    assertEquals(mockUser.getFirstName(), "Alice");
    assertEquals(mockUser.getLastName(), "Jane");
    assertEquals(mockUser.getUtorid(), "janeA6");
    assertEquals(mockUser.getStudentNumber(), "123456789");
    assertEquals(mockUser.getEmail(), "alice.jane@mail.utoronto.ca");
    assertEquals(mockUser.getPassword(), "password");
    assertEquals(mockUser.getIsProf(), 1);
    assertEquals(mockUser.getIsAdmin(), 0);
    assertEquals(mockUser.getActive(), 1);
    assertEquals(mockUser.getHash(), "testhash");
    assertEquals(mockUser.getCreationDate(), "2016-03-16 15:00:00");
    
    verify(mockUser, times(1)).getId();
    verify(mockUser, times(1)).getFirstName();
    verify(mockUser, times(1)).getLastName();
    verify(mockUser, times(1)).getUtorid();
    verify(mockUser, times(1)).getStudentNumber();
    verify(mockUser, times(1)).getEmail();
    verify(mockUser, times(1)).getPassword();
    verify(mockUser, times(1)).getIsProf();
    verify(mockUser, times(1)).getIsAdmin();
    verify(mockUser, times(1)).getActive();
    verify(mockUser, times(1)).getHash();
    verify(mockUser, times(1)).getCreationDate();
  }

  @Test
  public void TestUserCourse() {
    UserCourse mockUC = Mockito.mock(UserCourse.class);

    when(mockUC.getId()).thenReturn(2);
    when(mockUC.getUserID()).thenReturn(3);
    when(mockUC.getCourseID()).thenReturn(4);
    
    assertEquals(mockUC.getId(), 2);
    assertEquals(mockUC.getUserID(), 3);
    assertEquals(mockUC.getCourseID(), 4);
    
    verify(mockUC, times(1)).getId();
    verify(mockUC, times(1)).getUserID();
    verify(mockUC, times(1)).getCourseID();
  }
  
  @Test
  public void testUserManager() {
    UserManager mockUM = Mockito.mock(UserManager.class);
    when(mockUM.registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn((Integer) 0);
    assertEquals(mockUM.registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()), (Integer) 0);
    
    mockUM.activateUser(Mockito.anyString(), Mockito.anyString());
    
    when(mockUM.signInUser(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
    assertEquals(mockUM.signInUser(Mockito.anyString(), Mockito.anyString()), null);
    
    verify(mockUM, times(1)).registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    verify(mockUM, times(1)).activateUser(Mockito.anyString(), Mockito.anyString());
    verify(mockUM, times(1)).signInUser(Mockito.anyString(), Mockito.anyString());
  }
}
