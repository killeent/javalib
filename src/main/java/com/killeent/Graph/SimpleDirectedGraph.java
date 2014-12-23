package com.killeent.Graph;

import java.util.*;

/**
 * A simple, directed graph is an weighted, directed graph G = (V, E) that has
 * no self-loops or multi-edges.
 *
 * @author Trevor Killeen (2014)
 */
public class SimpleDirectedGraph<V extends Comparable<V>> implements LabeledGraph<V> {

    // We implement the graph using a Hash Table and adjacency list. Every vertex in the
    // graph is unique and maps to a list of edges, which represent edges from that vertex
    // to other nodes in the Graph.

    private final Map<V, List<Edge<V>>> graph;

    public SimpleDirectedGraph() {
        graph = new HashMap<V, List<Edge<V>>>();
    }

    @Override
    public void addVertex(V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("vertex cannot be null");
        }
        if (!graph.containsKey(vertex)) {
            graph.put(vertex, new LinkedList<Edge<V>>());
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
    public void addEdge(V vertexA, V vertexB) {
        if (vertexA == null || vertexB == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA)) {
            throw new IllegalArgumentException("vertexA not in the graph");
        }
        if (!graph.containsKey(vertexB)) {
            throw new IllegalArgumentException("vertexB not in the graph");
        }
        Collection<Edge<V>> edges = graph.get(vertexA);
        Edge<V> candidate = new Edge<V>(vertexA, vertexB);
        if (!edges.contains(candidate)) {
            edges.add(candidate);
        }
    }

    @Override
    public boolean containsEdge(V vertexA, V vertexB) {
        if (vertexA == null || vertexB == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA) || !graph.containsKey(vertexB)) {
            return false;
        }
        return graph.get(vertexA).contains(new Edge<V>(vertexA, vertexB));
    }

    @Override
    public boolean removeEdge(V vertexA, V vertexB) {
        if (vertexA == null || vertexB == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!graph.containsKey(vertexA)) {
            throw new IllegalArgumentException("vertexA not in the graph");
        }
        if (!graph.containsKey(vertexB)) {
            throw new IllegalArgumentException("vertexB not in the graph");
        }
        return graph.get(vertexA).remove(new Edge<V>(vertexA, vertexB));
    }

    @Override
    public Collection<Edge<V>> neighbors(V vertex) {
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
