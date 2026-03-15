package sit707_week2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
public class SeleniumOperations {

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void officeworks_registration_page(String url) {

		// Step 1: Locate chrome driver
		System.setProperty("webdriver.chrome.driver", "C:/Users/Levin/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");

		// Step 2: Launch browser
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();

		System.out.println("Driver info: " + driver);

		sleep(2);

		// Step 3: Open Officeworks registration page
		driver.get(url);
		sleep(3);
		
		WebElement firstname = driver.findElement(By.id("firstname"));
		System.out.println("Found element: " + firstname);
		firstname.sendKeys("levin");
		
		WebElement lastname = driver.findElement(By.id("lastname"));
		lastname.sendKeys("joseph");
		
		WebElement email = driver.findElement(By.id("email"));
		email.sendKeys("levinjoseph11@gmail.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("123");
		
		WebElement confirmPassword = driver.findElement(By.name("confirmPassword"));
		confirmPassword.sendKeys("123");
		
		WebElement phone = driver.findElement(By.name("phoneNumber"));
		phone.sendKeys("0468314089");

		/*
		 * Identify button 'Create account' and click
		 */
		WebElement createAccountBtn = driver.findElement(By.xpath("//button[contains(text(),'Create account')]"));
		createAccountBtn.click();
        sleep(3);
		/*
		 * Take screenshot
		 */
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File("officeworks_registration_screenshot.png");
			Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Screenshot saved.");
		} catch (Exception e) {
			System.out.println("Screenshot failed: " + e.getMessage());
		}

		// Wait before closing
		sleep(2);

		// Close browser
		driver.close();
	}
	
	public static void automationexercise_registration_page(String url) {

	    // Chrome driver path
	    System.setProperty("webdriver.chrome.driver","C:/Users/Levin/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");

	    System.out.println("Opening Chrome for Automation Exercise...");
	    WebDriver driver = new ChromeDriver();

	    sleep(2);

	    // Open website
	    driver.get(url);

	    sleep(3);
	    
	    WebElement name = driver.findElement(By.name("name"));
	    name.sendKeys("Levin");

	    
	    WebElement email = driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
	    email.sendKeys("levinjoseph123@gmail.com");
	   
	    WebElement signupBtn = driver.findElement(By.xpath("//button[@data-qa='signup-button']"));
	    signupBtn.click();

	    sleep(3);

	    // Screenshot
	    try {
	        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        File dest = new File("automationexercise_registration_screenshot.png");

	        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

	        System.out.println("Screenshot saved.");
	    } catch (Exception e) {
	        System.out.println("Screenshot failed: " + e.getMessage());
	    }

	    sleep(2);

	    driver.close();
	}
}