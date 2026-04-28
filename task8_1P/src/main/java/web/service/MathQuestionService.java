package web.service;

public class MathQuestionService {

	private static double parseNumber(String value, String fieldName) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(fieldName + " is required.");
		}

		try {
			return Double.parseDouble(value.trim());
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(fieldName + " must be a valid number.", ex);
		}
	}

	public static double q1Addition(String number1, String number2) {
		double result = parseNumber(number1, "First number") + parseNumber(number2, "Second number");
		return result;
	}
	
	public static double q2Subtraction(String number1, String number2) {
		double result = parseNumber(number1, "First number") - parseNumber(number2, "Second number");
		return result;
	}
}
