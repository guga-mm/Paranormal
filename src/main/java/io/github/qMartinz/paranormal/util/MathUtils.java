package io.github.qMartinz.paranormal.util;

public class MathUtils {
	/**
	 * Returns the given value if between the lower and the upper bound. If the value is less than the lower bound,
	 * returns the lower bound. If the value is greater than the upper bound, returns the upper bound.
	 * @param pValue The value that is clamped.
	 * @param pMin The lower bound for the clamp.
	 * @param pMax The upper bound for the clamp.
	 */
	public static double clamp(double pValue, double pMin, double pMax) {
		if (pValue < pMin) {
			return pMin;
		} else {
			return pValue > pMax ? pMax : pValue;
		}
	}

	public static int Oscillate(int input, int min, int max) {
		int range = max - min ;
		return min + Math.abs(((input + range) % (range * 2)) - range);
	}
}
