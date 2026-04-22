package web.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Business logic to handle login functions.
 * 
 * @author Ahsan.
 */
public class LoginService {

	private static final String VALID_USERNAME = "ahsan";
	private static final String VALID_PASSWORD = "ahsan_pass";
	private static final String VALID_DOB = "1990-01-01";
	private static final DateTimeFormatter DOB_FORMATTER =
			DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

	/**
	 * Static method returns true for successful login, false otherwise.
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean login(String username, String password, String dob) {
		if (!isNonBlank(username) || !isNonBlank(password) || !isNonBlank(dob)) {
			return false;
		}

		String normalizedUsername = username.trim();
		String normalizedPassword = password.trim();
		String normalizedDob = dob.trim();

		if (!isDobValid(normalizedDob)) {
			return false;
		}

		return VALID_USERNAME.equals(normalizedUsername)
				&& VALID_PASSWORD.equals(normalizedPassword)
				&& VALID_DOB.equals(normalizedDob);
	}

	private static boolean isNonBlank(String value) {
		return value != null && !value.trim().isEmpty();
	}

	private static boolean isDobValid(String dob) {
		try {
			LocalDate.parse(dob, DOB_FORMATTER);
			return true;
		} catch (DateTimeParseException ex) {
			return false;
		}
	}
}
