package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class WeatherAndMathUtilsTest {
	
	@Test
	public void testStudentIdentity() {
		String studentId = "s224877838"; // Replace with your own if needed.
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Levin Joseph Poovakulath"; // Replace with your own if needed.
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
	public void testTrueNumberIsEven() {
		Assert.assertTrue(WeatherAndMathUtils.isEven(10));
	}
	
	@Test
	public void testFalseNumberIsEven() {
		Assert.assertFalse(WeatherAndMathUtils.isEven(9));
	}

	@Test
	public void testCancelWeatherAdviceByWind() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(70.1, 0.0));
	}

	@Test
	public void testCancelWeatherAdviceByRain() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(10.0, 6.1));
	}

	@Test
	public void testCancelWeatherAdviceByCombinedConcerningLevels() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(46.0, 4.1));
	}

	@Test
	public void testWarnWeatherAdviceByWindOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(50.0, 0.0));
	}

	@Test
	public void testWarnWeatherAdviceByRainOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(10.0, 4.5));
	}

	@Test
	public void testAllClearWeatherAdvice() {
		Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(20.0, 1.0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWeatherAdviceRejectsNegativeWind() {
		WeatherAndMathUtils.weatherAdvice(-0.1, 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWeatherAdviceRejectsNegativeRain() {
		WeatherAndMathUtils.weatherAdvice(0.0, -0.1);
	}

	@Test
	public void testPrimeForOne() {
		Assert.assertTrue(WeatherAndMathUtils.isPrime(1));
	}

	@Test
	public void testPrimeForEvenComposite() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(4));
	}

	@Test
	public void testPrimeForOddPrime() {
		Assert.assertTrue(WeatherAndMathUtils.isPrime(3));
	}

}
