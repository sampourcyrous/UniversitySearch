package universitysearch.selenium;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	  OpenPage.class,
	  IncorrectSignIn.class,
	  CorrectSignIn.class,
	  AddCsc301.class,
	  CheckFollowButton.class,
	  FollowCSC301.class,
	  CheckFollowingCSC301.class,
	  FollowingCSC301.class,
	  UploadFile.class,
	  AdvancedSearch.class,
	  ViewCSC301File.class,
	  DeleteFile.class,
	  DeleteCSC301.class,
	  logout.class
})

public class Selenium_test {

}
