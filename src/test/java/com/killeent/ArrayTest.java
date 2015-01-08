package com.killeent;

import com.killeent.Array.Array;
import com.killeent.Misc.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for {@link com.killeent.Array.Array}.
 *
 * @author Trevor Killeen (2014)
 */
public class ArrayTest {

    private TestUtil.IntegerComparator integerComparator;

    @Before
    public void setUp() {
        integerComparator = new TestUtil.IntegerComparator();
    }

    /**
     * Tests common to all sort implementations:
     * {@link com.killeent.Array.Array#quickSort(Object[], java.util.Comparator)},
     * {@link com.killeent.Array.Array#mergeSort(Object[], java.util.Comparator)}.
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
        Array.quickSort(input, integerComparator);
        Array.mergeSort(input, integerComparator);
        Assert.assertArrayEquals(expected, input);
    }

    /**
     * Tests for {@link com.killeent.Array.Array#binarySearch}.
     */

    /**
     * Tests searching an empty array.
     */
    @Test
    public void testBinarySearchEmptyArray() {
        Integer[] input = new Integer[0];
        Assert.assertEquals(-1, Array.binarySearch(input, integerComparator, 0));
    }

    /**
     * Tests searching a single element array.
     */
    @Test
    public void testBinarySearchSingleElementArray() {
        Integer[] input = new Integer[]{1};
        Assert.assertEquals(-1, Array.binarySearch(input, integerComparator, 0));
        Assert.assertEquals(0, Array.binarySearch(input, integerComparator, 1));
    }

    /**
     * Test searching a multi-element array.
     */
    @Test
    public void testBinarySearchMultiElementArray() {
        Integer[] input = new Integer[]{1, 2, 3};
        Assert.assertEquals(-1, Array.binarySearch(input, integerComparator, 0));
        Assert.assertEquals(0, Array.binarySearch(input, integerComparator, 1));
        Assert.assertEquals(1, Array.binarySearch(input, integerComparator, 2));
        Assert.assertEquals(2, Array.binarySearch(input, integerComparator, 3));
    }

    /**
     * Tests for {@link com.killeent.Array.Array#firstOccurrence}.
     */

    /**
     * Tests searching an empty array.
     */
    @Test
    public void testFirstOccurrenceEmptyArray() {
        Integer[] input = new Integer[0];
        Assert.assertEquals(-1, Array.firstOccurrence(input, integerComparator, 0));
    }

    /**
     * Tests searching a single element array.
     */
    @Test
    public void testFirstOccurrenceSingleElementArray() {
        Integer[] input = new Integer[]{1};
        Assert.assertEquals(-1, Array.firstOccurrence(input, integerComparator, 0));
        Assert.assertEquals(0, Array.firstOccurrence(input, integerComparator, 1));
    }

    /**
     * Test searching a multi-element array without duplicates.
     */
    @Test
    public void testFirstOccurrenceMultiElementArrayNoDupes() {
        Integer[] input = new Integer[]{1, 2, 3};
        Assert.assertEquals(-1, Array.firstOccurrence(input, integerComparator, 0));
        Assert.assertEquals(0, Array.firstOccurrence(input, integerComparator, 1));
        Assert.assertEquals(1, Array.firstOccurrence(input, integerComparator, 2));
        Assert.assertEquals(2, Array.firstOccurrence(input, integerComparator, 3));
    }

    /**
     * Test searching a multi-element array without duplicates.
     */
    @Test
    public void testFirstOccurrenceMultiElementArrayWithDupes() {
        Integer[] input = new Integer[]{1, 1, 2, 2, 3, 3, 4};
        Assert.assertEquals(-1, Array.firstOccurrence(input, integerComparator, 0));
        Assert.assertEquals(0, Array.firstOccurrence(input, integerComparator, 1));
        Assert.assertEquals(2, Array.firstOccurrence(input, integerComparator, 2));
        Assert.assertEquals(4, Array.firstOccurrence(input, integerComparator, 3));
        Assert.assertEquals(6, Array.firstOccurrence(input, integerComparator, 4));
    }

    /**
     * Tests for {@link com.killeent.Array.Array#kthSmallestElement}.
     */

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when using a negative
     * k value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testKthSmallestNegativeK() {
        Integer[] input = new Integer[0];
        Array.kthSmallestElement(input, integerComparator, -1);
    }

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when using 0 as a
     * k value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testKthSmallestZeroK() {
        Integer[] input = new Integer[0];
        Array.kthSmallestElement(input, integerComparator, 0);
    }

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when using k is bigger
     * than the array length.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testKthSmallestKOutOfBounds() {
        Integer[] input = new Integer[0];
        Array.kthSmallestElement(input, integerComparator, 1);
    }

    /**
     * Tests for finding the kth smallest element in a single element array.
     */
    @Test
    public void testKthSmallestOneElementArray() {
        Integer[] input = new Integer[]{1};
        Assert.assertEquals(new Integer(1), Array.kthSmallestElement(input, integerComparator, 1));
    }

    /**
     * Tests for finding the kth smallest elements in a two element array.
     */
    @Test
    public void testKthSmallestTwoElementArray() {
        Integer[] input = new Integer[]{1, 2};
        Assert.assertEquals(new Integer(1), Array.kthSmallestElement(input, integerComparator, 1));
        Assert.assertEquals(new Integer(2), Array.kthSmallestElement(input, integerComparator, 2));
    }

    /**
     * Tests finding the kth smallest elements in a large array.
     */
    @Test
    public void testKthSmallestLargeArray() {
        Integer[] input = new Integer[]{4, 5, 7, 1, 2, 9, 0, 6, 3, 8};
        Assert.assertEquals(new Integer(0), Array.kthSmallestElement(input, integerComparator, 1));
        Assert.assertEquals(new Integer(1), Array.kthSmallestElement(input, integerComparator, 2));
        Assert.assertEquals(new Integer(2), Array.kthSmallestElement(input, integerComparator, 3));
        Assert.assertEquals(new Integer(3), Array.kthSmallestElement(input, integerComparator, 4));
        Assert.assertEquals(new Integer(4), Array.kthSmallestElement(input, integerComparator, 5));
        Assert.assertEquals(new Integer(5), Array.kthSmallestElement(input, integerComparator, 6));
        Assert.assertEquals(new Integer(6), Array.kthSmallestElement(input, integerComparator, 7));
        Assert.assertEquals(new Integer(7), Array.kthSmallestElement(input, integerComparator, 8));
        Assert.assertEquals(new Integer(8), Array.kthSmallestElement(input, integerComparator, 9));
        Assert.assertEquals(new Integer(9), Array.kthSmallestElement(input, integerComparator, 10));

    }

    /**
     * Test for {@link com.killeent.Array.Array#inversions}.
     */

    /**
     * Tests for 0 inversions in an empty array.
     */

    @Test
    public void testInversionsEmptyArray() {
        Integer[] input = new Integer[0];
        Assert.assertEquals(0, Array.inversions(input, integerComparator));
    }

    /**
     * Tests for 0 inversions in a single element array.
     */
    @Test
    public void testInversionsSingleElementArray() {
        Integer[] input = new Integer[]{1};
        Assert.assertEquals(0, Array.inversions(input, integerComparator));
    }

    /**
     * Tests for 0 inversions in a sorted array.
     */
    @Test
    public void testInversionsSortedArray() {
        Integer[] input = new Integer[]{1, 2};
        Assert.assertEquals(0, Array.inversions(input, integerComparator));
        input = new Integer[]{1, 2, 3, 4, 5};
        Assert.assertEquals(0, Array.inversions(input, integerComparator));
        input = new Integer[]{1, 2, 2, 3, 3, 4, 4, 4, 4, 4, 5, 5, 6};
        Assert.assertEquals(0, Array.inversions(input, integerComparator));
    }

    /**
     * Tests for 0 inversions in an array with all of the same element.
     */
    @Test
    public void testInversionsSameElementArray() {
        Integer[] input = new Integer[]{1, 1, 1, 1, 1};
        Assert.assertEquals(0, Array.inversions(input, integerComparator));
    }

    /**
     * Tests for finding all possible inversions in a reverse sorted array. This
     * is (n choose 2) where n is the number of elements in the array.
     */
    @Test
    public void testInversionsReverseSortedArray() {
        Integer[] input = new Integer[]{3, 2, 1};
        Assert.assertEquals(3, Array.inversions(input, integerComparator));
        input = new Integer[]{5, 4, 3, 2, 1};
        Assert.assertEquals(10, Array.inversions(input, integerComparator));
        input = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        Assert.assertEquals(36, Array.inversions(input, integerComparator));
    }

    /**
     * Tests finding inversions from an array with various sequences of increasing
     * and decreasing values.
     */
    @Test
    public void testInversionsComplexArray() {
        Integer[] input = new Integer[]{4, 5, 3, 7, 4, 5, 1, 2, 10};
        Assert.assertEquals(17, Array.inversions(input, integerComparator));
    }

    /**
     * Tests for {@link com.killeent.Array.Array#removeDuplicates}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if arr is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDuplicatesNullArray() {
        Array.removeDuplicates(null);
    }

    /**
     * Tests removing duplicates from an empty array.
     */
    @Test
    public void testRemoveDuplicatesEmptyArray() {
        Assert.assertEquals(0, Array.removeDuplicates(new Integer[0]));
    }

    /**
     * Tests removing duplicates from a single element array.
     */
    @Test
    public void testRemoveDuplicatesSingleElementArray() {
        Assert.assertEquals(0, Array.removeDuplicates(new Integer[]{1}));
    }

    /**
     * Tests removing duplicates from an array with no duplicates.
     */
    @Test
    public void testRemoveDuplicatesDuplicateFreeArray() {
        Integer[] input = new Integer[]{1, 2, 3, 4, 5};
        Integer[] expected = new Integer[]{1, 2, 3, 4, 5};
        Assert.assertEquals(0, Array.removeDuplicates(input));
        Assert.assertEquals(expected, input);
    }

    /**
     * Tests removing duplicates from an array with all duplicates.
     */
    @Test
    public void testRemoveDuplicatesAllDuplicateArray() {
        Integer[] input = new Integer[]{1, 1, 1, 1, 1};
        Integer[] expected = new Integer[]{1};
        Assert.assertEquals(4, Array.removeDuplicates(input));
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], input[i]);
        }
        input = new Integer[]{1, 1, 2, 2, 2, 3, 3};
        expected = new Integer[]{1, 2, 3};
        Assert.assertEquals(4, Array.removeDuplicates(input));
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], input[i]);
        }
    }

    /**
     * Tests removing duplicates from a mixed array.
     */
    @Test
    public void testRemoveDuplicatesMixedArray() {
        Integer[] input = new Integer[]{1, 2, 2, 3, 4, 4, 4, 5, 6, 9, 11, 11};
        Integer[] expected = new Integer[]{1, 2, 3, 4, 5, 6, 9, 11};
        Assert.assertEquals(4, Array.removeDuplicates(input));
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], input[i]);
        }
    }

    /**
     * Tests for {@link com.killeent.Array.Array#minCover(Object[], java.util.Set)}.
     */

    /**
     * Tests for a null min cover when the match is bigger than input.
     */
    @Test
    public void testMinCoverLargerMatch() {
        Integer[] input = new Integer[0];
        Set<Integer> match = new HashSet<Integer>();
        match.add(1);
        Assert.assertNull(Array.minCover(input, match));
    }

    /**
     * Tests for a min cover for a single element input.
     */
    @Test
    public void testMinCoverSingleElementInput() {
        Integer[] input = new Integer[]{1};

        // cover found
        Set<Integer> match = new HashSet<Integer>();
        match.add(1);
        Pair<Integer> expected = new Pair<Integer>(0, 1);
        Assert.assertEquals(expected, Array.minCover(input, match));

        // no cover
        match.clear();
        match.add(2);
        Assert.assertNull(Array.minCover(input, match));
    }

    /**
     * Tests for a min cover for multi element input.
     */
    @Test
    public void testMinCoverMultiElementInput() {
        Integer[] input = new Integer[]{1, 2, 3};

        // cover found (single element)
        Set<Integer> match = new HashSet<Integer>();
        match.add(1);
        Pair<Integer> expected = new Pair<Integer>(0, 1);
        Assert.assertEquals(expected, Array.minCover(input, match));

        match.clear();
        match.add(2);
        expected = new Pair<Integer>(1, 2);
        Assert.assertEquals(expected, Array.minCover(input, match));

        match.clear();
        match.add(3);
        expected = new Pair<Integer>(2, 3);
        Assert.assertEquals(expected, Array.minCover(input, match));

        // cover found (two elements)
        match.clear();
        TestUtil.addArrayElementsToCollection(match, 1, 2);
        expected = new Pair<Integer>(0, 2);
        Assert.assertEquals(expected, Array.minCover(input, match));

        match.clear();
        TestUtil.addArrayElementsToCollection(match, 1, 3);
        expected = new Pair<Integer>(0, 3);
        Assert.assertEquals(expected, Array.minCover(input, match));

        match.clear();
        TestUtil.addArrayElementsToCollection(match, 2, 3);
        expected = new Pair<Integer>(1, 3);
        Assert.assertEquals(expected, Array.minCover(input, match));

        // cover found (all elements)
        match.clear();
        TestUtil.addArrayElementsToCollection(match, 1, 2, 3);
        expected = new Pair<Integer>(0, 3);
        Assert.assertEquals(expected, Array.minCover(input, match));

        // no cover (single element)
        match.clear();
        match.add(4);
        Assert.assertNull(Array.minCover(input, match));

        // no cover (multiple elements, all not in input)
        match.clear();
        TestUtil.addArrayElementsToCollection(match, 5, 6);
        Assert.assertNull(Array.minCover(input, match));

        // no cover (multiple elements, one not in input)
        match.clear();
        TestUtil.addArrayElementsToCollection(match, 1, 4);
        Assert.assertNull(Array.minCover(input, match));

        // // no cover (multiple elements, multiple not in input)
        match.clear();
        TestUtil.addArrayElementsToCollection(match, 2, 4, 5);
        Assert.assertNull(Array.minCover(input, match));
    }

    /**
     * Tests for the optimality of minCover - i.e. it finds the smallest cover as
     * specified by its i, j values.
     */
    @Test
    public void testMinCoverOptimality() {
        // optimal at beginning
        Integer[] input = new Integer[]{1, 2, 3, 1, 1, 1, 2, 2, 3};
        Set<Integer> match = new HashSet<Integer>();
        TestUtil.addArrayElementsToCollection(match, 1, 2, 3);
        Pair<Integer> expected = new Pair<Integer>(0, 3);
        Assert.assertEquals(expected, Array.minCover(input, match));

        // optimal in middle
        input = new Integer[]{1, 1, 1, 2, 2, 3, 3, 1, 2, 2, 3, 2, 2, 1, 1, 2, 2, 3};
        match.clear();
        TestUtil.addArrayElementsToCollection(match, 1, 2, 3);
        expected = new Pair<Integer>(6, 9);
        Assert.assertEquals(expected, Array.minCover(input, match));

        // optimal at end
        input = new Integer[]{1, 1, 1, 2, 2, 3, 3, 3, 3, 1, 2};
        match.clear();
        TestUtil.addArrayElementsToCollection(match, 1, 2, 3);
        expected = new Pair<Integer>(8, 11);
        Assert.assertEquals(expected, Array.minCover(input, match));
    }

    /**
     * Tests for {@link com.killeent.Array.Array#permute(Object[])}.
     */

    /**
     * Tests that permute doesn't crash on an empty array.
     */
    @Test
    public void testPermuteEmptyArray() {
        Array.permute(new Integer[0]);
    }

    /**
     * Tests that permute doesn't modify a single element array.
     */
    @Test
    public void testPermuteSingleElementArray() {
        Integer[] arr = new Integer[]{0};
        Array.permute(arr);
        Assert.assertEquals(0, (int)arr[0]);
    }

    /**
     * Tests that permute doesn't change the elements in a multi-element array.
     */
    @Test
    public void testPermuteMultiElementArray() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5};
        Array.permute(arr);
        TestUtil.assertArrayContains(arr, 1, 2, 3, 4, 5);
    }

    /**
     * todo: Tests for {@link com.killeent.Array.Array#permutations(Object[])}.
     */

    /**
     * todo: Tests for {@link com.killeent.Array.Array#subset(Object[], int)}.
     */

}
