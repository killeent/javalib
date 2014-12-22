package com.killeent;

import com.killeent.Math.Math;
import org.junit.Assert;
import org.junit.Test;

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


}
