package com.killeent;

import com.killeent.Array.ArraySearch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link com.killeent.Array.ArraySort}.
 */
public class ArraySearchTest {

    private TestUtil.IntegerComparator integerComparator;

    @Before
    public void setUp() {
        integerComparator = new TestUtil.IntegerComparator();
    }

    /**
     * Tests for {@link com.killeent.Array.ArraySearch#binarySearch}.
     */

    /**
     * Tests searching an empty array.
     */
    @Test
    public void testBinarySearchEmptyArray() {
        Integer[] input = new Integer[0];
        Assert.assertEquals(-1, ArraySearch.binarySearch(input, integerComparator, 0));
    }

    /**
     * Tests searching a single element array.
     */
    @Test
    public void testBinarySearchSingleElementArray() {
        Integer[] input = new Integer[]{1};
        Assert.assertEquals(-1, ArraySearch.binarySearch(input, integerComparator, 0));
        Assert.assertEquals(0, ArraySearch.binarySearch(input, integerComparator, 1));
    }

    /**
     * Test searching a multi-element array.
     */
    @Test
    public void testBinarySearchMultiElementArray() {
        Integer[] input = new Integer[]{1, 2, 3};
        Assert.assertEquals(-1, ArraySearch.binarySearch(input, integerComparator, 0));
        Assert.assertEquals(0, ArraySearch.binarySearch(input, integerComparator, 1));
        Assert.assertEquals(1, ArraySearch.binarySearch(input, integerComparator, 2));
        Assert.assertEquals(2, ArraySearch.binarySearch(input, integerComparator, 3));
    }

}
