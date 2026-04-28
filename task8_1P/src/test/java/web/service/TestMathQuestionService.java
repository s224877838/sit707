package web.service;

import org.junit.Assert;
import org.junit.Test;

public class TestMathQuestionService {

	@Test
	public void testQ1AdditionWithValidNumbers() {
		Assert.assertEquals(3.0, MathQuestionService.q1Addition("1", "2"), 0.0);
	}

	@Test
	public void testQ2SubtractionWithValidNumbers() {
		Assert.assertEquals(2.5, MathQuestionService.q2Subtraction("5.5", "3"), 0.0);
	}

	@Test
	public void testQ1AdditionWithBlankNumberThrowsHelpfulMessage() {
		IllegalArgumentException exception = Assert.assertThrows(
				IllegalArgumentException.class,
				() -> MathQuestionService.q1Addition("", "2"));

		Assert.assertEquals("First number is required.", exception.getMessage());
	}

	@Test
	public void testQ1AdditionWithInvalidNumberThrowsHelpfulMessage() {
		IllegalArgumentException exception = Assert.assertThrows(
				IllegalArgumentException.class,
				() -> MathQuestionService.q1Addition("abc", "2"));

		Assert.assertEquals("First number must be a valid number.", exception.getMessage());
	}
}
