package sit707_week6;

public class LoopCoverageUtils {

	/**
	 * Part B(a): conditional loop with simple statements only.
	 * Returns sum of all integers from 1..n, and 0 for n <= 0.
	 */
	public static int sumUpTo(int n) {
		int sum = 0;
		int i = 1;
		while (i <= n) {
			sum += i;
			i++;
		}
		return sum;
	}

	/**
	 * Part B(b): conditional loop with an inner conditional statement.
	 * Counts how many numbers in the array are positive.
	 */
	public static int countPositiveNumbers(int[] values) {
		if (values == null) {
			throw new IllegalArgumentException("values cannot be null");
		}

		int count = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > 0) {
				count++;
			}
		}
		return count;
	}
}
