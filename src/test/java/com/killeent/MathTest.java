package com.killeent;

import com.killeent.Math.Math;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test class for the Math library {@link com.killeent.Math.Math}.
 *
 * @author Trevor Killeen (2014)
 */
public class MathTest {

    /**
     * Test for {@link com.killeent.Math.Math#power}.
     */

    public static final double POWER_EPSILON = 0.0000001;

    /**
     * Tests that taking numbers to a power of zero returns 1.
     */
    @Test
    public void testZeroPower() {
        Assert.assertEquals(1, Math.power(0, 0), 0);
        Assert.assertEquals(1, Math.power(-1, 0), 0);
        Assert.assertEquals(1, Math.power(1, 0), 0);
    }

    /**
     * Tests that taking numbers to power of 1 returns those numbers.
     */
    @Test
    public void testOnePower() {
        Assert.assertEquals(0, Math.power(0, 1), 0);
        Assert.assertEquals(-1, Math.power(-1, 1), 0);
        Assert.assertEquals(1, Math.power(1, 1), 0);
    }

    /**
     * Tests that taking numbers to a power of -1 returns their reciprocal.
     */
    @Test
    public void testPowerReciprocal() {
        Assert.assertEquals(1, Math.power(1, -1), 0);
        Assert.assertEquals(1.0/10, Math.power(10, -1), POWER_EPSILON);
        Assert.assertEquals(-1.0/10, Math.power(-10, -1), POWER_EPSILON);
    }

    /**
     * Tests for various positive powers.
     */
    @Test
    public void testPositivePowers() {
        Assert.assertEquals(25, Math.power(5, 2), POWER_EPSILON);
        Assert.assertEquals(9, Math.power(-3, 2), POWER_EPSILON);
        Assert.assertEquals(-27, Math.power(-3, 3), POWER_EPSILON);
        Assert.assertEquals(6.25, Math.power(2.5, 2), POWER_EPSILON);
    }

    /**
     * Test for various negative powers.
     */
    @Test
    public void testNegativePowers() {
        Assert.assertEquals(4, Math.power(0.5, -2), POWER_EPSILON);
        Assert.assertEquals(1.0/9, Math.power(3, -2), POWER_EPSILON);
        Assert.assertEquals(-1.0/27, Math.power(-3, -3), POWER_EPSILON);
    }

    /**
     * Tests for {@link com.killeent.Math.Math#sqrt(double, double)}.
     */

    /**
     * Tests for {@link java.lang.NullPointerException} if input is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSqrtNegativeInput() {
        Math.sqrt(-1, 0);
    }

    /**
     * Tests for {@link java.lang.NullPointerException} if epsilon is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSqrtNegativeEpsilon() {
        Math.sqrt(0, -1);
    }

    /**
     * Tests for square roots less than 1.0.
     */
    @Test
    public void testSqrtInputLessThanOne() {
        double delta = 0.01;     // for junit
        double epsilon = 0.0001; // for sqrt

        Assert.assertEquals(0, Math.sqrt(0, epsilon), delta);
        Assert.assertEquals(0.9, Math.sqrt(0.81, epsilon), delta);
        Assert.assertEquals(0.7, Math.sqrt(0.49, epsilon), delta);
        Assert.assertEquals(0.8062, Math.sqrt(0.65, epsilon), delta);
    }

    /**
     * Tests for square roots greater than or equal to 1.0.
     */
    @Test
    public void testSqrtInputGreaterThanOrEqualToOne() {
        double delta = 0.01;     // for junit
        double epsilon = 0.0001; // for sqrt

        Assert.assertEquals(1.0, Math.sqrt(1.0, epsilon), delta);
        Assert.assertEquals(2.0, Math.sqrt(4.0, epsilon), delta);
        Assert.assertEquals(5.0, Math.sqrt(25.0, epsilon), delta);
        Assert.assertEquals(1.5, Math.sqrt(2.25, epsilon), delta);
        Assert.assertEquals(6.11089, Math.sqrt(37.343, epsilon), delta);
    }

    /**
     * Tests for {@link com.killeent.Math.Math#binomialCoefficient}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if n is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBinomialCoefficientNegativeN() {
        Math.binomialCoefficient(-1, 0);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if k is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBinomialCoefficientNegativeK() {
        Math.binomialCoefficient(0, -1);
    }

    /**
     * Tests for 0 when k > n.
     */
    @Test
    public void testBinomialCoefficientKBiggerThanN() {
        Assert.assertEquals(0, Math.binomialCoefficient(0, 1));
    }

    /**
     * Tests for 1 when k = n.
     */
    @Test
    public void testBinomialCoefficientKEqualsN() {
        Assert.assertEquals(1, Math.binomialCoefficient(1, 1));
        Assert.assertEquals(1, Math.binomialCoefficient(10, 10));
        Assert.assertEquals(1, Math.binomialCoefficient(1000, 1000));
    }

    /**
     * Tests a range of k values for the binomial coefficient.
     */
    @Test
    public void testBinomialCoefficientKRange() {
        Assert.assertEquals(1, Math.binomialCoefficient(6, 0));
        Assert.assertEquals(6, Math.binomialCoefficient(6, 1));
        Assert.assertEquals(15, Math.binomialCoefficient(6, 2));
        Assert.assertEquals(20, Math.binomialCoefficient(6, 3));
        Assert.assertEquals(15, Math.binomialCoefficient(6, 4));
        Assert.assertEquals(6, Math.binomialCoefficient(6, 5));
        Assert.assertEquals(1, Math.binomialCoefficient(6, 6));
    }

    /**
     * Tests for {@link com.killeent.Math.Math#enumeratePrimes(int)}.
     */

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} if n is < 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEnumeratePrimesNLessThan2() {
        Math.enumeratePrimes(1);
    }

    /**
     * Tests for enumerating all primes between 2 and 2.
     */
    @Test
    public void testEnumeratePrimes2To2() {
        List<Integer> result = Math.enumeratePrimes(2);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains(2));
    }

    /**
     * Tests for enumerating all primes between 2 and 100.
     */
    @Test
    public void testEnumeratePrimes2To100() {
        List<Integer> result = Math.enumeratePrimes(100);
        Integer[] expected = new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
                53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        Assert.assertEquals(expected.length, result.size());
        for(int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], result.get(i));
        }
    }


}
