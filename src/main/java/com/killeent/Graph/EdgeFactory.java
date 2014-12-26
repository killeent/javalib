package com.killeent.Graph;

/**
 * Factory class for creating special edge types.
 *
 * @author Trevor Killeen (2014)
 */
public class EdgeFactory {

    /**
     * Generates an unweighted edge value - that is an edge value that is
     * equal to every other edge value.
     *
     * @return An object representing an unweighted edge.
     */
    public static <E> Comparable<E> unweightedEdgeValue() {
        return new Comparable<E>() {

            @Override
            public int compareTo(E o) {
                return 0;
            }
        };
    }

}
