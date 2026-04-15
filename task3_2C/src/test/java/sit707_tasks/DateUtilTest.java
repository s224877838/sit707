package sit707_tasks;

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

	// D1 (1-28), M2 (31-day month), Y2 (non-leap)
	@Test
	public void testD1M2Y2IncrementWithinMonth() {
		DateUtil date = new DateUtil(15, 1, 2023);
		date.increment();
		Assert.assertEquals(16, date.getDay());
		Assert.assertEquals(1, date.getMonth());
		Assert.assertEquals(2023, date.getYear());
	}

	// D2 (29), M3 (February), Y1 (leap)
	@Test
	public void testD2M3Y1IncrementToMarchFirst() {
		DateUtil date = new DateUtil(29, 2, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	// D3 (30), M1 (30-day month), Y2 (non-leap)
	@Test
	public void testD3M1Y2IncrementToNextMonth() {
		DateUtil date = new DateUtil(30, 4, 2023);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(5, date.getMonth());
		Assert.assertEquals(2023, date.getYear());
	}

	// D4 (31), M2 (31-day month), Y1 (leap)
	@Test
	public void testD4M2Y1IncrementToNextMonth() {
		DateUtil date = new DateUtil(31, 1, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(2, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testDecrementAcrossMonthBoundary() {
		DateUtil date = new DateUtil(1, 4, 2024);
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(3, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testDecrementAcrossYearBoundary() {
		DateUtil date = new DateUtil(1, 1, 2024);
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(12, date.getMonth());
		Assert.assertEquals(2023, date.getYear());
	}

	@Test
	public void testIncrementAcrossYearBoundary() {
		DateUtil date = new DateUtil(31, 12, 2023);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(1, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testInvalidDayBelowRangeShouldThrow() {
		try {
			new DateUtil(0, 1, 2024);
			Assert.fail("Expected invalid day to throw");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().contains("Invalid day"));
		}
	}

	@Test
	public void testInvalidMonthAboveRangeShouldThrow() {
		try {
			new DateUtil(1, 13, 2024);
			Assert.fail("Expected invalid month to throw");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().contains("Invalid month"));
		}
	}

	@Test
	public void testInvalidYearBelowRangeShouldThrow() {
		try {
			new DateUtil(1, 1, 1699);
			Assert.fail("Expected invalid year to throw");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().contains("Invalid year"));
		}
	}

	@Test
	public void testInvalidDate31InAprilShouldThrow() {
		try {
			new DateUtil(31, 4, 2024);
			Assert.fail("Expected invalid day for month to throw");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().contains("max day"));
		}
	}

	@Test
	public void testInvalidFeb29InNonLeapYearShouldThrow() {
		try {
			new DateUtil(29, 2, 2023);
			Assert.fail("Expected non-leap Feb 29 to throw");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().contains("max day"));
		}
	}
	
}