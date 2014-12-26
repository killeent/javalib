package com.killeent;

import com.killeent.Graph.DirectedGraph;
import com.killeent.Graph.Edge;
import com.killeent.Graph.GraphSearch;
import com.killeent.Graph.SimpleLabeledGraph;
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

    private SimpleLabeledGraph<Integer, Integer> instance;
    private List<Edge<Integer, Integer>> llInstance;

    @Before
    public void setUp() {
        instance = new DirectedGraph<Integer, Integer>();
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
        GraphSearch.shortestPath(instance, null, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * end vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullEnd() {
        GraphSearch.shortestPath(instance, 1, null, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * edge list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullEdgeList() {
        GraphSearch.shortestPath(instance, 1, 2, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a start
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullMissingStart() {
        instance.addVertex(2);
        GraphSearch.shortestPath(instance, 1, 2, llInstance);
        instance.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a end
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullMissingEnd() {
        instance.addVertex(1);
        GraphSearch.shortestPath(instance, 1, 2, llInstance);
        instance.clear();
    }

    /**
     * Tests finding the shortest path to self.
     */
    @Test
    public void testShortestPathToSelf() {
        instance.addVertex(1);
        Assert.assertTrue(GraphSearch.shortestPath(instance, 1, 1, llInstance));
        Assert.assertTrue(llInstance.isEmpty());
        instance.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two unconnected nodes returns false.
     */
    @Test
    public void testShortestPathUnconnectedNodes() {
        instance.addVertex(1);
        instance.addVertex(2);
        Assert.assertFalse(GraphSearch.shortestPath(instance, 1, 2, llInstance));
        instance.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by a single edge.
     */
    @Test
    public void testShortestPathSingleEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        Assert.assertTrue(GraphSearch.shortestPath(instance, 1, 2, llInstance));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 0)));
        instance.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by multiple edges.
     */
    @Test
    public void testShortestPathMultipleEdges() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addEdge(1, 2, 0);
        instance.addEdge(2, 3, 0);
        Assert.assertTrue(GraphSearch.shortestPath(instance, 1, 3, llInstance));
        Assert.assertEquals(2, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 0)));
        instance.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by multiple paths, one
     * of which is shorter.
     */
    @Test
    public void testShortestPathMultiplePaths() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addEdge(1, 2, 0);
        instance.addEdge(2, 3, 0);
        instance.addEdge(1, 3, 0);
        Assert.assertTrue(GraphSearch.shortestPath(instance, 1, 3, llInstance));
        Assert.assertEquals(1, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 3, 0)));
        instance.clear();
        llInstance.clear();
    }

    /**
     * Tests that shortest path can still find the shortest path in the presence
     * of a cycle.
     */
    @Test
    public void testShortestPathGraphWithCycles() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addVertex(4);
        instance.addVertex(5);
        instance.addVertex(6);
        instance.addEdge(1, 2, 0);
        instance.addEdge(2, 3, 0);
        instance.addEdge(3, 4, 0);
        instance.addEdge(4, 2, 0);
        instance.addEdge(4, 5, 0);
        instance.addEdge(5, 6, 0);
        Assert.assertTrue(GraphSearch.shortestPath(instance, 1, 6, llInstance));
        Assert.assertEquals(5, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(3, 4, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(4, 5, 0)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(5, 6, 0)));
        instance.clear();
        llInstance.clear();
    }
}
