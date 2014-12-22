package com.killeent.Array;

import java.util.Comparator;
import java.util.Random;

/**
 * Various searching and sorting algorithms for arrays.
 *
 * @author Trevor Killeen (2014)
 */
public class Array {

    /**
     * Sorts the passed array using the quick sort algorithm.
     *
     * @param array The array to sort.
     * @param comparator The comparator to use when sorting.
     */
    public static <T> void quickSort(T[] array, Comparator<? super T> comparator) {
        quickSort(array, comparator, 0, array.length);
    }

    /**
     * Sorts the elements of the array from lo to hi-1.
     *
     * @param array The array to sort.
     * @param comparator The comparator to use when sorting.
     * @param lo The low (inclusive) index of the array range to sort.
     * @param hi The high (exclusive) index of the array range to sort.
     */
    private static <T> void quickSort(
            T[] array, Comparator<? super T> comparator, int lo, int hi) {
       if (hi - lo > 1) {
           int pivot = genPivot(array, comparator, lo, hi);
           int partition = partition(array, comparator, pivot, lo, hi);
           quickSort(array, comparator, lo, partition);
           quickSort(array, comparator, partition + 1, hi);
       }
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
        // array: [lo ... index - 1 <= partition | index ... hi - 2 - countBigger unknown
        // | hi - 1 - countBigger ... hi - 1 > partition | hi - 1 the partition value]
        while (index != hi - 1 - countBigger) {
            if (comparator.compare(array[index], partitionVal) <= 0) {
                // value is lower than partition
                index++;
            } else {
                // value is higher than partition
                countBigger++;
                swap(array, index, hi - 1 - countBigger);
            }
        }
        // place partition in middle
        swap(array, index, hi - 1);
        return index;
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

    /**
     * Searches for the kth smallest element in the array. Assumes that the array has unique
     * elements - i.e. for every index i, j such that i != j implies that A[i] != A[j].
     *
     * For example, given array A [6, 1, 4, 3, 5] and k = 2, we return 3.
     *
     * @param array The array to search through. No duplicates.
     * @param comparator Comparator to use when searching.
     * @param k Specifies the k value for the kth smallest element.
     * @throws IllegalArgumentException if k > the number of elements in the array, or if k <= 0.
     * @return The kth smallest element in the array.
     */
    @SuppressWarnings("unchecked")  // we are simply making a copy of an array with the same type
    public static <T> T kthSmallestElement(T[] array, Comparator<? super T> comparator, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be a positive integer");
        }
        if (k > array.length) {
            throw new IllegalArgumentException("k greater than the number of elements in the array");
        }

        // make a copy of the array so we don't mutate the input
        T[] copy = copyArray(array);

        Random r = new Random();
        return kthSmallestElement(copy, comparator, r, k, 0, array.length);
    }

    /**
     * Searches for the kth smallest element in the array A[lo ... hi-1]. Assumes that the array
     * has unique elements - i.e. for every index i, j such that i != j implies that A[i] != A[j].
     *
     * For example, given array A [6, 1, 4, 3, 5, 8, 9, 7], lo = 1, hi = 4 and k = 2 we return 3.
     *
     * @param array The array to search through. No duplicates.
     * @param comparator Comparator to use when searching.
     * @param r Random generator for generating partition values.
     * @param k Specifies the k value for the kth smallest element.
     * @param lo The low (inclusive) index of the array range to search.
     * @param hi The high (exclusive) index of the array range to search.
     * @return The kth smallest element in the array.
     */
    private static <T> T kthSmallestElement(
            T[] array, Comparator<? super T> comparator, Random r, int k, int lo, int hi) {
        int pivotIndex = lo + r.nextInt(hi - lo);
        int partitionIndex = partition(array, comparator, pivotIndex, lo, hi);

        // calculate the number of elements up to and including the partition. These elements
        // are less than or equal to the partition in value
        int countLessEqual = partitionIndex - lo + 1;

        if (countLessEqual < k) {
            // the number of elements lower than the partition plus the partition is less than
            // k. So now we search the upper half of the array for the k - (countLessEqual)th
            // smallest element
            return kthSmallestElement(array, comparator, r, k - countLessEqual, partitionIndex + 1, hi);
        } else if (countLessEqual == k) {
            return array[partitionIndex];
        } else {
            // the number of elements lower than the partition plus the partition is greater than
            // k. So we now search the lower half of the array for the kth smallest element
            return kthSmallestElement(array, comparator, r, k, lo, partitionIndex);
        }
    }

    /**
     * Utility function to create a new array that has a copy the contents of another.
     */
    @SuppressWarnings("unchecked") // we are simply making a copy of an array with a defined type
    private static <T> T[] copyArray(T[] src) {
        T[] dst = (T[]) new Object[src.length];
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i];
        }
        return dst;
    }

    /**
     * Counts the number of inversions in the array. An inversion is a pair of
     * indices i, j with i != j and A[j] < A[i].
     *
     * @param array The array to search through.
     * @param comparator Comparator to use to compare elements.
     * @return The number of inversions in the given array.
     */
    public static <T> int inversions(T[] array, Comparator<? super T> comparator) {
        T[] copy = copyArray(array);
        return inversions(copy, comparator, 0, array.length);
    }

    /**
     * Recursive helper function to count inversions while merge sorting an array.
     *
     * @param array The array to sort and count inversions in.
     * @param comparator Comparator to use to compare elements.
     * @param lo The low (inclusive) index of the array range to consider.
     * @param hi The high (exclusive) index of the array range to consider.
     * @return The number of inversions in array[lo ... hi-1]
     */
    @SuppressWarnings("unchecked") // making a copy of a generic array of the same type
    private static <T> int inversions(T[] array, Comparator<? super T> comparator, int lo, int hi) {
        if (lo >= hi - 1) {
            return 0;
        } else {
            int mid = (lo + hi) / 2;
            int inversions =
                    inversions(array, comparator, lo, mid) + inversions(array, comparator, mid, hi);

            T[] aux = (T[]) new Object[hi - lo];
            int i = lo;
            int j = mid;
            int k = 0;

            // merge the arrays, counting inversions as we go
            while (i < mid && j < hi) {
                if (comparator.compare(array[i], array[j]) <= 0) {
                    // not an inversion, simply copy it to the auxiliary array
                    aux[k] = array[i];
                    i++;
                } else {
                    // an inversion because array[i] > array[j]. Copy array[j] to the
                    // aux array and increment the inversion count by 1
                    aux[k] = array[j];
                    j++;
                    inversions++;
                }
                k++;
            }

            // any remaining elements in array[lo ... mid-1] are greater than all elements
            // in array[mid ... hi - 1], so for each element copy it to the aux array and
            // increment inversions by hi - mid. Note that we consider the element at i++ -
            // element i has already been compared to elements in array[mid ... hi - 1]
            if (i < mid) {
                aux[k] = array[i];
                i++;
                k++;
            }

            while (i < mid) {
                aux[k] = array[i];
                i++;
                k++;
                inversions += hi - mid;
            }

            // otherwise just copy over the elements in the upper half
            while (j < hi) {
                aux[k] = array[j];
                j++;
                k++;
            }

            // now copy back the elements into the original array, and return
            for (int l = 0; l < hi - lo; l++) {
                array[lo + l] = aux[l];
            }
            return inversions;
        }
    }
}