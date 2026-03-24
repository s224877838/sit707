package sit707_tasks;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Levin Joseph Poovakulath
 */
public class DateUtilTest {
	
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
	public void testMaxJanuary31ShouldIncrementToFebruary1() {
		DateUtil date = new DateUtil(31, 1, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(2, date.getMonth());
	}

	@Test
	public void testMaxJanuary31ShouldDecrementToJanuary30() {
		DateUtil date = new DateUtil(31, 1, 2024);
		date.decrement();
		Assert.assertEquals(30, date.getDay());
		Assert.assertEquals(1, date.getMonth());
	}

	@Test
	public void testMinJanuary1ShouldIncrementToJanuary2() {
		DateUtil date = new DateUtil(1, 1, 2024);
		date.increment();
		Assert.assertEquals(2, date.getDay());
		Assert.assertEquals(1, date.getMonth());
	}

	@Test
	public void testMinJanuary1ShouldDecrementToDecember31() {
		DateUtil date = new DateUtil(1, 1, 2024);
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(12, date.getMonth());
		Assert.assertEquals(2023, date.getYear());
	}

	@Test
	public void testNominalJanuary() {
		int rand_day = 1 + new Random().nextInt(31);
		DateUtil date = new DateUtil(rand_day, 1, 2024);
		date.increment();
		Assert.assertTrue(date.getDay() >= 1 && date.getDay() <= 31);
	}

	@Test
	public void testMaxApril30ShouldIncrementToMay1() {
		DateUtil date = new DateUtil(30, 4, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(5, date.getMonth());
	}

	@Test
	public void testMinApril1ShouldDecrementToMarch31() {
		DateUtil date = new DateUtil(1, 4, 2024);
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(3, date.getMonth());
	}

	//  FEBRUARY (LEAP YEAR) 
	@Test
	public void testLeapYearFeb29ShouldIncrementToMarch1() {
		DateUtil date = new DateUtil(29, 2, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth());
	}

	@Test
	public void testMarch1ShouldDecrementToFeb29LeapYear() {
		DateUtil date = new DateUtil(1, 3, 2024);
		date.decrement();
		Assert.assertEquals(29, date.getDay());
		Assert.assertEquals(2, date.getMonth());
	}

	//  FEBRUARY (NON-LEAP YEAR) 
	@Test
	public void testNonLeapYearFeb28ShouldIncrementToMarch1() {
		DateUtil date = new DateUtil(28, 2, 2023);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth());
	}	

	@Test
	public void testMaxDecember31ShouldIncrementToJanuary1NextYear() {
		DateUtil date = new DateUtil(31, 12, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(1, date.getMonth());
		Assert.assertEquals(2025, date.getYear());
	}

	@Test
	public void testMiddleOfMonthIncrement() {
		DateUtil date = new DateUtil(15, 6, 2024);
		date.increment();
		Assert.assertEquals(16, date.getDay());
		Assert.assertEquals(6, date.getMonth());
	}
	
}