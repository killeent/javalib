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

}
