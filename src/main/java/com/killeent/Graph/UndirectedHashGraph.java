package com.killeent.Graph;

import java.util.*;

/**
 * A simple, undirected graph G = (V, E) that has no self-loops or multi-edges.
 *
 * @author Trevor Killeen (2014)
 */
public class UndirectedHashGraph<V extends Comparable<V>, E extends Comparable<E>> implements UndirectedGraph<V,E> {

    // We implement the graph using a Hash Table and adjacency list. Every vertex in the
    // graph is unique and maps to a list of edges, which represent edges from that vertex
    // to other nodes in the Graph.

    private final Map<V, List<Edge<V, E>>> graph;

    public UndirectedHashGraph() {
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
    public Set<V> vertices() {
        return Collections.unmodifiableSet(graph.keySet());
    }

    @Override
    public void addEdge(V vertexA, V vertexB, E edge) {
        if (vertexA == null || vertexB == null || edge == null) {
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
        Edge<V, E> candidate = new Edge<V, E>(vertexA, vertexB, edge);
        if (!edges.contains(candidate)) {
            edges.add(candidate);
            graph.get(vertexB).add(new Edge<V, E>(vertexB, vertexA, edge));
        }
    }

    @Override
    public boolean containsEdge(V vertexA, V vertexB, E edge) {
        if (vertexA == null || vertexB == null || edge == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA) || !graph.containsKey(vertexB)) {
            return false;
        }
        return graph.get(vertexA).contains(new Edge<V, E>(vertexA, vertexB, edge));
    }

    @Override
    public boolean removeEdge(V vertexA, V vertexB, E edge) {
        if (vertexA == null || vertexB == null || edge == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA)) {
            throw new IllegalArgumentException("vertexA not in the graph");
        }
        if (!graph.containsKey(vertexB)) {
            throw new IllegalArgumentException("vertexB not in the graph");
        }
        return graph.get(vertexA).remove(new Edge<V, E>(vertexA, vertexB, edge)) &&
                graph.get(vertexB).remove(new Edge<V, E>(vertexB, vertexA, edge));
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
    public void clear() {
        graph.clear();
    }
}
