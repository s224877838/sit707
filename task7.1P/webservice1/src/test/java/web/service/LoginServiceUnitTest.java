package web.service;

import org.junit.Assert;
import org.junit.Test;

public class LoginServiceUnitTest {

	@Test
	public void loginShouldSucceedForValidCredentials() {
		Assert.assertTrue(LoginService.login("ahsan", "ahsan_pass", "1990-01-01"));
	}

	@Test
	public void loginShouldAcceptTrimmedInput() {
		Assert.assertTrue(LoginService.login("  ahsan ", " ahsan_pass ", " 1990-01-01 "));
	}

	@Test
	public void loginShouldFailForWrongUsername() {
		Assert.assertFalse(LoginService.login("ahsa", "ahsan_pass", "1990-01-01"));
	}

	@Test
	public void loginShouldFailForWrongPassword() {
		Assert.assertFalse(LoginService.login("ahsan", "bad_password", "1990-01-01"));
	}

	@Test
	public void loginShouldFailForWrongDobEvenWithValidUserAndPassword() {
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-01-02"));
	}

	@Test
	public void loginShouldFailWhenDobHasInvalidDate() {
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-02-30"));
	}

	@Test
	public void loginShouldFailWhenDobHasWrongFormat() {
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", "01-01-1990"));
	}

	@Test
	public void loginShouldFailForNullValues() {
		Assert.assertFalse(LoginService.login(null, "ahsan_pass", "1990-01-01"));
		Assert.assertFalse(LoginService.login("ahsan", null, "1990-01-01"));
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", null));
	}

	@Test
	public void loginShouldFailForBlankValues() {
		Assert.assertFalse(LoginService.login(" ", "ahsan_pass", "1990-01-01"));
		Assert.assertFalse(LoginService.login("ahsan", " ", "1990-01-01"));
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", " "));
	}
}
