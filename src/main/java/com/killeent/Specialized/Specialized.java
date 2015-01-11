package com.killeent.Specialized;

import java.util.*;

/**
 * A class full of specialized algorithms that don't generalize as well as things in
 * e.g. {@link com.killeent.Array.Array} but rather have single use cases.
 *
 * @author Trevor Killeen (2015)
 */
public class Specialized {

    /**
     * Computes some subset of indices s of T = {0 ... values.length - 1} such that
     * abs(sum(values[i] for i in s) - sum (values[i] for in in T - s) is minimized.
     *
     * @param values An array of values to partition.
     * @throws java.lang.IllegalArgumentException if values is null.
     * @return A set of indices that conform to the above definition.
     */
    public static Set<Integer> minimalDivision(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException();
        }

        // maps valid sums to the subsets that yield those sums
        Map<Integer, Set<Integer>> sums = new HashMap<Integer, Set<Integer>>();

        // the empty set yields a sum of 0
        sums.put(0, new HashSet<Integer>());

        // keeps track of the sum of all the elements
        int sum = 0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i];

            // for each index, calculate and place the sum/set of that including that
            // index with every existing sum->subset match
            Set<Integer> existing = sums.keySet();

            for (Integer j : existing) {
                if (sums.containsKey(values[i] + j)) {
                    // do nothing, as we already have found a subset that yields this
                    // value; we save space by not storing a duplicates
                } else {
                    // we found a new value; map it to the sums
                    Set<Integer> union = new HashSet<Integer>(sums.get(j));
                    union.add(i);
                    sums.put(values[i] + j, union);
                }
            }
        }

        // we want to find the subset such that the overall sum - the sum of the subset
        // is closest to the sum/2

        int target = sum / 2;
        int minDistance = Integer.MAX_VALUE;
        int closest = -1;

        for (Integer i : sums.keySet()) {
            int distance = Math.abs(sum - i - target);
            if (distance <= minDistance) {
                minDistance = distance;
                closest = i;
            }
        }

        return sums.get(closest);
    }

}
