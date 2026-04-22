package sit707_week2;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <h2>B. Login page source → Selenium locator map (DevTools / View Source)</h2>
 * <p>
 * Open {@link #LOGIN_URL}, use <strong>Inspect</strong> on each control, then map the observed HTML to Selenium APIs:
 * </p>
 * <table border="1" summary="HTML elements and Selenium locators">
 * <caption>Typical Bunnings login / embedded identity markup</caption>
 * <tr><th>UI element</th><th>What to look for in source</th><th>Selenium API (preferred → fallback)</th></tr>
 * <tr><td>Email / username</td><td>{@code &lt;input type="email"&gt;}, {@code name="username"}, {@code autocomplete="username"}, or {@code id} containing {@code email}</td><td>{@link org.openqa.selenium.By#cssSelector(String) By.cssSelector}({@code "input[type='email']"}) then {@code By.name("username")}; {@link org.openqa.selenium.By#xpath(String) By.xpath} for dynamic {@code id}</td></tr>
 * <tr><td>Password</td><td>{@code &lt;input type="password"&gt;}, often {@code autocomplete="current-password"}</td><td>{@code By.cssSelector("input[type='password']")}, {@code By.cssSelector("input[autocomplete='current-password']")}</td></tr>
 * <tr><td>Submit</td><td>{@code &lt;button type="submit"&gt;} or button text “Sign in” / “Log in”</td><td>{@code By.cssSelector("button[type='submit']")}, {@link org.openqa.selenium.By#xpath(String) By.xpath} with {@code contains(.,'Sign in')}</td></tr>
 * <tr><td>Embedded login</td><td>{@code &lt;iframe src="...auth...login..."&gt;} wrapping the form</td><td>{@link org.openqa.selenium.WebDriver.TargetLocator#frame(org.openqa.selenium.WebElement) driver.switchTo().frame(iframe)} then locate fields inside the frame</td></tr>
 * <tr><td>Error message</td><td>{@code role="alert"}, or nodes whose {@code class} contains {@code error}</td><td>{@code By.cssSelector("[role='alert']")}, XPath on {@code class}</td></tr>
 * </table>
 * <p>
 * Fallback lists below mirror the order you would try while inspecting: semantic {@code type} / {@code name} first, then text XPath.
 * </p>
 */
final class BunningsLoginPage {

	static final String LOGIN_URL = "https://www.bunnings.com.au/login";

	private BunningsLoginPage() {
	}

	static void dismissCookieBannerIfPresent(WebDriver driver) {
		List<By> acceptButtons = Arrays.asList(
				By.xpath("//button[contains(translate(.,'ACCEPT','accept'),'accept')]"),
				By.xpath("//button[contains(.,'Accept all')]"),
				By.xpath("//button[contains(.,'I accept')]"));
		for (By by : acceptButtons) {
			try {
				List<WebElement> els = driver.findElements(by);
				for (WebElement el : els) {
					if (el.isDisplayed()) {
						el.click();
						return;
					}
				}
			} catch (Exception ignored) {
				// try next pattern
			}
		}
	}

	static void switchToDefault(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	/**
	 * Tries each iframe until a password field is found (inspection: login is often embedded).
	 */
	static void switchToFrameContainingPasswordField(WebDriver driver) {
		switchToDefault(driver);
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for (WebElement frame : frames) {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(frame);
			try {
				findPasswordField(driver);
				return;
			} catch (TimeoutException ignored) {
				// try next frame
			}
		}
		switchToDefault(driver);
	}

	static WebElement waitForFirstDisplayed(WebDriver driver, int seconds, List<By> locators) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		TimeoutException last = null;
		for (By by : locators) {
			try {
				return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			} catch (TimeoutException e) {
				last = e;
			}
		}
		if (last != null) {
			throw last;
		}
		throw new IllegalStateException("No locators supplied");
	}

	static WebElement findEmailOrUsernameField(WebDriver driver) {
		List<By> locators = Arrays.asList(
				By.cssSelector("input[type='email']"),
				By.cssSelector("input[name='username']"),
				By.cssSelector("input[autocomplete='username']"),
				By.xpath("//input[contains(@id,'email') or contains(@name,'email') or contains(@id,'Email')]"));
		return waitForFirstDisplayed(driver, 20, locators);
	}

	static WebElement findPasswordField(WebDriver driver) {
		List<By> locators = Arrays.asList(
				By.cssSelector("input[type='password']"),
				By.cssSelector("input[name='password']"),
				By.cssSelector("input[autocomplete='current-password']"));
		return waitForFirstDisplayed(driver, 10, locators);
	}

	static WebElement findSubmitButton(WebDriver driver) {
		List<By> locators = Arrays.asList(
				By.cssSelector("button[type='submit']"),
				By.xpath("//button[contains(.,'Sign in') or contains(.,'Log in') or contains(.,'Login')]"),
				By.xpath("//input[@type='submit']"));
		return waitForFirstDisplayed(driver, 10, locators);
	}

	static boolean isErrorOrAlertVisible(WebDriver driver) {
		List<By> hints = Arrays.asList(
				By.cssSelector("[role='alert']"),
				By.xpath("//*[contains(@class,'error') or contains(@class,'Error')]"),
				By.xpath("//*[contains(.,'incorrect') or contains(.,'Invalid') or contains(.,'wrong')]"));
		for (By by : hints) {
			for (WebElement el : driver.findElements(by)) {
				try {
					if (el.isDisplayed()) {
						return true;
					}
				} catch (Exception ignored) {
				}
			}
		}
		return false;
	}

	static void ensureLoginFormVisible(WebDriver driver) {
		dismissCookieBannerIfPresent(driver);
		switchToDefault(driver);
		try {
			findPasswordField(driver);
			return;
		} catch (TimeoutException ignored) {
			// try iframes
		}
		switchToFrameContainingPasswordField(driver);
		findPasswordField(driver);
	}

	static void fillLogin(WebDriver driver, String emailOrUsername, String password) {
		ensureLoginFormVisible(driver);
		WebElement email = findEmailOrUsernameField(driver);
		email.clear();
		email.sendKeys(emailOrUsername);
		WebElement pwd = findPasswordField(driver);
		pwd.clear();
		pwd.sendKeys(password);
	}

	static void submit(WebDriver driver) {
		findSubmitButton(driver).click();
	}

	static void waitForUrlChangeOrAlert(WebDriver driver, String urlBefore, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		try {
			wait.until(d -> !d.getCurrentUrl().equals(urlBefore) || isErrorOrAlertVisible(d));
		} catch (TimeoutException ignored) {
			// page may use in-place validation only
		}
	}

	static boolean isSignOutVisible(WebDriver driver) {
		List<WebElement> els = driver.findElements(By.xpath(
				"//a[contains(.,'Sign out') or contains(.,'Sign Out')]|//button[contains(.,'Sign out')]"));
		for (WebElement el : els) {
			try {
				if (el.isDisplayed()) {
					return true;
				}
			} catch (Exception ignored) {
			}
		}
		return false;
	}

	/**
	 * Uses the address bar after submit: failed login should not land on a shopper “my account” style URL.
	 */
	static boolean urlLooksLikeAuthenticatedShopperArea(String url) {
		String u = url.toLowerCase();
		return u.contains("/my-account") || u.contains("/account/overview") || u.contains("/account/dashboard");
	}

	/**
	 * URL still consistent with login / OAuth / authorisation round-trip (inspect redirects in Network + address bar).
	 */
	static boolean urlIndicatesLoginOrAuthContext(String url) {
		String u = url.toLowerCase();
		return u.contains("login") || u.contains("signin") || u.contains("sign-in") || u.contains("auth")
				|| u.contains("oauth") || u.contains("authorize");
	}

	static String currentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}
}
