package com.killeent.Graph;

import java.util.Collection;
import java.util.Set;

/**
 * A Graph G = (V, E) is a set of vertices V together with a set of edges E of ordered
 * or unordered pairs of vertices from V.
 *
 * @author Trevor Killeen (2014)
 */
public interface Graph<V extends Comparable<V>, E extends Comparable<E>> {

    /**
     * Adds the specified vertex to the Graph.
     *
     * @param vertex The vertex to add.
     * @throws java.lang.NullPointerException if V is null.
     */
    void addVertex(V vertex);

    /**
     * Checks to see if the specified vertex is in the Graph.
     *
     * @param vertex The vertex to look for.
     * @throws java.lang.NullPointerException if vertex is null.
     * @return True if the graph contains the specified vertex, otherwise false.
     */
    boolean containsVertex(V vertex);

    /**
     * Adds an edge between vertex A and vertex B.
     *
     * @param vertexA The source vertex.
     * @param vertexB The destination vertex.
     * @param edge The edge to add.
     * @throws java.lang.NullPointerException if vertexA, vertexB or edge is null.
     * @throws java.lang.IllegalArgumentException is vertexA or vertexB is not in the Graph.
     */
    void addEdge(V vertexA, V vertexB, E edge);

    /**
     * Removes an edge between vertex A and vertex B if it exists.
     *
     * @param vertexA The source vertex.
     * @param vertexB The destination vertex.
     * @param edge The edge to remove.
     * @throws java.lang.NullPointerException if vertexA, vertexB or edge is null.
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
     * @return A collection of all edges connected to the passed vertex.
     */
    Collection<Edge<V,E>> neighbors(V vertex);

    /**
     * @return A set of all the vertices in the Graph.
     */
    Set<V> vertices();

}
