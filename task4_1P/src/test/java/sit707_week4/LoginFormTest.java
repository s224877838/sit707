package sit707_week4;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests functions in LoginForm.
 * @author Ahsan Habib
 */
public class LoginFormTest 
{

	@Test
	public void testStudentIdentity() {
		String studentId = "s224877838";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Levin Joseph Poovakulath";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
    public void testFailEmptyUsernameAndEmptyPasswordAndDontCareValCode()
    {
		LoginStatus status = LoginForm.login(null, null);
		Assert.assertTrue( status.isLoginSuccess() == false );
    }
	
	@Test
	public void testEmptyUsername() {
	    LoginStatus status = LoginForm.login(null, "abc");
	    Assert.assertFalse(status.isLoginSuccess());
	}

	@Test
	public void testEmptyPassword() {
	    LoginStatus status = LoginForm.login("ahsan", null);
	    Assert.assertFalse(status.isLoginSuccess());
	}

	@Test
	public void testWrongUsername() {
	    LoginStatus status = LoginForm.login("wrong", "ahsan_pass");
	    Assert.assertFalse(status.isLoginSuccess());
	}

	@Test
	public void testWrongPassword() {
	    LoginStatus status = LoginForm.login("ahsan", "wrong");
	    Assert.assertFalse(status.isLoginSuccess());
	}

	@Test
	public void testCorrectLogin() {
	    LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
	    Assert.assertTrue(status.isLoginSuccess());
	}
	@Test
	public void testEmptyValidationCode() {
	    Assert.assertFalse(LoginForm.validateCode(null));
	}

	@Test
	public void testWrongValidationCode() {
	    Assert.assertFalse(LoginForm.validateCode("abcd"));
	}

	@Test
	public void testCorrectValidationCode() {
	    Assert.assertTrue(LoginForm.validateCode("123456"));
	}
}
