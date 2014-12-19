package com.killeent.Array;

import java.util.Comparator;

/**
 * Various search algorithms for arrays.
 *
 * @author Trevor Killeen (2014)
 */
public class ArraySearch {

    /**
     * Searches for the specified value in the array using binary search. Assumes that the array
     * is sorted. If the comparator's ordering is inconsistent with equals, this function
     * may behave strangely.
     *
     * @param array The array to search through. Should be sorted.
     * @param comparator Comparator to use when searching.
     * @param value The value to search for.
     * @return The index where the specified value is in the array, or -1 if the value
     * is not in the array.
     */
    public static <T> int binarySearch(T[] array, Comparator<? super T> comparator, T value) {
        return binarySearch(array, comparator, value, 0, array.length - 1);
    }

    /**
     * Support function to perform binary search on an index range of an array.
     *
     * @param array The array to search through. Should be sorted.
     * @param comparator Comparator to use when searching.
     * @param value The value to search for.
     * @param lo The low (inclusive) index of the array range to search.
     * @param hi The high (inclusive) index of the array range to search.
     * @return The index where the specified value is in the array[lo ... hi], or -1 if the value
     * is not in the array[lo ... hi].
     */
    private static <T> int binarySearch(
            T[] array, Comparator<? super T> comparator, T value, int lo, int hi) {
        while (hi - lo >= 0) {
            int mid = hi - ((hi - lo) / 2);
            int relation = comparator.compare(array[mid], value);
            if (relation < 0) {
                // a[mid] < value, search upper half of array
                lo = mid + 1;
            } else if (relation == 0) {
                // a[mid] == value, return index
                return mid;
            } else {
                // a[mid] > value, search lower half of array
                hi = mid - 1;
            }
        }
        // not found
        return -1;
    }

    /**
     * Searches for the first occurrence of the specified value in the array. Assumes that the
     * array is sorted. If the comparator's ordering is inconsistent with equals, this function
     * may behave strangely.
     *
     * @param array The array to search through. Should be sorted.
     * @param comparator Comparator to use when searching.
     * @return The first index where of specified value in the array, or -1 if the value
     * is not in the array.
     */
    public static <T> int firstOccurrence(T[] array, Comparator<? super T> comparator, T value) {
        int index = -1;
        int searchResult = 0;
        int hi = array.length - 1;
        while ((searchResult = binarySearch(array, comparator, value, 0, hi)) != -1) {
            // desired value found in index returned by binary search
            index = searchResult;
            // continue binary searching arr[0 ... index - 1]
            hi = searchResult - 1;
        }
        return index;
    }

}
