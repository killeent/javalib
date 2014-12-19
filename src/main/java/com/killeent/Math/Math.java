package com.killeent.Math;

/**
 * Various mathematical operations.
 *
 * @author Trevor Killeen (2014)
 */
public class Math {

    /**
     * Computes x^y.
     *
     * @param x The base x.
     * @param y The exponent y.
     * @return x^y.
     */
    public static double power(double x, int y) {
        boolean negative = y < 0;
        return negative ? 1 / powerRecursive(x, -y) : powerRecursive(x, y);
    }

    /**
     * Recursive helper function to calculate the x^y.
     */
    private static double powerRecursive(double x, int y) {
        if (y == 0) {
            return 1;
        } else if (y % 2 == 1) {
            return x * powerRecursive(x, y-1);
        } else {
            return powerRecursive(x, y/2) * powerRecursive(x, y/2);
        }
    }

}
