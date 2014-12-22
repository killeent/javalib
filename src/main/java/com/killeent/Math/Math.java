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

    /**
     * Computes the binomial coefficient aka 'n choose k'. This is equivalent
     * to finding the number of distinct subsets of size k when picking from
     * a set of n elements.
     *
     * @param n The n index of the binomial coefficient.
     * @param k The k index of the binomial coefficient.
     * @throws java.lang.IllegalArgumentException if n or k is negative.
     * @return The binomial coefficient (n choose k).
     */
    public static long binomialCoefficient(int n, int k) {
        if (k < 0 || n < 0) {
            throw new IllegalArgumentException("values must be positive");
        }

        // return quickly if k > n OR k == n
        if (k > n) {
            return 0;
        } else if (k == n) {
            return 1;
        }

        long[][] matrix = new long[n+1][k+1];

        // n choose 0 is 1 for any value of n
        for (int i = 0; i <= n; i++) {
            matrix[i][0] = 1;
        }

        // n choose k is 0 if n < k
        for (int i = 0; i <= n; i++) {
            for (int j = i+1; j <= k; j++) {
                matrix[i][j] = 0;
            }
        }

        // Note that (n choose k) = (n-1 choose k-1) + (n-1 choose k). We use this identity
        // along with dynamic programming to calculate our results incrementally
        for (int kval = 1; kval <= k; kval++) {
            for (int nval = 1; nval <= n; nval++) {
                matrix[nval][kval] = matrix[nval-1][kval-1] + matrix[nval-1][kval];
            }
        }

        return matrix[n][k];
    }

}
