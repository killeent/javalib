package com.killeent.Graph;

/**
 * Interface that specifies a directed graph, i.e. ones where edges are directed from
 * one vertex to another.
 *
 * @author Trevor Killeen (2014)
 */
public interface DirectedGraph<V extends Comparable<V>, E extends Comparable<E>> extends SimpleLabeledGraph<V,E> {
}
