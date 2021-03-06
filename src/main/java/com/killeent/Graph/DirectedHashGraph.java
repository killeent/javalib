package com.killeent.Graph;

import java.util.*;

/**
 * A simple, directed graph  G = (V, E) that has no self-loops or multi-edges.
 *
 * @author Trevor Killeen (2014)
 */
public class DirectedHashGraph<V extends Comparable<V>, E extends Comparable<E>> implements DirectedGraph<V, E> {

    // We implement the graph using a Hash Table and adjacency list. Every vertex in the
    // graph is unique and maps to a list of edges, which represent edges from that vertex
    // to other nodes in the Graph.

    private final Map<V, List<Edge<V, E>>> graph;

    public DirectedHashGraph() {
        graph = new HashMap<V, List<Edge<V, E>>>();
    }

    @Override
    public void addVertex(V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("vertex cannot be null");
        }
        if (!graph.containsKey(vertex)) {
            graph.put(vertex, new LinkedList<Edge<V, E>>());
        }
    }

    @Override
    public boolean containsVertex(V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("vertex cannot be null");
        }
        return graph.containsKey(vertex);
    }

    @Override
    public void addEdge(V vertexA, V vertexB, E value) {
        if (vertexA == null || vertexB == null || value == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA)) {
            throw new IllegalArgumentException("vertexA not in the graph");
        }
        if (!graph.containsKey(vertexB)) {
            throw new IllegalArgumentException("vertexB not in the graph");
        }
        if (vertexA == vertexB) {
            throw new IllegalArgumentException("no self edges allowed");
        }
        Collection<Edge<V, E>> edges = graph.get(vertexA);
        Edge<V, E> candidate = new Edge<V, E>(vertexA, vertexB, value);
        if (!edges.contains(candidate)) {
            edges.add(candidate);
        }
    }

    @Override
    public boolean containsEdge(V vertexA, V vertexB, E value) {
        if (vertexA == null || vertexB == null || value == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA) || !graph.containsKey(vertexB)) {
            return false;
        }
        return graph.get(vertexA).contains(new Edge<V, E>(vertexA, vertexB, value));
    }

    @Override
    public boolean removeEdge(V vertexA, V vertexB, E value) {
        if (vertexA == null || vertexB == null || value == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA)) {
            throw new IllegalArgumentException("vertexA not in the graph");
        }
        if (!graph.containsKey(vertexB)) {
            throw new IllegalArgumentException("vertexB not in the graph");
        }
        return graph.get(vertexA).remove(new Edge<V, E>(vertexA, vertexB, value));
    }

    @Override
    public Collection<Edge<V, E>> neighbors(V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("vertex cannot be null");
        }
        if (!graph.containsKey(vertex)) {
            throw new IllegalArgumentException("vertex not in graph");
        }
        return Collections.unmodifiableCollection(graph.get(vertex));
    }

    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(graph.keySet());
    }

    @Override
    public void clear() {
        graph.clear();
    }
}
