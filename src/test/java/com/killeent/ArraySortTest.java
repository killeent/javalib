package com.killeent;

import com.killeent.Array.ArraySort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

/**
 * Tests for {@link com.killeent.Array.ArraySort}.
 */
public class ArraySortTest {

    private IntegerComparator integerComparator;

    @Before
    public void setUp() {
        integerComparator = new IntegerComparator();
    }

    /**
     * Tests common to all sort implementations:
     * {@link com.killeent.Array.ArraySort#quickSort}.
     */

    /**
     * Tests sorting an empty array.
     */
    @Test
    public void testSortEmptyArray() {
        Integer[] input = new Integer[0];
        Integer[] expected = new Integer[0];
        runTest(input, expected);
    }

    /**
     * Tests sorting a single element array.
     */
    @Test
    public void testSortSingleElementArray() {
        Integer[] input = new Integer[]{1};
        Integer[] expected = new Integer[]{1};
        runTest(input, expected);
    }

    /**
     * Tests sorting a multi-element array.
     */
    @Test
    public void testSortMultiElementArray() {
        Integer[] input = new Integer[]{1, 4, 3};
        Integer[] expected = new Integer[]{1, 3, 4};
        runTest(input, expected);
    }

    /**
     * Tests sorting a multi-element array that is already sorted.
     */
    @Test
    public void testSortAlreadySortedArray() {
        Integer[] input = new Integer[]{1, 3, 4, 7, 10};
        Integer[] expected = new Integer[]{1, 3, 4, 7, 10};
        runTest(input, expected);
    }

    /**
     * Tests sorting a multi-element array that is initially in reverse order.
     */
    @Test
    public void testSortReverseOrderArray() {
        Integer[] input = new Integer[]{1, 0, -1, -2};
        Integer[] expected = new Integer[]{-2, -1, 0, 1};
        runTest(input, expected);
    }

    /**
     * Tests sorting an array with duplicates.
     */
    @Test
    public void testSortDuplicateValueArray() {
        Integer[] input = new Integer[]{1, 2, 3, 3, 2, 1, 2, 2, 4};
        Integer[] expected = new Integer[]{1, 1, 2, 2, 2, 2, 3, 3, 4};
        runTest(input, expected);
    }

    /**
     * Test sorting an array with all the same values.
     */
    @Test
    public void testSortUniformArray() {
        Integer[] input = new Integer[]{0, 0, 0, 0};
        Integer[] expected = new Integer[]{0, 0, 0, 0};
        runTest(input, expected);
    }

    /**
     * Tests sorting an array with all the same values, except for the last
     * element being the max/min.
     */
    @Test
    public void testSortBoundaryArray() {
        Integer[] input = new Integer[]{5, 1, 1, 1};
        Integer[] expected = new Integer[]{1, 1, 1, 5};
        runTest(input, expected);
        input = new Integer[]{1, 1, 1, 0};
        expected = new Integer[]{0, 1, 1, 1};
        runTest(input, expected);
    }

    /**
     * Helper function to run the unit test on all of the sorts.
     */
    private void runTest(Integer[] input, Integer[] expected) {
        ArraySort.quickSort(input, integerComparator);
        Assert.assertArrayEquals(expected, input);
    }


    /**
     * Simple integer comparator.
     */
    private class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

}
