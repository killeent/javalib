package com.killeent;

import junit.framework.Assert;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Various utility functions and classes for testing purposes.
 *
 * @author Trevor Killeen (2014)
 */
public class TestUtil {

    /**
     * Simple integer comparator.
     */
    public static class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    /**
     * Asserts that the elements in two lists are equal.
     */
    public static <T> void assertListEquals(List<T> expected, List<T> actual) {
        Assert.assertEquals("mismatched list sizes", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    public static <T> void addArrayElementsToCollection(Collection<T> collection, T... arr) {
        for (int i = 0; i < arr.length; i++) {
            collection.add(arr[i]);
        }
    }

}
