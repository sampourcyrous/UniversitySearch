package universitysearch.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class DeleteCSC301 {
	private WebDriver driver = WebDriverClass.driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Test
	public void testDeleteCSC301() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.linkText("Add/Remove a Course")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.linkText("Add/Remove a Course")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.cssSelector("ng-form[name=\"removeCourse\"] > input[name=\"courseCode\"]"))
						.isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.cssSelector("ng-form[name=\"removeCourse\"] > input[name=\"courseCode\"]")).clear();
		driver.findElement(By.cssSelector("ng-form[name=\"removeCourse\"] > input[name=\"courseCode\"]")).sendKeys("");
		driver.findElement(By.cssSelector("ng-form[name=\"removeCourse\"] > input[name=\"courseCode\"]")).clear();
		driver.findElement(By.cssSelector("ng-form[name=\"removeCourse\"] > input[name=\"courseCode\"]"))
				.sendKeys("CSC301");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.cssSelector("span.courses_dropdown.ng-binding")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.cssSelector("span.courses_dropdown.ng-binding")).click();
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
		driver.findElement(By.linkText("Home")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (3 == driver.findElements(By.cssSelector("th")).size())
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
