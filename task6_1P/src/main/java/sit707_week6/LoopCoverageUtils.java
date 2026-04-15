package sit707_week6;

public class LoopCoverageUtils {

	public static int sumUpTo(int n) {
		int sum = 0;
		int i = 1;
		while (i <= n) {
			sum += i;
			i++;
		}
		return sum;
	}


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
