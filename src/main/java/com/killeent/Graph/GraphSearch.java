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
            if (discovered.containsKey(dst) &&
                    (discovered.get(candidate) == null || !discovered.get(candidate).equals(dst))) {
                // this is a back edge; there is a cycle
                return true;
            } else if (!discovered.containsKey(dst)) {
                // tree edge; recurse
                discovered.put(dst, candidate);
                containsCycle(g, dst, discovered);
            }
        }
        return false;
    }

    /**
     * Leverages DFS to find all of the articulation vertices in an undirected graph, if any.
     * An articulation vertex is a vertex whose removal disconnects the graph.
     *
     * @param g The graph to search.
     * @param start Start node to begin our search at.
     * @throws java.lang.IllegalArgumentException if g is null, or start is null.
     * @throws java.lang.IllegalArgumentException if g does not contain start.
     * @return An unmodifiable set of articulation vertices. In particular, if the graph has no
     * such vertices, then this set is empty.
     */
    public static <V extends Comparable<V>, E extends Comparable<E>> Set<V> articulationVertices(
            UndirectedGraph<V,E> g, V start) {
        if (g == null || start == null) {
            throw new IllegalArgumentException("null arguments to contains cycle");
        }
        if (!g.containsVertex(start)) {
            throw new IllegalArgumentException("vertex missing from graph");
        }

        // a vertex v's dfs number is the order in which it was explored in the dfs algorithm; the
        // first vertex explored has dfs number = 1, the second vertex explored = 2, etc.
        Map<V, Integer> dfsNumber = new HashMap<V, Integer>();

        // A vertex v's low value is the lowest dfs number of any vertex in the dfs subtree rooted
        // at vertex (including v) or connected to a vertex in that subtree by a back edge
        Map<V, Integer> low = new HashMap<V, Integer>();

        // A vertex v's tree out degree is the number of edges connected to v in the tree
        // formed by DFS
        Map<V, Integer> degree = new HashMap<V, Integer>();

        // Vertices we have seen and their parent
        Map<V,V> discovered = new HashMap<V, V>();
        discovered.put(start, null);

        Set<V> result = new HashSet<V>();
        articulationVertices(g, start, dfsNumber, low, degree, discovered, result);
        return Collections.unmodifiableSet(result);
    }

    /**
     * Recursive helper function to calculate the articulation vertices in an undirected
     * graph using DFS.
     *
     * @param g The graph to search.
     * @param candidate The current node to evaluate.
     * @param dfsNumber Keeps track of the DFS ordering of nodes.
     * @param low Keeps track of the lowest node in the DFS tree reachable from the subtree of the
     *            candidate.
     * @param degree Keeps track of the the degree of every node.
     * @param discovered Keeps track of discovered nodes and their parent.
     * @param result Stores the articulation vertices in the graph.
     */
    private static <V extends Comparable<V>, E extends Comparable<E>> void articulationVertices(
            UndirectedGraph<V,E> g, V candidate, Map<V,Integer> dfsNumber, Map<V,Integer> low,
            Map<V, Integer> degree, Map<V,V> discovered, Set<V> result) {
        // initialize dfs number to the number of discovered vertices
        dfsNumber.put(candidate, discovered.size());

        // initialize low to be the candidate's dfs number
        low.put(candidate, dfsNumber.get(candidate));

        // initialize DFS degree to be 0
        degree.put(candidate, 0);

        for (Edge<V,E> neighbor : g.neighbors(candidate)) {
            V dst = neighbor.getDestination();
            if (!discovered.containsKey(dst)) {
                // tree edge

                // mark dst as discovered, set candidate as parent
                discovered.put(dst, candidate);

                // increment the candidate's DFS degree count
                degree.put(candidate, degree.get(candidate) + 1);

                // recurse
                articulationVertices(g, dst, dfsNumber, low, degree, discovered, result);

                // update low value if the child links to something above us in the DFS tree
                if (low.get(dst) < low.get(candidate)) {
                    low.put(candidate, low.get(dst));
                }
            } else if (discovered.containsKey(dst) &&
                    (discovered.get(candidate) == null || !discovered.get(candidate).equals(dst))) {
                // back edge; update low value
                if (dfsNumber.get(dst) < low.get(candidate)) {
                    low.put(candidate, dfsNumber.get(dst));
                }
            }
        }

        // We have now processed the candidate and can determine if it is an articulation vertex
        // or not
        V parent = discovered.get(candidate);
        int dfs = dfsNumber.get(candidate);
        int deg = degree.get(candidate);
        int lw = low.get(candidate);

        // First, if current node is the root of the tree, we check to see if it has degree > 1;
        // if so, it is an articulation vertex because removing it disconnects its 2+ children
        if (parent == null) {
            if (deg > 1) {
                result.add(candidate);
            }
            return;
        }

        // If the earliest reachable vertex of the candidate is the candidate, then deleting the
        // edge (candidate, parent(candidate)) disconnects the graph. This is because no edge in
        // the graph represented by the DFS subtree rooted at the candidate is connected to
        // an ancestor of the candidate.
        //
        // The parent is an articulation vertex if it is not the root, and the candidate is an
        // articulation vertex if it is not a leaf
        if (lw == dfs) {
            if (discovered.get(parent) != null) {
                // parent is not the root, so it is an articulation vertex
                result.add(parent);
            }
            if (deg > 0) {
                // candidate is not a leaf, so it is an articulation vertex
                result.add(candidate);
            }
        }

        // If the earliest reachable vertex of the candidate is the parent of the candidate, then
        // the parent of candidate must be an articulation vertex unless it is the root.
        //
        // Note that the candidate cannot be an articulation vertex, as it is connected to the
        // parent through one of the nodes in the graph represented by the DFS subtree rooted at
        // candidate
        if (lw == dfsNumber.get(parent)) {
            // low[candidate] = dfs of candidate's parent
            if (discovered.get(parent) != null) {
                result.add(parent);
            }
        }
    }

}
