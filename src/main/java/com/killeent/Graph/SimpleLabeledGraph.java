package com.killeent.Graph;

import java.util.Collection;
import java.util.Set;

/**
 * A Graph G = (V, E) is a set of vertices V together with a set of edges E of ordered
 * or unordered pairs of vertices from V. This interface defines a simple, labeled
 * Graph, that is one with no self or multi- edges and the vertices are unique.
 *
 * @author Trevor Killeen (2014)
 */
public interface SimpleLabeledGraph<V extends Comparable<V>, E extends Comparable<E>> {

    /**
     * Adds the specified vertex to the Graph if it does not already exist.
     *
     * @param vertex The vertex to add.
     * @throws java.lang.IllegalArgumentException if V is null.
     */
    void addVertex(V vertex);

    /**
     * Checks to see if the specified vertex is in the Graph.
     *
     * @param vertex The vertex to look for.
     * @throws java.lang.IllegalArgumentException if vertex is null.
     * @return True if the graph contains the specified vertex, otherwise false.
     */
    boolean containsVertex(V vertex);

    /**
     * @return A unmodifiable set of all the vertices in the set.
     */
    Set<V> vertices();

    /**
     * Adds an edge between vertex A and vertex B if it does not already exist.
     *
     * @param vertexA The source vertex.
     * @param vertexB The destination vertex.
     * @param edge The edge value.
     * @throws java.lang.IllegalArgumentException if vertexA or vertexB is null.
     * @throws java.lang.IllegalArgumentException is vertexA or vertexB is not in the Graph.
     * @throws java.lang.IllegalArgumentException if vertexA == vertexB or if there is
     * already an edge from A to B in the Graph.
     */
    void addEdge(V vertexA, V vertexB, E edge);

    /**
     * Checks to see if the specified vertex is in the Graph.
     *
     * @param vertexA The source vertex.
     * @param vertexB The destination vertex.
     * @param edge the edge value.
     * @throws java.lang.IllegalArgumentException if vertexA or vertexB is null.
     * @return True if there is an edge between A and B in the Graph, otherwise false.
     */
    boolean containsEdge(V vertexA, V vertexB, E edge);

    /**
     * Removes an edge between vertex A and vertex B if it exists.
     *
     * @param vertexA The source vertex.
     * @param vertexB The destination vertex.
     * @param edge the edge value.
     * @throws java.lang.IllegalArgumentException if vertexA or vertexB is null.
     * @return True if the graph contained the edge and it was removed, otherwise false if the
     * graph did not contain the edge.
     */
    boolean removeEdge(V vertexA, V vertexB, E edge);

    /**
     * Returns the set of edges connected to the specified vertex.
     *
     * @param vertex The vertex to consider.
     * @throws java.lang.NullPointerException if vertex is null.
     * @throws java.lang.IllegalArgumentException is vertex is not in the graph.
     * @return An unmodifiable collection of all edges connected to the passed vertex.
     */
    Collection<Edge<V,E>> neighbors(V vertex);

    /**
     * Removes all nodes and edges from the Graph.
     */
    void clear();
}
