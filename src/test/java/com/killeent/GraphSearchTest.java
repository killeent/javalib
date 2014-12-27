package com.killeent;

import com.killeent.Graph.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Tests for {@link com.killeent.Graph.GraphSearch};
 *
 * @author Trevor Killeen (2014)
 */
public class GraphSearchTest {

    private SimpleLabeledGraph<Integer, Integer> graph;
    private UndirectedGraph<Integer, Integer> undirected;
    private List<Edge<Integer, Integer>> llInstance;

    @Before
    public void setUp() {
        graph = new DirectedHashGraph<Integer, Integer>();
        undirected = new UndirectedHashGraph<Integer, Integer>();
        llInstance = new LinkedList<Edge<Integer, Integer>>();
    }


    /**
     * Tests for {@link com.killeent.Graph.GraphSearch#shortestPath}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullGraph() {
        GraphSearch.shortestPath(null, 1, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * start vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullStart() {
        GraphSearch.shortestPath(graph, null, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * end vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullEnd() {
        GraphSearch.shortestPath(graph, 1, null, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * edge list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullEdgeList() {
        GraphSearch.shortestPath(graph, 1, 2, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a start
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullMissingStart() {
        graph.addVertex(2);
        GraphSearch.shortestPath(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a end
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullMissingEnd() {
        graph.addVertex(1);
        GraphSearch.shortestPath(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests finding the shortest path to self.
     */
    @Test
    public void testShortestPathToSelf() {
        graph.addVertex(1);
        Assert.assertTrue(GraphSearch.shortestPath(graph, 1, 1, llInstance));
        Assert.assertTrue(llInstance.isEmpty());
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two unconnected nodes returns false.
     */
    @Test
    public void testShortestPathUnconnectedNodes() {
        graph.addVertex(1);
        graph.addVertex(2);
        Assert.assertFalse(GraphSearch.shortestPath(graph, 1, 2, llInstance));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by a single edge.
     */
    @Test
    public void testShortestPathSingleEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 0);
        Assert.assertTrue(GraphSearch.shortestPath(graph, 1, 2, llInstance));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 0)));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by multiple edges.
     */
    @Test
    public void testShortestPathMultipleEdges() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        Assert.assertTrue(GraphSearch.shortestPath(graph, 1, 3, llInstance));
        Assert.assertEquals(2, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 0)));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by multiple paths, one
     * of which is shorter.
     */
    @Test
    public void testShortestPathMultiplePaths() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(1, 3, 0);
        Assert.assertTrue(GraphSearch.shortestPath(graph, 1, 3, llInstance));
        Assert.assertEquals(1, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 3, 0)));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests that shortest path can still find the shortest path in the presence
     * of a cycle.
     */
    @Test
    public void testShortestPathGraphWithCycles() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(3, 4, 0);
        graph.addEdge(4, 2, 0);
        graph.addEdge(4, 5, 0);
        graph.addEdge(5, 6, 0);
        Assert.assertTrue(GraphSearch.shortestPath(graph, 1, 6, llInstance));
        Assert.assertEquals(5, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(3, 4, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(4, 5, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(5, 6, 0)));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests for {@link com.killeent.Graph.GraphSearch#containsCycle}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing a null graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsCycleNullGraph() {
        GraphSearch.containsCycle(null, 1);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing a null vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsCycleNullVertex() {
        GraphSearch.containsCycle(undirected, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing in a vertex not
     * found in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsCycleMissingVertex() {
        GraphSearch.containsCycle(undirected, 1);
    }

    /**
     * Tests that a single element graph contains no cycles.
     */
    @Test
    public void testContainsCycleEmptyGraph() {
        undirected.addVertex(1);
        Assert.assertFalse(GraphSearch.containsCycle(undirected, 1));
        undirected.clear();
    }
}
