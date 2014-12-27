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
     * @throws java.lang.IllegalArgumentException if any passed parameters are null.
     * @throws java.lang.IllegalArgumentException if start or end isn't in the graph.
     * @return True if start and end are connected, otherwise false.
     */
    public static <V extends Comparable<V>, E extends Comparable<E>> boolean shortestPath(
            SimpleLabeledGraph<V,E> g, V start, V end, List<Edge<V, E>> path) {
        if (g == null || start == null || end == null || path == null) {
            throw new IllegalArgumentException("null arguments to shortest path");
        }
        if (!g.containsVertex(start) || !g.containsVertex(end)) {
            throw new IllegalArgumentException("vertex missing from graph");
        }

        Queue<V> queue = new LinkedList<V>();
        Map<V, Edge<V,E>> discovered = new HashMap<V, Edge<V,E>>();
        queue.add(start);
        discovered.put(start, null);

        // perform DFS to find the shortest path
        while (!queue.isEmpty()) {
            V candidate = queue.remove();

            // if we have found the end, build up the list of edges we took
            // to get here
            if (candidate.equals(end)) {
                Edge<V, E> temp = discovered.get(candidate);
                while (temp != null) {
                    path.add(0, temp);
                    temp = discovered.get(temp.getSource());
                }
                return true;
            }

            // otherwise, queue up all outgoing edges to non-discovered vertices
            for (Edge<V,E> neighbor : g.neighbors(candidate)) {
                if (!discovered.containsKey(neighbor.getDestination())) {
                    discovered.put(neighbor.getDestination(), neighbor);
                    queue.add(neighbor.getDestination());
                }
            }
        }

        // we didn't find the destination
        return false;
    }

    /**
     * Leverages DFS to detect the presence of a cycle in an undirected graph, if
     * one exists.
     *
     * @param g The graph to search.
     * @param start Start node to begin our search at.
     * @throws java.lang.IllegalArgumentException if g or start is null.
     * @throws java.lang.IllegalArgumentException if start is not in g.
     * @return true if the graph contains a cycle, otherwise false.
     */
    public static <V extends Comparable<V>, E extends Comparable<E>> boolean containsCycle(
            UndirectedGraph<V,E> g, V start) {
        if (g == null || start == null) {
            throw new IllegalArgumentException("null arguments to contains cycle");
        }
        if (!g.containsVertex(start)) {
            throw new IllegalArgumentException("vertex missing from graph");
        }

        Map<V,V> discovered = new HashMap<V,V>();   // nodes we have seen and their parents
        discovered.put(start, null);

        return containsCycle(g, start, discovered);
    }

    /**
     * Recursive helper function to detect the presence of a cycle in an undirected graph.
     *
     * @param g The graph to search.
     * @param candidate node to search at.
     * @param discovered Set of nodes that we have discovered along with their parents.
     * @return true if the graph contains a cycle, otherwise false.
     */
    private static <V extends Comparable<V>, E extends Comparable<E>> boolean containsCycle(
            UndirectedGraph<V,E> g, V candidate, Map<V,V> discovered) {
        for (Edge<V,E> neighbor : g.neighbors(candidate)) {
            V dst = neighbor.getDestination();
            if (discovered.containsKey(dst) && !discovered.get(candidate).equals(dst)) {
                // this is a back edge; there is a cycle
                return true;
            } else {
                // tree edge; recurse
                discovered.put(dst, candidate);
                containsCycle(g, dst, discovered);
            }
        }
        return false;
    }

}
