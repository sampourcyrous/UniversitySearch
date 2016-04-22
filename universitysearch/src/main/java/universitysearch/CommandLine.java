package universitysearch;

import org.codehaus.jettison.json.JSONException;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CommandLine {
  public static void main(String[] args) {
    // USAGE1: -F -U userID -C courseID -P coursePath file1 file2 ... fileN
    //          -F means you're uploading files, can be an unlimited amount
    //          -U is the userID that's uploading the files
    //          -P is the coursePath on the webserver
    //              -coursePath must be in form of /path/to/file/ with leading and ending /'s
    // USAGE2: -D -U userID -C courseID -P coursePath directory
    //          -D means you're uploading an entire directory, there can only be one
    FileUpload fileUpload = new FileUpload();
    FileManager fm = new FileManager();
    String coursePath = "";
    boolean foundType = false;
    int userID = -1;
    int courseID = -1;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-U")) {
        userID = Integer.parseInt(args[i+1]);
      }
      if (args[i].equals("-C")) {
        courseID = Integer.parseInt(args[i+1]);
      }
      if (args[i].equals("-P")) {
        coursePath = args[i+1];
      }
    }
    if (args[0].equals("-F") || args[0].equals("-D")) {
      foundType = true;
    }
    if (coursePath.equals("") || userID == -1 || foundType == false || courseID == -1) {
      System.out.println("ERROR: coursePath, userID, courseID, uploadType is empty");
      System.exit(-1);
    }
    // Aquire DB connection
    SessionFactory factory = DBManager.getSessionFactory();
    fm.setFactory(factory);
    if (args[0].equals("-D")) {
      // Checking if directory being uploaded
      File fileDir = new File(args[7]);
      if (fileDir.isDirectory() && args.length == 8) {
        for (final File fileEntry : fileDir.listFiles()) {
          try {
            fileUpload.saveFile(fileEntry, fm, coursePath, userID, courseID);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }      
      } else {
        System.out.println("ERROR: Only one directory allowed at a time");
        System.exit(-1);
      }
    } else if (args[0].equals("-F")) {  
      for (int i = 7; i < args.length; i++) {
        File file = new File(args[i]);
        FileInputStream fis = null;
        try {
          fis = new FileInputStream(file);
          fileUpload.saveFile(file, fm, coursePath, userID, courseID);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }
    factory.close();
  }

}
