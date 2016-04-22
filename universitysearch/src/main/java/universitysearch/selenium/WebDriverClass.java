package universitysearch.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverClass {
	public static WebDriver driver = new FirefoxDriver();
	public String baseUrl = "http://localhost:3000/";
	public boolean acceptNextAlert = true;
	
	public WebDriverClass() {
		
	}
}
