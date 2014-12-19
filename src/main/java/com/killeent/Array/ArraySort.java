package com.killeent.Array;

import java.util.Comparator;

/**
 * Various sorting algorithms for arrays.
 *
 * @author Trevor Killeen (2014)
 */
public class ArraySort {

    /**
     * Sorts the passed array using the quick sort algorithm.
     *
     * @param array The array to sort.
     * @param comparator The comparator to use when sorting.
     */
    public static <T> void quickSort(T[] array, Comparator<? super T> comparator) {

    }

    /**
     * Partitions the array such that values less than or equal to partition come before
     * partition in the array and values greater than partition come after partition
     * in the array.
     *
     * @param array The array to partition.
     * @param comparator Comparator for comparing elements.
     * @param partition The index of the partition value.
     * @param lo The low (inclusive) index of the array range to consider.
     * @param hi The high (exclusive) index of the array range to consider.
     * @return The index where the partition value is stored.
     */
    private static <T> int partition(
            T[] array, Comparator<? super T> comparator, int partition, int lo, int hi) {
        T partitionVal = array[partition];
        // initially swap the partition value to the end of the region
        swap(array, partition, hi - 1);

        int index = lo;
        int countBigger = 0;
        while (index != hi - 2 - countBigger) {
            if (comparator.compare(array[lo], partitionVal) <= 0) {
                // value is lower than partition
                index++;
            } else {
                // value is higher than partition
                swap(array, lo, hi - );
                countBigger++;
            }
        }
    }

    /**
     * Determines the median of the first, middle and last elements of the array and returns
     * the index of that median.
     *
     * @param array The array to search for.
     * @param comparator Comparator for comparing elements.
     * @param lo The low (inclusive) index of the array range to consider.
     * @param hi The high (exclusive) index of the array range to consider.
     * @return The index of the median.
     */
    private static <T> int genPivot(T[] array, Comparator<? super T> comparator, int lo, int hi) {
        int mid = hi - ((hi - lo) / 2);
        T vlo = array[lo];
        T vmid = array[mid];
        T vhi = array[hi - 1];

        // find the median
        if (comparator.compare(vlo, vmid) <= 0) {
            // vlo <= vmid
            if (comparator.compare(vmid, vhi) <= 0) {
                // vlo <= vmd <= vhi
                return mid;
            } else {
                // vlo <= vmid ; vhi < vmid
                if (comparator.compare(vlo, vhi) <= 0) {
                    // vlo <= vhi <= vmid
                    return hi - 1;
                } else {
                    // vhi < vlo <= vmid
                    return lo;
                }
            }
        } else {
            // vmid < vlo
            if (comparator.compare(vhi, vmid) <= 0) {
                // vhi <= vmid < vlo
                return mid;
            } else {
                // vmid < vlo ; vmid < vhi
                if (comparator.compare(vlo, vhi) <= 0) {
                    // vmid < vlo <= vhi
                    return lo;
                } else {
                    // vmid < vhi <= vlo
                    return hi - 1;
                }
            }
        }
    }

    /**
     * Helper function to swap the elements of an array.
     */
    private static <T> void swap(T[] array, int a, int b) {
        T temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

}
