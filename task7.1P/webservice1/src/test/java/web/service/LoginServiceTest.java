package web.service;

import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginServiceTest {

	private static final String DEFAULT_LOGIN_PAGE = Paths
			.get(System.getProperty("user.dir"), "..", "pages", "login.html")
			.toUri()
			.toString();

	private WebDriver driver;

	@Before
	public void setupDriver() {
		String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
		Assume.assumeTrue("Set -Dwebdriver.chrome.driver to run Selenium tests.",
				chromeDriverPath != null && !chromeDriverPath.trim().isEmpty());
		driver = new ChromeDriver();
	}

	@After
	public void tearDownDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	private String getLoginPageUrl() {
		return System.getProperty("login.page.url", DEFAULT_LOGIN_PAGE);
	}

	private void submitLoginForm(String username, String password, String dob) {
		driver.navigate().to(getLoginPageUrl());

		WebElement ele = driver.findElement(By.id("username"));
		ele.clear();
		ele.sendKeys(username);

		ele = driver.findElement(By.id("passwd"));
		ele.clear();
		ele.sendKeys(password);

		ele = driver.findElement(By.id("dob"));
		ele.clear();
		ele.sendKeys(dob);

		ele = driver.findElement(By.cssSelector("[type=submit]"));
		ele.submit();
	}

	@Test
	public void testLoginSuccess() {
		submitLoginForm("ahsan", "ahsan_pass", "1990-01-01");
		String title = driver.getTitle();
		Assert.assertEquals("success", title);
	}

	@Test
	public void testLoginFailWithInvalidDob() {
		submitLoginForm("ahsan", "ahsan_pass", "1990-01-02");
		String title = driver.getTitle();
		Assert.assertEquals("fail", title);
	}

	@Test
	public void testLoginFailWithInvalidPassword() {
		submitLoginForm("ahsan", "wrong_pass", "1990-01-01");
		String title = driver.getTitle();
		Assert.assertEquals("fail", title);
	}

	@Test
	public void testLoginFailWithEmptyUsername() {
		submitLoginForm("", "ahsan_pass", "1990-01-01");
		String title = driver.getTitle();
		Assert.assertEquals("fail", title);
	}
}
