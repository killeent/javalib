package com.killeent;

import com.killeent.PriorityQueue.Heap;
import com.killeent.PriorityQueue.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

/**
 * A simple test class for the binary heap {@link com.killeent.PriorityQueue.Heap}.
 *
 * @author Trevor Killeen (2014)
 *
 */
public class HeapTest {

    // Note: This class uses Strings for testing; The heap should function
    // properly for any generic value with a properly implemented comparator

    // tests for an empty heap

    // tests that an empty heap is considered empty
    @Test
    public void testEmptyHeapIsEmpty() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        Assert.assertTrue(heap.isEmpty());
    }

    // tests that we return null when trying to peek the highest priority element
    // from the heap
    @Test
    public void testEmptyHeapPeek() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        Assert.assertNull(heap.peek());
    }

    // tests that we return null when trying to remove the highest priority element
    // from the heap
    @Test
    public void testEmptyHeapRemove() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        Assert.assertNull(heap.remove());
    }

    // test a simple addition and deletion on an initially empty heap
    @Test
    public void testEmptyHeapAdditionDeletion() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        heap.add("test");
        Assert.assertEquals(1, heap.size());
        Assert.assertEquals("test", heap.peek());
        Assert.assertEquals("test", heap.remove());
        Assert.assertTrue(heap.isEmpty());
    }

    // tests additions and deletions on a heap containing 1-2 nodes

    // tests adding a higher priority element into a heap containing one element
    @Test
    public void testSimpleLowerPriorityAddition() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        heap.add("b");
        heap.add("a");
        Assert.assertEquals("a", heap.peek());
    }

    // tests adding a higher priority element into a heap containing one element
    @Test
    public void testSimpleHigherPriorityAddition() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        heap.add("a");
        heap.add("b");
        Assert.assertEquals("a", heap.peek());
    }

    // test deleting an element from a heap containing two elements
    @Test
    public void testSimpleLowerPriorityDeletion() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        heap.add("a");
        heap.add("b");
        Assert.assertEquals("a", heap.remove());
        Assert.assertEquals("b", heap.peek());
    }

    // more complex addition and deletions

    // tests a series of additions. Includes additions with 0 upward bubble swaps,
    // multiple upward bubble swaps, and adding a maximum priority value
    @Test
    public void testComplexAdditionPattern() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        addValues(heap, "c", "e", "f", "h", "l", "n", "q");
        Assert.assertEquals("c", heap.peek());

        // add value with 0 bubble ups
        heap.add("s");
        Assert.assertEquals("c", heap.peek());

        // add a value with a single percolation
        heap.add("d");
        Assert.assertEquals("c", heap.peek());

        // add a value that percolates all the way up
        heap.add("a");
        Assert.assertEquals("a", heap.peek());

        // check that heap removal is as expected
        assertExpectedHeapRemoval(heap, new String[]{"a", "c", "d", "e", "f", "h", "l", "n", "q", "s"});
    }

    // tests a series of deletions, and looks at the final array representation
    // to see if they were handled properly. Includes deletions with single and
    // multiple bubble downs
    @Test
    public void testComplexDeletionPattern() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        addValues(heap, "c", "f", "n", "j", "r", "s", "z");
        Assert.assertEquals("c", heap.peek());

        // bubbles down all the way
        heap.remove();
        Assert.assertEquals("f", heap.peek());

        clearHeap(heap);
        addValues(heap, "f", "h", "n", "j", "r", "z", "w");
        Assert.assertEquals("f", heap.peek());

        // bubbles down once, but not all the way
        heap.remove();
        Assert.assertEquals("h", heap.peek());

        heap.add("x");

        // bubbles down to the correct sub child
        heap.remove();
        Assert.assertEquals("j", heap.peek());

        // check that heap removal is as expected
        assertExpectedHeapRemoval(heap, new String[]{"j", "n", "r", "w", "x", "z"});
    }

    // tests a series of interleaved additions and deletions
    @Test
    public void testComplexAdditionDeletionPattern() {
        PriorityQueue<String> heap = new Heap<String>(new StringComparator());
        addValues(heap, "a", "e", "f", "l", "o", "p", "x", "z");
        Assert.assertEquals("a", heap.peek());

        heap.add("b");
        Assert.assertEquals("a", heap.peek());

        heap.add("y");
        Assert.assertEquals("a", heap.peek());

        heap.remove();
        Assert.assertEquals("b", heap.peek());

        heap.remove();
        Assert.assertEquals("e", heap.peek());

        heap.remove();
        Assert.assertEquals("f", heap.peek());

        heap.add("c");
        Assert.assertEquals("c", heap.peek());

        assertExpectedHeapRemoval(heap, new String[]{"c", "f", "l", "o", "p", "x", "y", "z"});
    }

    // tests the "build heap" Heap constructor
    @Test
    public void testBuildHeap() {
        String[] toBeHeaped = new String[]{"a", "f", "g", "d", "e", "r"};
        String[] expected = new String[]{"a", "d", "e", "f", "g", "r"};
        PriorityQueue<String> heap = new Heap<String>(new StringComparator(), toBeHeaped);
        assertExpectedHeapRemoval(heap, expected);
    }

    private class StringComparator implements Comparator<String> {

        /**
         * Compares two Strings lexicographically.
         *
         * @param s1 the first String
         * @param s2 the second String
         * @return Returns a positive value if s1 comes before s2 lexicographically,
         * a negative value if s2 comes before s1 lexicographically or 0 if the two
         * strings are equal
         */
        @Override
        public int compare(String s1, String s2) {
            // compare character by character, checking each time to see if one comes before
            // the other in the alphabet
            for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
                int diff = s2.charAt(i) - s1.charAt(i);
                if (diff != 0)
                    return diff;
            }

            // if the two strings have the same characters up to the length of one of the strings,
            // the shorter string comes first lexicographically
            return s1.length() - s2.length();
        }
    }

    // helper method to add a range of values into a priority queue
    private void addValues(PriorityQueue<String> heap, String... values) {
        for (int i = 0; i < values.length; i++) {
            heap.add(values[i]);
        }
    }

    // helper method to check that the sequence of values removed is equal
    // to the sequence provided
    private void assertExpectedHeapRemoval(PriorityQueue<String> heap, String[] expected) {
        int i = 0;
        while (!heap.isEmpty()) {
            if (i == expected.length) {
                Assert.fail("Too many elements in heap");
            }
            Assert.assertEquals(expected[i], heap.remove());
            i++;
        }
    }

    // helper method to clear a heap
    private void clearHeap(PriorityQueue<String> heap) {
        while (!heap.isEmpty())
            heap.remove();
    }
}
