package com.killeent;

import com.killeent.Graph.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
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
    private DirectedGraph<Integer, Integer> directed;
    private List<Edge<Integer, Integer>> llInstance;

    @Before
    public void setUp() {
        graph = new DirectedHashGraph<Integer, Integer>();
        undirected = new UndirectedHashGraph<Integer, Integer>();
        directed = new DirectedHashGraph<Integer, Integer>();
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
    public void testShortestPathMissingStart() {
        graph.addVertex(2);
        Graphs.shortestPath(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a end
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathMissingEnd() {
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
     * Tests for {@link com.killeent.Graph.Graphs#djikstrasPath(
     * com.killeent.Graph.SimpleLabeledGraph, Comparable, Comparable, java.util.List)} and
     * {@link com.killeent.Graph.Graphs#shortestDjikstras(
     * com.killeent.Graph.SimpleLabeledGraph, Comparable, Comparable, java.util.List)}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDjikstrasNullGraph() {
        Graphs.djikstrasPath(null, 1, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * start vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDjikstrasNullStart() {
        Graphs.djikstrasPath(graph, null, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * end vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDjikstrasNullEnd() {
        Graphs.djikstrasPath(graph, 1, null, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * edge list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDjikstrasNullEdgeList() {
        Graphs.djikstrasPath(graph, 1, 2, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a start
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDjikstrasMissingStart() {
        graph.addVertex(2);
        Graphs.djikstrasPath(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a end
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDjikstrasMissingEnd() {
        graph.addVertex(1);
        Graphs.djikstrasPath(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestDjikstrasNullGraph() {
        Graphs.shortestDjikstras(null, 1, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * start vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestDjikstrasNullStart() {
        Graphs.shortestDjikstras(graph, null, 2, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * end vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestDjikstrasNullEnd() {
        Graphs.shortestDjikstras(graph, 1, null, llInstance);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a null
     * edge list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestDjikstrasNullEdgeList() {
        Graphs.shortestDjikstras(graph, 1, 2, null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a start
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestDjikstrasMissingStart() {
        graph.addVertex(2);
        Graphs.shortestDjikstras(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if passing in a end
     * vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testShortestDjikstrasMissingEnd() {
        graph.addVertex(1);
        Graphs.shortestDjikstras(graph, 1, 2, llInstance);
        graph.clear();
    }

    /**
     * Tests finding the shortest path to self.
     */
    @Test
    public void testDjikstrasToSelf() {
        graph.addVertex(1);
        Assert.assertTrue(Graphs.djikstrasPath(graph, 1, 1, llInstance));
        Assert.assertTrue(llInstance.isEmpty());
        llInstance.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(graph, 1, 1, llInstance));
        Assert.assertTrue(llInstance.isEmpty());
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two unconnected nodes returns false.
     */
    @Test
    public void testDjikstrasUnconnectedNodes() {
        graph.addVertex(1);
        graph.addVertex(2);
        Assert.assertFalse(Graphs.djikstrasPath(graph, 1, 2, llInstance));
        llInstance.clear();

        Assert.assertFalse(Graphs.shortestDjikstras(graph, 1, 2, llInstance));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by a single edge.
     */
    @Test
    public void testDjikstrasSingleEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 1);
        Assert.assertTrue(Graphs.djikstrasPath(graph, 1, 2, llInstance));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        llInstance.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(graph, 1, 2, llInstance));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by multiple edges.
     */
    @Test
    public void testDjikstrasMultipleEdges() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 2);
        Assert.assertTrue(Graphs.djikstrasPath(graph, 1, 3, llInstance));
        Assert.assertEquals(2, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 2)));
        llInstance.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(graph, 1, 3, llInstance));
        Assert.assertEquals(2, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 2)));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests finding the shortest path between two nodes separated by two unequal paths.
     */
    @Test
    public void testDjikstrasMultiplePaths() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 4, 2);
        graph.addEdge(1, 3, 2);
        graph.addEdge(3, 4, 2);
        Assert.assertTrue(Graphs.djikstrasPath(graph, 1, 4, llInstance));
        Assert.assertEquals(2, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 4, 2)));
        llInstance.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(graph, 1, 4, llInstance));
        Assert.assertEquals(2, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 4, 2)));
        graph.clear();
        llInstance.clear();
    }

    /**
     * Tests that Djikstra's can still find the shortest path in the presence
     * of a cycle.
     */
    @Test
    public void testDjikstrasGraphWithCycles() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 2, 2);
        graph.addEdge(4, 5, 1);
        graph.addEdge(5, 6, 2);
        Assert.assertTrue(Graphs.djikstrasPath(graph, 1, 6, llInstance));
        Assert.assertEquals(5, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 2)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(3, 4, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(4, 5, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(5, 6, 2)));
        llInstance.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(graph, 1, 6, llInstance));
        Assert.assertEquals(5, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 2, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(2, 3, 2)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(3, 4, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(4, 5, 1)));
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(5, 6, 2)));
        llInstance.clear();
    }

    /**
     * Tests Djikstra's with a more complex graph.
     *
     *  (s) --13--> (b) <---------2-------(j)
     *    \2         | \__4___            ^^
     *    v          1        v          / |
     *   (a)-----1---|------->(e)       2  |
     *    \          v          \       |  |
     *     \        (d)          \     /   |
     *     5         |            2   /    |
     *     |         3             \ /     |
     *     |   ______|__1__>(f)----|-      |
     *     |  /      |             |       |
     *     v /       v             v       |
     *    (c)---4-->(g)----5----->(h)------6
     */
    @Test
    public void testDjikstrasAlgorithmComplex() {
        DirectedGraph<Character, Integer> g = new DirectedHashGraph<Character, Integer>();
        List<Edge<Character, Integer>> list = new LinkedList<Edge<Character, Integer>>();

        g.addVertex('s');
        g.addVertex('b');
        g.addVertex('j');
        g.addVertex('a');
        g.addVertex('e');
        g.addVertex('d');
        g.addVertex('f');
        g.addVertex('c');
        g.addVertex('g');
        g.addVertex('h');

        g.addEdge('s', 'b', 13);
        g.addEdge('s', 'a', 2);
        g.addEdge('b', 'd', 1);
        g.addEdge('b', 'e', 4);
        g.addEdge('j', 'b', 2);
        g.addEdge('a', 'e', 1);
        g.addEdge('a', 'c', 5);
        g.addEdge('e', 'h', 2);
        g.addEdge('d', 'g', 1);
        g.addEdge('f', 'j', 2);
        g.addEdge('c', 'g', 4);
        g.addEdge('c', 'f', 1);
        g.addEdge('g', 'h', 5);
        g.addEdge('h', 'j', 6);

        // s to a
        List<Edge<Character, Integer>> expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'a', list));
        Assert.assertEquals(1, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'a', list));
        Assert.assertEquals(1, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();


        // s to c
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'c', 5));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'c', list));
        Assert.assertEquals(2, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'c', list));
        Assert.assertEquals(2, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        // s to e
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'e', 1));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'e', list));
        Assert.assertEquals(2, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'e', list));
        Assert.assertEquals(2, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        // s to g
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'c', 5),
                new Edge<Character, Integer>('c', 'g', 4));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'g', list));
        Assert.assertEquals(3, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'g', list));
        Assert.assertEquals(3, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        // s to f
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'c', 5),
                new Edge<Character, Integer>('c', 'f', 1));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'f', list));
        Assert.assertEquals(3, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'f', list));
        Assert.assertEquals(3, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        // s to h
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'e', 1),
                new Edge<Character, Integer>('e', 'h', 2));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'h', list));
        Assert.assertEquals(3, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'h', list));
        Assert.assertEquals(3, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        // s to j
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'c', 5),
                new Edge<Character, Integer>('c', 'f', 1),
                new Edge<Character, Integer>('f', 'j', 2));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'j', list));
        Assert.assertEquals(4, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'j', list));
        Assert.assertEquals(4, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        // s to b
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'c', 5),
                new Edge<Character, Integer>('c', 'f', 1),
                new Edge<Character, Integer>('f', 'j', 2),
                new Edge<Character, Integer>('j', 'b', 2));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'b', list));
        Assert.assertEquals(5, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'b', list));
        Assert.assertEquals(5, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        // s to d
        expected = Arrays.asList(
                new Edge<Character, Integer>('s', 'a', 2),
                new Edge<Character, Integer>('a', 'c', 5),
                new Edge<Character, Integer>('c', 'f', 1),
                new Edge<Character, Integer>('f', 'j', 2),
                new Edge<Character, Integer>('j', 'b', 2),
                new Edge<Character, Integer>('b', 'd', 1));

        Assert.assertTrue(Graphs.djikstrasPath(g, 's', 'd', list));
        Assert.assertEquals(6, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();

        Assert.assertTrue(Graphs.shortestDjikstras(g, 's', 'd', list));
        Assert.assertEquals(6, list.size());
        TestUtil.assertListEquals(expected, list);
        list.clear();
    }

    /**
     * Tests for finding the min length path when two path costs are equals for
     * shortestDjikstra's.
     */
    @Test
    public void testShortestDjikstasSameCost() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        
        graph.addEdge(1, 3, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);

        Assert.assertTrue(Graphs.shortestDjikstras(graph, 1, 3, llInstance));
        Assert.assertEquals(1, llInstance.size());
        Assert.assertTrue(llInstance.contains(new Edge<Integer, Integer>(1, 3, 2)));
    }

    /**
     * Tests for {@link com.killeent.Graph.Graphs#containsCycle}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when passing a null graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsCycleNullGraph() {
        Graphs.<Integer,Integer>containsCycle(null, 1);
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
        Graphs.<Integer, Integer>articulationVertices(null, 1);
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

    /**
     * Tests for {@link com.killeent.Graph.Graphs#topologicalSort}.
     */

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when passing in a
     * null graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTopologicalSortNullGraph() {
        Graphs.<Integer,Integer>topologicalSort(null);
    }

    /**
     * Tests topologically sorting an empty graph.
     */
    @Test
    public void testTopologicalSortEmptyGraph() {
        Graphs.topologicalSort(directed);
    }

    /**
     * Tests topologically sorting a single element graph.
     */
    @Test
    public void testTopologicalSortSingleElementGraph() {
        directed.addVertex(1);
        List<Integer> result = Graphs.topologicalSort(directed);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, (int)result.get(0));
        directed.clear();
    }

    /**
     * Tests topologically sorting a list of three vertices.
     */
    @Test
    public void testTopologicalSortThreeElementList() {
        directed.addVertex(1);
        directed.addVertex(2);
        directed.addVertex(3);
        directed.addEdge(1, 2, 0);
        directed.addEdge(2, 3, 0);
        List<Integer> result = Graphs.topologicalSort(directed);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(1, (int)result.get(0));
        Assert.assertEquals(2, (int)result.get(1));
        Assert.assertEquals(3, (int)result.get(2));
        directed.clear();
    }

    /**
     * Tests topological sort for a more complicated DAG:
     *
     *          (7)     (5)      (3)
     *           |  \    /        /|
     *           |   (11)        / |
     *           |__ /_ |\ ____(8)  |
     *              /   | \___ / _  |
     *           (2)   (9)____/   \ |
     *                            (10)
     *
     * (All edges are directed downwards from higher node to lower node).
     *
     * We test that we have generated a topological sort by asserting all
     * the precedence constraints hold.
     */
    @Test
    public void testTopologicalSortComplexGraph() {
        directed.addVertex(7);
        directed.addVertex(5);
        directed.addVertex(3);
        directed.addVertex(11);
        directed.addVertex(8);
        directed.addVertex(2);
        directed.addVertex(9);
        directed.addVertex(10);

        directed.addEdge(7, 8, 0);
        directed.addEdge(7, 11, 0);
        directed.addEdge(5, 11, 0);
        directed.addEdge(3, 8, 0);
        directed.addEdge(3, 10, 0);
        directed.addEdge(11, 2, 0);
        directed.addEdge(11, 9, 0);
        directed.addEdge(11, 10, 0);
        directed.addEdge(8, 9, 0);

        List<Integer> sorted = Graphs.topologicalSort(directed);
        Assert.assertTrue(sorted.indexOf(7) < sorted.indexOf(8));
        Assert.assertTrue(sorted.indexOf(7) < sorted.indexOf(11));
        Assert.assertTrue(sorted.indexOf(5) < sorted.indexOf(11));
        Assert.assertTrue(sorted.indexOf(3) < sorted.indexOf(8));
        Assert.assertTrue(sorted.indexOf(3) < sorted.indexOf(10));
        Assert.assertTrue(sorted.indexOf(11) < sorted.indexOf(2));
        Assert.assertTrue(sorted.indexOf(11) < sorted.indexOf(9));
        Assert.assertTrue(sorted.indexOf(11) < sorted.indexOf(10));
        Assert.assertTrue(sorted.indexOf(8) < sorted.indexOf(9));

        directed.clear();
    }

    /**
     * todo: add tests for {@link com.killeent.Graph.Graphs#allPairsShortestPaths}.
     */
}
