package web.functional;

import org.junit.AfterClass;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import web.MyServer;

public class TestStemMathApplication {

	private static ConfigurableApplicationContext context;
	private static String baseUrl;

	private WebDriver driver;

	@BeforeClass
	public static void startApplication() {
		context = SpringApplication.run(MyServer.class, "--server.port=0");
		baseUrl = "http://127.0.0.1:" + context.getEnvironment().getProperty("local.server.port");
	}

	@AfterClass
	public static void stopApplication() {
		if (context != null) {
			context.close();
		}
	}

	@Before
	public void setupDriver() {
		driver = new HtmlUnitDriver(true);
	}

	@After
	public void tearDownDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testLoginFailureShowsMessageAndStaysOnLoginPage() {
		driver.get(baseUrl + "/login");

		driver.findElement(By.id("username")).sendKeys("wrong");
		driver.findElement(By.id("passwd")).sendKeys("wrong_pass");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"));
		Assert.assertTrue(driver.getPageSource().contains("Incorrect credentials."));
	}

	@Test
	public void testSuccessfulLoginAndMathFlowReachesQ3() {
		driver.get(baseUrl + "/login");

		driver.findElement(By.id("username")).sendKeys("ahsan");
		driver.findElement(By.id("passwd")).sendKeys("ahsan_pass");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q1"));

		driver.findElement(By.id("number1")).sendKeys("2");
		driver.findElement(By.id("number2")).sendKeys("3");
		driver.findElement(By.id("result")).sendKeys("5");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q2"));

		driver.findElement(By.id("number1")).sendKeys("9");
		driver.findElement(By.id("number2")).sendKeys("4");
		driver.findElement(By.id("result")).sendKeys("5");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q3"));
		Assert.assertTrue(driver.getPageSource().contains("Q3"));
	}

	@Test
	public void testQ1BlankNumbersShowValidationMessageWithoutErrorPage() {
		driver.get(baseUrl + "/q1");

		driver.findElement(By.id("result")).sendKeys("5");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		assertOnQuestionPageWithMessage("/q1", "First number is required.");
	}

	@Test
	public void testQ2InvalidNumberShowsValidationMessageWithoutErrorPage() {
		driver.get(baseUrl + "/q2");

		driver.findElement(By.id("number1")).sendKeys("ten");
		driver.findElement(By.id("number2")).sendKeys("4");
		driver.findElement(By.id("result")).sendKeys("6");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		assertOnQuestionPageWithMessage("/q2", "First number must be a valid number.");
	}

	private void assertOnQuestionPageWithMessage(String path, String expectedMessage) {
		Assert.assertTrue(driver.getCurrentUrl().endsWith(path));
		Assert.assertFalse(driver.getTitle().toLowerCase().contains("error"));

		WebElement body = driver.findElement(By.tagName("body"));
		Assert.assertTrue(body.getText().contains(expectedMessage));
	}
}
