package universitysearch.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class DeleteFile {
	private WebDriver driver = WebDriverClass.driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Test
	public void testDeleteFile() throws Exception {
		driver.get(baseUrl + "/");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.cssSelector("th.ng-binding")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.cssSelector("th.ng-binding")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.linkText("About Stacks.pdf")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.linkText("About Stacks.pdf")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.xpath("//button[@type='button btn btn-primary']")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("//button[@type='button btn btn-primary']")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (0 == driver.findElements(By.cssSelector("tr.ng-scope > th")).size())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.cssSelector("button.followButton.ng-binding")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if ("Follow".equals(driver.findElement(By.cssSelector("button.followButton.ng-binding")).getText()))
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
