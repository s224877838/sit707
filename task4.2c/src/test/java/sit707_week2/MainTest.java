package sit707_week2;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * <h2>A. Decision table: Bunnings login ({@value BunningsLoginPage#LOGIN_URL})</h2>
 * <p>
 * Conditions: <strong>C1</strong> = email/username syntactically valid; <strong>C2</strong> = password non-empty;
 * <strong>C3</strong> = credentials accepted by server.
 * </p>
 * <table border="1" summary="Login decision table">
 * <tr><th>Rule</th><th>C1</th><th>C2</th><th>C3</th><th>Expected outcome</th><th>Automated test</th></tr>
 * <tr><td>R1</td><td>Y</td><td>Y</td><td>Y</td><td>URL moves away from login challenge toward authenticated experience.</td><td>{@link #test_DT_R1_successfulLogin_urlChangesWhenEnvCredentialsSet()} (optional env)</td></tr>
 * <tr><td>R2</td><td>Y</td><td>Y</td><td>N</td><td>Rejected login: URL stays in login/auth flow or error shown; not shopper account home.</td><td>{@link #test_DT_R2_invalidCredentials_urlDoesNotReachShopperAccount()}</td></tr>
 * <tr><td>R3</td><td>N</td><td>Y</td><td>-</td><td>Invalid email format: no authenticated session; URL does not become account home.</td><td>{@link #test_DT_R3_invalidEmailFormat_urlDoesNotBecomeShopperAccount()}</td></tr>
 * <tr><td>R4</td><td>Y</td><td>N</td><td>-</td><td>Empty password: submit blocked or no navigation to account; compare URL before/after.</td><td>{@link #test_DT_R4_emptyPassword_urlUnchangedOrStillLoginContext()}</td></tr>
 * <tr><td>R5</td><td>N</td><td>N</td><td>-</td><td>Both invalid/empty: same class as R3/R4; covered by R3/R4 tests.</td><td>(same helpers)</td></tr>
 * </table>
 * <p>
 * B–C: HTML → Selenium mapping and automation helpers live on {@link BunningsLoginPage}.<br>
 * D: Tests record {@link org.openqa.selenium.WebDriver#getCurrentUrl()} and use {@link org.junit.Assert} on URL
 * equality, inequality, and substrings to decide pass/fail.
 * </p>
 * <p>
 * <strong>Screenshots / why the page “refreshes”:</strong> each test’s {@code @Before} loads the login URL again, so
 * when you run the <em>whole</em> class the browser leaves the error state as soon as the next test starts. To
 * photograph Chrome after a failed login (R2) or validation (R3/R4), either
 * (1) run <strong>only one</strong> test method (right‑click the method → Run As → JUnit Test), and/or
 * (2) set VM argument {@code -Dbunnings.demo.pause.seconds=60} so the driver waits on the final screen before closing.
 * Set back to {@code 0} or omit for normal fast runs.
 * </p>
 *
 * @author Ahsan Habib
 */
public class MainTest {

	private static final String STUDENT_ID = "s224877838";
	private static final String STUDENT_NAME = "Levin Joseph Poovakulath";

	private static final String CHROME_DRIVER_PATH = "C:/Users/Levin/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe";

	/**
	 * Demo only: seconds to keep Chrome on the last screen (errors, invalid email, empty password). Pass
	 * {@code -Dbunnings.demo.pause.seconds=60} in Run → Run Configurations → Arguments → VM arguments. Use 0 for normal runs.
	 */
	private static final int DEMO_PAUSE_SEC = parseDemoPauseSeconds();

	private static WebDriver driver;

	private static int parseDemoPauseSeconds() {
		String p = System.getProperty("bunnings.demo.pause.seconds", "0");
		try {
			int n = Integer.parseInt(p.trim());
			return Math.max(0, Math.min(n, 600));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/** Keeps the page from advancing to the next test so you can screenshot Chrome. */
	private static void pauseForDemoScreenshot() {
		if (DEMO_PAUSE_SEC > 0) {
			System.out.println("bunnings.demo.pause.seconds=" + DEMO_PAUSE_SEC + " — screenshot Chrome now, then wait...");
			sleep(DEMO_PAUSE_SEC);
		}
	}

	private static void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public MainTest() {
		System.out.println("MainTest");
	}

	@BeforeClass
	public static void startBrowser() {
		String path = System.getProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
		System.setProperty("webdriver.chrome.driver", path);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void stopBrowser() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	@Before
	public void setup() {
		System.out.println("Before...");
		driver.get(BunningsLoginPage.LOGIN_URL);
		sleep(2);
		BunningsLoginPage.dismissCookieBannerIfPresent(driver);
		BunningsLoginPage.switchToDefault(driver);
	}

	@Test
	public void testStudentIdentity() {
		Assert.assertNotNull("Student ID is null", STUDENT_ID);
		Assert.assertEquals("Student ID must match submission", "s224877838", STUDENT_ID);
		System.out.println("PASS - Student ID: " + STUDENT_ID);
	}

	@Test
	public void testStudentName() {
		Assert.assertNotNull("Student name is null", STUDENT_NAME);
		Assert.assertEquals("Student name must match submission", "Levin Joseph Poovakulath", STUDENT_NAME);
		System.out.println("PASS - Student Name: " + STUDENT_NAME);
	}

	/**
	 * D: {@link Assert#assertTrue(String, boolean)} — after load, URL is a Bunnings host and login path is present
	 * before any submit (inspect address bar vs. {@code driver.getCurrentUrl()}).
	 */
	@Test
	public void test_DT_R0_pageLoad_urlIsBunningsLoginEntry() {
		String url = BunningsLoginPage.currentUrl(driver);
		String u = url.toLowerCase();
		Assert.assertTrue("URL should reference bunnings.com.au (inspect address bar vs getCurrentUrl)",
				u.contains("bunnings.com.au"));
		Assert.assertTrue("Initial navigation should stay on login path or redirect into an auth URL",
				u.contains("/login") || u.contains("auth") || u.contains("signin"));
	}

	/**
	 * Scaffold {@code assertTrue(false)} replaced: D uses URL + form — still on login host and password field
	 * visible in DOM context.
	 */
	@Test
	public void testAssertTrue() {
		System.out.println("testAssertTrue...");
		String url = BunningsLoginPage.currentUrl(driver);
		Assert.assertTrue("Still on a Bunnings login-related URL", url.toLowerCase().contains("bunnings"));
		BunningsLoginPage.ensureLoginFormVisible(driver);
		Assert.assertTrue("Password field should be visible on the login page",
				BunningsLoginPage.findPasswordField(driver).isDisplayed());
	}

	/**
	 * Scaffold {@code assertFalse(true)} replaced: D — after failed submit, assert URL is not shopper account and
	 * assertFalse on “Sign out” (post-login chrome).
	 */
	@Test
	public void testAssertFalse() {
		System.out.println("testAssertFalse...");
		String urlBefore = BunningsLoginPage.currentUrl(driver);
		BunningsLoginPage.fillLogin(driver, "student.test.invalid@example.com", "DefinitelyWrongPassword!123");
		BunningsLoginPage.submit(driver);
		BunningsLoginPage.waitForUrlChangeOrAlert(driver, urlBefore, 25);
		sleep(2);
		String urlAfter = BunningsLoginPage.currentUrl(driver);
		Assert.assertFalse("URL after invalid login must not be the shopper my-account area",
				BunningsLoginPage.urlLooksLikeAuthenticatedShopperArea(urlAfter));
		Assert.assertFalse("Invalid credentials must not show Sign out",
				BunningsLoginPage.isSignOutVisible(driver));
		pauseForDemoScreenshot();
	}

	/**
	 * R2: Invalid password — D: URL either unchanged, or still auth/login-like, or error visible; never account home.
	 */
	@Test
	public void test_DT_R2_invalidCredentials_urlDoesNotReachShopperAccount() {
		String urlBefore = BunningsLoginPage.currentUrl(driver);
		BunningsLoginPage.fillLogin(driver, "automation.student@example.com", "WrongPassword!999");
		BunningsLoginPage.submit(driver);
		BunningsLoginPage.waitForUrlChangeOrAlert(driver, urlBefore, 25);
		sleep(2);
		String urlAfter = BunningsLoginPage.currentUrl(driver);
		Assert.assertFalse("Must not navigate to authenticated shopper account URL",
				BunningsLoginPage.urlLooksLikeAuthenticatedShopperArea(urlAfter));
		Assert.assertTrue(
				"After rejected password, URL should stay in login/auth context, or show error. before="
						+ urlBefore + " after=" + urlAfter,
				urlAfter.equals(urlBefore) || BunningsLoginPage.urlIndicatesLoginOrAuthContext(urlAfter)
						|| BunningsLoginPage.isErrorOrAlertVisible(driver));
		pauseForDemoScreenshot();
	}

	/**
	 * R4: Empty password — D: {@link Assert#assertEquals(Object, Object)} when HTML5 blocks navigation (URL
	 * unchanged), else assert URL still login/auth.
	 */
	@Test
	public void test_DT_R4_emptyPassword_urlUnchangedOrStillLoginContext() {
		String urlBefore = BunningsLoginPage.currentUrl(driver);
		BunningsLoginPage.ensureLoginFormVisible(driver);
		BunningsLoginPage.findEmailOrUsernameField(driver).clear();
		BunningsLoginPage.findEmailOrUsernameField(driver).sendKeys("automation.student@example.com");
		BunningsLoginPage.findPasswordField(driver).clear();
		try {
			BunningsLoginPage.submit(driver);
		} catch (Exception ignored) {
			// HTML5 may block submit
		}
		sleep(2);
		String urlAfter = BunningsLoginPage.currentUrl(driver);
		if (urlAfter.equals(urlBefore)) {
			Assert.assertEquals("With empty password, many browsers keep the URL identical (no round-trip)",
					urlBefore, urlAfter);
		} else {
			Assert.assertFalse("Empty password must not navigate to shopper account home",
					BunningsLoginPage.urlLooksLikeAuthenticatedShopperArea(urlAfter));
			Assert.assertTrue("URL after empty-password attempt should remain login/auth related: " + urlAfter,
					BunningsLoginPage.urlIndicatesLoginOrAuthContext(urlAfter)
							|| urlAfter.toLowerCase().contains("bunnings.com.au/login"));
		}
		pauseForDemoScreenshot();
	}

	/** R3: Malformed email — D: compare before/after; must not become shopper account URL. */
	@Test
	public void test_DT_R3_invalidEmailFormat_urlDoesNotBecomeShopperAccount() {
		String urlBefore = BunningsLoginPage.currentUrl(driver);
		BunningsLoginPage.fillLogin(driver, "not-an-email", "SomePassword1!");
		BunningsLoginPage.submit(driver);
		BunningsLoginPage.waitForUrlChangeOrAlert(driver, urlBefore, 15);
		sleep(1);
		String urlAfter = BunningsLoginPage.currentUrl(driver);
		Assert.assertFalse("Malformed email must not land on shopper account URL",
				BunningsLoginPage.urlLooksLikeAuthenticatedShopperArea(urlAfter));
		Assert.assertFalse("Malformed email must not authenticate (Sign out visible)",
				BunningsLoginPage.isSignOutVisible(driver));
		Assert.assertTrue(
				"URL after malformed email should remain in login/auth or revert to login host. before=" + urlBefore
						+ " after=" + urlAfter,
				BunningsLoginPage.urlIndicatesLoginOrAuthContext(urlAfter) || urlAfter.equals(urlBefore)
						|| urlAfter.toLowerCase().contains("bunnings.com.au/login"));
		pauseForDemoScreenshot();
	}

	/**
	 * R1 (optional): set {@code BUNNINGS_TEST_EMAIL} and {@code BUNNINGS_TEST_PASSWORD} in the environment.
	 * D: {@link Assert#assertNotEquals(Object, Object)} — successful login should change {@code getCurrentUrl()}
	 * away from the starting login URL.
	 */
	@Test
	public void test_DT_R1_successfulLogin_urlChangesWhenEnvCredentialsSet() {
		String email = System.getenv("BUNNINGS_TEST_EMAIL");
		String password = System.getenv("BUNNINGS_TEST_PASSWORD");
		Assume.assumeTrue("Set BUNNINGS_TEST_EMAIL and BUNNINGS_TEST_PASSWORD to run the R1 success-path test",
				email != null && !email.isEmpty() && password != null && !password.isEmpty());

		String urlBefore = BunningsLoginPage.currentUrl(driver);
		BunningsLoginPage.fillLogin(driver, email, password);
		BunningsLoginPage.submit(driver);
		BunningsLoginPage.waitForUrlChangeOrAlert(driver, urlBefore, 40);
		sleep(3);
		String urlAfter = BunningsLoginPage.currentUrl(driver);
		Assert.assertNotEquals("Successful login should change the browser URL (inspect redirect in DevTools)",
				urlBefore, urlAfter);
	}
}
