package com.killeent.Math;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

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
     * Calculates the sqrt of the input within the specified epsilon, i.e. the result^2
     * is less than epsilon away from input. Makes use of no mathematical constructs, just
     * simply performs binary search.
     *
     * @param input The double-precision number to calculate a sqrt for.
     * @param epsilon The result squared will be less than epsilon away from input.
     * @throws java.lang.IllegalArgumentException if input, epsilon is < 0.
     * @return A sqrt estimate of input.
     */
    public static double sqrt(double input, double epsilon) {
        if (input < 0 || epsilon < 0) {
            throw new IllegalArgumentException("negative input to sqrt");
        }

        double lo;
        double hi;

        if (input < 1.0) {
            lo = input;
            hi = 1.0;
        } else {
            lo = 1.0;
            hi = input;
        }

        while (lo < hi) {
            double mid = lo + (hi-lo)/2.0;
            double estimate = mid * mid;
            if (abs(estimate - input) < epsilon) {
                return mid;
            } else if (estimate > input) {
                hi = mid;
            } else {
                lo = mid;
            }
        }

        return lo;
    }

    /**
     * @return The absolute value of input.
     */
    private static double abs(double input) {
        return input >= 0 ? input : -input;
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
    public static BigInteger binomialCoefficient(int n, int k) {
        if (k < 0 || n < 0) {
            throw new IllegalArgumentException("values must be positive");
        }

        // return quickly if k > n OR k == n
        if (k > n) {
            return BigInteger.ZERO;
        } else if (k == n) {
            return BigInteger.ONE;
        }

        // pick the lowest equivalent value of k ((n choose k) == (n choose n-k))
        if (n - k < k) {
            k = n-k;
        }

        BigInteger[][] matrix = new BigInteger[n+1][k+1];

        // n choose 0 is 1 for any value of n
        for (int i = 0; i <= n; i++) {
            matrix[i][0] = BigInteger.ONE;
        }

        // n choose k is 0 if n < k
        for (int i = 0; i <= n; i++) {
            for (int j = i+1; j <= k; j++) {
                matrix[i][j] = BigInteger.ZERO;
            }
        }

        // Note that (n choose k) = (n-1 choose k-1) + (n-1 choose k). We use this identity
        // along with dynamic programming to calculate our results incrementally
        for (int kval = 1; kval <= k; kval++) {
            for (int nval = 1; nval <= n; nval++) {
                matrix[nval][kval] = matrix[nval-1][kval-1].add(matrix[nval-1][kval]);
            }
        }

        return matrix[n][k];
    }

    /**
     * Returns a list of all prime numbers between 2 and n.
     *
     * @param n Upper (inclusive) bound on numbers to consider.
     * @return An ordered list of primes between 2 and n.
     */
    public static List<Integer> enumeratePrimes(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("n must be >= 2");
        }

        List<Integer> result = new LinkedList<Integer>();

        // composites[i] is set to false if i is not a composite integer -> it is prime
        BitSet composites = new BitSet(n+1);

        for (int i = 2; i <= n; i++) {
            if (!composites.get(i)) {
                result.add(i);

                // set all multiples of i to be composite
                int j = i * i;
                while (j <= n) {
                    composites.set(j);
                    j = j + i;
                }
            }
        }

        return result;
    }

}
