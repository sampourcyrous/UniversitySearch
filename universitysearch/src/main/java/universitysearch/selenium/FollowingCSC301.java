package universitysearch.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class FollowingCSC301 {
	private WebDriver driver = WebDriverClass.driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Test
	public void testFollowingCSC301() throws Exception {
		driver.get("http://localhost:3000/");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (1 == driver.findElements(By.cssSelector("th.ng-binding")).size())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (1 == driver.findElements(By.cssSelector("div.ng-binding")).size())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

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
