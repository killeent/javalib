package com.killeent;

import com.killeent.Graph.Edge;
import com.killeent.Graph.SimpleLabeledGraph;
import com.killeent.Graph.DirectedGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Objects;

/**
 * Tests for the {@link com.killeent.Graph.DirectedGraph}.
 *
 * @author Trevor Killeen (2014)
 */
public class DirectedGraphTest extends BaseSimpleLabeledGraphTest {
    @Override
    protected SimpleLabeledGraph<Integer, Integer> createInstance() {
        return new DirectedGraph<Integer, Integer>();
    }

    /**
     * Tests specific to the {@link com.killeent.Graph.DirectedGraph}.
     */

    /**
     * Tests for {@link com.killeent.Graph.DirectedGraph#addEdge},
     * {@link com.killeent.Graph.DirectedGraph#containsEdge}
     * and {@link DirectedGraphTest#removeEdge}.
     */

    /**
     * Tests adding a single edge in the graph.
     */
    @Test
    public void testDirectedGraphAddSingleEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertFalse(instance.containsEdge(2, 1, 0));
        instance.clear();
    }

    /**
     * Tests adding an edge in both directions to the graph.
     */
    @Test
    public void testDirectedGraphAddBidirectionalEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        instance.addEdge(2, 1, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertTrue(instance.containsEdge(2, 1, 0));
        instance.clear();
    }

    /**
     * Tests adding multiple outgoing edges to the graph.
     */
    @Test
    public void testDirectedGraphAddMultipleOutEdges() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addEdge(1, 2, 0);
        instance.addEdge(1, 3, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertTrue(instance.containsEdge(1, 3, 0));
        instance.clear();
    }

    /**
     * Tests adding multiple ingoing edges to the graph.
     */
    @Test
    public void testDirectedGraphAddMultipleInEdges() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addEdge(1, 3, 0);
        instance.addEdge(2, 3, 0);
        Assert.assertTrue(instance.containsEdge(1, 3, 0));
        Assert.assertTrue(instance.containsEdge(2, 3, 0));
        instance.clear();
    }

    /**
     * Tests removing a single edge.
     */
    @Test
    public void testDirectedGraphRemoveEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertTrue(instance.removeEdge(1, 2, 0));
        Assert.assertFalse(instance.containsEdge(2, 1, 0));
        instance.clear();
    }

    /**
     * Tests that removing an edge in one direction does not remove an edge in both.
     */
    @Test
    public void testDirectedGraphRemoveOneDirectionOfEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        instance.addEdge(2, 1, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertTrue(instance.containsEdge(2, 1, 0));
        Assert.assertTrue(instance.removeEdge(1, 2, 0));
        Assert.assertTrue(instance.containsEdge(2, 1, 0));
        Assert.assertFalse(instance.containsEdge(1, 2, 0));
        instance.clear();
    }

    /**
     * Tests for {@link com.killeent.Graph.DirectedGraph#neighbors}
     */

    /**
     * Tests for an empty neighbors set.
     */
    @Test
    public void testDirectedGraphNoNeighbors() {
        instance.addVertex(1);
        Assert.assertTrue(instance.neighbors(1).isEmpty());
    }

    /**
     * Tests for a non-empty neighbors set.
     */
    @Test
    public void testDirectedGraphNeighbors() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addEdge(1, 2, 0);
        instance.addEdge(1, 3, 0);
        Collection<Edge<Integer, Integer>> neighbors = instance.neighbors(1);
        Assert.assertEquals(2, neighbors.size());
        Assert.assertTrue(neighbors.contains(new Edge<Integer, Object>(1, 2, 0)));
        Assert.assertTrue(neighbors.contains(new Edge<Integer, Object>(1, 3, 0)));
        instance.clear();
    }

}
