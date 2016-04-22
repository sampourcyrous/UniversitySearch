package universitysearch.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ViewCSC301File {
	private WebDriver driver = WebDriverClass.driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Test
	public void testViewCSC301File() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.linkText("CSC490")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.linkText("CSC490")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (3 == driver.findElements(By.cssSelector("tr.ng-scope > th")).size())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		assertEquals("About Stacks.pdf", driver.findElement(By.cssSelector("tr.ng-scope > th")).getText());
		assertEquals("CSC490", driver
				.findElement(By.xpath("//div[@id='wrapper']/div[2]/grid-view/div/table/tbody/tr/th[2]")).getText());
		driver.findElement(By.linkText("About Stacks.pdf")).click();
		assertEquals("N", driver.findElement(By.cssSelector("th.ng-binding")).getText());
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (1 == driver.findElements(By.cssSelector("em.ng-binding")).size())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		assertEquals("example", driver.findElement(By.cssSelector("span.ng-binding.ng-scope")).getText());
		assertEquals("About Stacks.pdf", driver.findElement(By.cssSelector("em.ng-binding")).getText());
		driver.findElement(By.xpath("(//button[@type='button btn btn-primary'])[2]")).click();
		assertEquals("You are not authorized to approve this file!",
				driver.findElement(By.xpath("//div[@id='modal_sidebar']/div[3]/div[2]/div")).getText());
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
