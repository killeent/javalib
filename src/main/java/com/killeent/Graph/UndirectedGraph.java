package com.killeent.Graph;

/**
 * Represents an undirected graph, i.e. a graph where edges have no direction (can be
 * traversed in both directions).
 *
 * @author Trevor Killeen (2014)
 */
public interface UndirectedGraph<V extends Comparable<V>, E extends Comparable<E>> extends SimpleLabeledGraph<V,E> {
}
