package com.killeent;

import java.util.Comparator;

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

}
