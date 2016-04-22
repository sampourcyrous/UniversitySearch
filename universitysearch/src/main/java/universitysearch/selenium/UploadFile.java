package universitysearch.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class UploadFile {
	private WebDriver driver = WebDriverClass.driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Test
	  public void testUploadFile() throws Exception {
		try {
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
	    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    driver.findElement(By.xpath("//input[@type='text']")).click();
	    driver.findElement(By.xpath("//input[@type='text']")).clear();
	    driver.findElement(By.xpath("//input[@type='text']")).sendKeys("CSC301");
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if (driver.findElement(By.cssSelector("span.courses_dropdown.ng-binding")).isDisplayed()) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    driver.findElement(By.cssSelector("span.courses_dropdown.ng-binding")).click();
	    driver.findElement(By.cssSelector("div.host > div.tags")).click();
	    
	    try {
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("example");
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("");
	    
	    try {
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    driver.findElement(By.xpath("//div[3]/input")).click();
	    driver.findElement(By.xpath("//div[3]/input")).clear();
	    driver.findElement(By.xpath("//div[3]/input")).sendKeys("This is an upload test");
	    
	    try {
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    driver.findElement(By.cssSelector("input[type=\"file\"]")).clear();
	    driver.findElement(By.cssSelector("input[type=\"file\"]")).sendKeys("/Users/Sam/Documents/About Stacks.pdf");
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if (1 == driver.findElements(By.cssSelector("div.upload_file_drag_drop")).size()) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    assertEquals("Upload Success!", driver.findElement(By.cssSelector("div > span.ng-scope")).getText());
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
}
