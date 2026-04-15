package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class LoopCoverageUtilsTest {

	@Test
	public void testSumUpToNormalValue() {
		Assert.assertEquals(15, LoopCoverageUtils.sumUpTo(5));
	}

	@Test
	public void testSumUpToZero() {
		Assert.assertEquals(0, LoopCoverageUtils.sumUpTo(0));
	}

	@Test
	public void testSumUpToNegative() {
		Assert.assertEquals(0, LoopCoverageUtils.sumUpTo(-3));
	}

	@Test
	public void testCountPositiveNumbersMixedValues() {
		Assert.assertEquals(2, LoopCoverageUtils.countPositiveNumbers(new int[] { -5, 0, 4, 7 }));
	}

	@Test
	public void testCountPositiveNumbersNoPositives() {
		Assert.assertEquals(0, LoopCoverageUtils.countPositiveNumbers(new int[] { -2, -1, 0 }));
	}

	@Test
	public void testCountPositiveNumbersEmptyArray() {
		Assert.assertEquals(0, LoopCoverageUtils.countPositiveNumbers(new int[] {}));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountPositiveNumbersNullArray() {
		LoopCoverageUtils.countPositiveNumbers(null);
	}
}
