package com.killeent;

import com.killeent.Graph.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Tests for {@link com.killeent.Graph.Graphs};
 *
 * @author Trevor Killeen (2014)
 */
public class GraphsTest {

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
     * Tests for {@link com.killeent.Graph.Graphs#shortestPath}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullGraph() {
        Graphs.shortestPath(null, 1, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * start vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullStart() {
        Graphs.shortestPath(graph, null, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * end vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullEnd() {
        Graphs.shortestPath(graph, 1, null, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * edge list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullEdgeList() {
        Graphs.shortestPath(graph, 1, 2, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a start
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullMissingStart() {
        graph.addVertex(2);
        Graphs.shortestPath(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a end
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathNullMissingEnd() {
        graph.addVertex(1);
        Graphs.shortestPath(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests finding the shortest path to self.
     */
    @Test
    public void testShortestPathToSelf() {
        graph.addVertex(1);
        Assert.assertTrue(Graphs.shortestPath(graph, 1, 1, llInstance));
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
        Assert.assertFalse(Graphs.shortestPath(graph, 1, 2, llInstance));
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
        Assert.assertTrue(Graphs.shortestPath(graph, 1, 2, llInstance));
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
        Assert.assertTrue(Graphs.shortestPath(graph, 1, 3, llInstance));
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
        Assert.assertTrue(Graphs.shortestPath(graph, 1, 3, llInstance));
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
        Assert.assertTrue(Graphs.shortestPath(graph, 1, 6, llInstance));
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
     * Tests for {@link com.killeent.Graph.Graphs#containsCycle}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing a null graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsCycleNullGraph() {
        Graphs.containsCycle(null, 1);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing a null vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsCycleNullVertex() {
        Graphs.containsCycle(undirected, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing in a vertex not
     * found in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsCycleMissingVertex() {
        Graphs.containsCycle(undirected, 1);
    }

    /**
     * Tests that a single element graph contains no cycles.
     */
    @Test
    public void testContainsCycleEmptyGraph() {
        undirected.addVertex(1);
        Assert.assertFalse(Graphs.containsCycle(undirected, 1));
        undirected.clear();
    }

    /**
     * Tests that a graph containing a single edge does not contain a cycle.
     */
    @Test
    public void testContainsCycleSingleEdge() {
        undirected.addVertex(1);
        undirected.addVertex(2);
        undirected.addEdge(1, 2, 0);
        Assert.assertFalse(Graphs.containsCycle(undirected, 1));
        Assert.assertFalse(Graphs.containsCycle(undirected, 2));
        undirected.clear();
    }

    /**
     * Tests that a graph representing a list does not contain a cycle.
     */
    @Test
    public void testContainsCycleListGraph() {
        undirected.addVertex(1);
        undirected.addVertex(2);
        undirected.addVertex(3);
        undirected.addEdge(1, 2, 0);
        undirected.addEdge(2, 3, 0);
        Assert.assertFalse(Graphs.containsCycle(undirected, 1));
        Assert.assertFalse(Graphs.containsCycle(undirected, 2));
        Assert.assertFalse(Graphs.containsCycle(undirected, 3));
        undirected.clear();
    }

    /**
     * Tests a graph containing a single simple cycle.
     */
    @Test
    public void testContainsCycleSimpleCycle() {
        undirected.addVertex(1);
        undirected.addVertex(2);
        undirected.addVertex(3);
        undirected.addEdge(1, 2, 0);
        undirected.addEdge(2, 3, 0);
        undirected.addEdge(3, 1, 0);
        Assert.assertTrue(Graphs.containsCycle(undirected, 1));
        Assert.assertTrue(Graphs.containsCycle(undirected, 2));
        Assert.assertTrue(Graphs.containsCycle(undirected, 3));
        undirected.clear();
    }

    /**
     * Tests for {@link com.killeent.Graph.Graphs#articulationVertices}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing a null graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testArticulationVerticesNullGraph() {
        Graphs.articulationVertices(null, 1);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing a null vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testArticulationVerticesNullVertex() {
        Graphs.articulationVertices(undirected, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing in a vertex not
     * found in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testArticulationVerticesMissingVertex() {
        Graphs.articulationVertices(undirected, 1);
    }

    /**
     * Tests that a single element graph contains no cycles.
     */
    @Test
    public void testArticulationVerticesEmptyGraph() {
        undirected.addVertex(1);
        Assert.assertTrue(Graphs.articulationVertices(undirected, 1).isEmpty());
        undirected.clear();
    }

    /**
     * Tests that a graph containing a single edge does not contain any articulation vertices.
     */
    @Test
    public void testArticulationVertexSingleEdge() {
        undirected.addVertex(1);
        undirected.addVertex(2);
        undirected.addEdge(1, 2, 0);
        Assert.assertTrue(Graphs.articulationVertices(undirected, 1).isEmpty());
        Assert.assertTrue(Graphs.articulationVertices(undirected, 2).isEmpty());
        undirected.clear();
    }

    /**
     * Tests that the graph consisting of three vertices in a line has 1 articulation vertex.
     */
    @Test
    public void testArticulationVertexThreeVertexGraph() {
        undirected.addVertex(1);
        undirected.addVertex(2);
        undirected.addVertex(3);
        undirected.addEdge(1, 2, 0);
        undirected.addEdge(2, 3, 0);
        Assert.assertTrue(Graphs.articulationVertices(undirected, 1).size() == 1);
        Assert.assertTrue(Graphs.articulationVertices(undirected, 1).contains(2));
        Assert.assertTrue(Graphs.articulationVertices(undirected, 2).size() == 1);
        Assert.assertTrue(Graphs.articulationVertices(undirected, 2).contains(2));
        Assert.assertTrue(Graphs.articulationVertices(undirected, 3).size() == 1);
        Assert.assertTrue(Graphs.articulationVertices(undirected, 3).contains(2));
        undirected.clear();
    }

    /**
     * Tests that a graph consisting of three vertices in a cycle has no articulation vertex.
     */
    @Test
    public void testArticulationVertexThreeVertexCycle() {
        undirected.addVertex(1);
        undirected.addVertex(2);
        undirected.addVertex(3);
        undirected.addEdge(1, 2, 0);
        undirected.addEdge(2, 3, 0);
        undirected.addEdge(3, 1, 0);
        Assert.assertTrue(Graphs.articulationVertices(undirected, 1).isEmpty());
        Assert.assertTrue(Graphs.articulationVertices(undirected, 2).isEmpty());
        Assert.assertTrue(Graphs.articulationVertices(undirected, 3).isEmpty());
        undirected.clear();
    }

    /**
     * More comprehensive test for articulation vertices. Consider the input graph:
     *
     *                              (1)
     *                             /   \
     *                            (2)--(10)_____
     *                           /      | |     \
     *                         _(3)_   /  (11)--(12)
     *                        /  |  \ /           |
     *                       /  (7) (8)          (13)
     *                       |   |   |
     *                       |  (6) (9)
     *                      (4)/  |
     *                        \   /
     *                         (5)
     *
     * Nodes 3, 8, 10, and 12 are articulation vertices.
     */
    @Test
    public void testArticulationVerticesComprehensive() {
        for (int i = 1; i <=13; i++) {
            undirected.addVertex(i);
        }

        // construct edges as specified
        undirected.addEdge(1, 2, 0);
        undirected.addEdge(1, 10, 0);
        undirected.addEdge(2, 3, 0);
        undirected.addEdge(3, 4, 0);
        undirected.addEdge(3, 7, 0);
        undirected.addEdge(3, 8, 0);
        undirected.addEdge(4, 5, 0);
        undirected.addEdge(4, 6, 0);
        undirected.addEdge(5, 6, 0);
        undirected.addEdge(6, 7, 0);
        undirected.addEdge(8, 9, 0);
        undirected.addEdge(8, 10, 0);
        undirected.addEdge(10, 11, 0);
        undirected.addEdge(10, 12, 0);
        undirected.addEdge(11, 12, 0);
        undirected.addEdge(12, 13, 0);

        for (int i = 1; i <= 13; i++) {
            Set<Integer> result = Graphs.articulationVertices(undirected, i);
            Assert.assertEquals(4, result.size());
            Assert.assertTrue(result.contains(3));
            Assert.assertTrue(result.contains(8));
            Assert.assertTrue(result.contains(10));
            Assert.assertTrue(result.contains(12));
        }

        undirected.clear();
    }


}
