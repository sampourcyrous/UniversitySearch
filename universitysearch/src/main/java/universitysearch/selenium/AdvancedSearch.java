package universitysearch.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AdvancedSearch {
	private WebDriver driver = WebDriverClass.driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Test
	public void testAdvancedSearch() throws Exception {
		driver.findElement(By.linkText("Home")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.linkText("Advanced Search")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.linkText("Advanced Search")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.xpath("(//input[@type='text'])[4]")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("(//input[@type='text'])[4]")).clear();
		driver.findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys("stack OR monkey");
		driver.findElement(By.xpath("//div[@id='wrapper']/div[4]/button")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.cssSelector("tr.ng-scope > th")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		assertEquals("About Stacks.pdf", driver.findElement(By.cssSelector("tr.ng-scope > th")).getText());
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
