package com.killeent.Graph;

import java.util.*;

/**
 * Various Graph Search/Traversal algorithms.
 *
 * @author Trevor Killeen (2014)
 */
public class GraphSearch {

    /**
     * Performs BFS on the input graph to find a shortest path between two vertices, if
     * one exists.
     *
     * @param g The graph to search.
     * @param start The start node to consider.
     * @param end The node to find the shortest path to.
     * @param path An output parameter where the shortest path will be stored in one
     *             exists.
     * @return True if start and end are connected, otherwise false. 
     */
    public static <V extends Comparable<V>> boolean shortestPath(
            SimpleLabeledGraph<V> g, V start, V end, List<Edge<V>> path) {
        if (g == null || start == null || end == null || path == null) {
            throw new IllegalArgumentException("null arguments to shortest path");
        }
        if (!g.containsVertex(start) || !g.containsVertex(end)) {
            throw new IllegalArgumentException("vertex missing from graph");
        }

        Queue<V> queue = new LinkedList<V>();
        Map<V, Edge<V>> discovered = new HashMap<V, Edge<V>>();
        queue.add(start);
        discovered.put(start, null);

        // perform DFS to find the shortest path
        while (!queue.isEmpty()) {
            V candidate = queue.remove();

            // if we have found the end, build up the list of edges we took
            // to get here
            if (candidate.equals(end)) {
                Edge<V> temp = discovered.get(candidate);
                while (temp != null) {
                    path.add(0, temp);
                    temp = discovered.get(temp.getSource());
                }
                return true;
            }

            // otherwise, queue up all outgoing edges to non-discovered vertices
            for (Edge<V> neighbor : g.neighbors(candidate)) {
                if (!discovered.containsKey(neighbor.getDestination())) {
                    discovered.put(neighbor.getDestination(), neighbor);
                    queue.add(neighbor.getDestination());
                }
            }
        }

        // we didn't find the destination
        return false;
    }

}
