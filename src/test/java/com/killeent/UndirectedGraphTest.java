package com.killeent;

import com.killeent.Graph.Edge;
import com.killeent.Graph.SimpleLabeledGraph;
import com.killeent.Graph.UndirectedHashGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Tests for the {@link com.killeent.Graph.UndirectedGraph};
 *
 * @author Trevor Killeen (2014)
 */
public class UndirectedGraphTest extends BaseSimpleLabeledGraphTest {

    @Override
    protected SimpleLabeledGraph<Integer, Integer> createInstance() {
        return new UndirectedHashGraph<Integer, Integer>();
    }

    /**
     * Tests specific to the {@link com.killeent.Graph.UndirectedGraph}.
     */

    /**
     * Tests for {@link com.killeent.Graph.DirectedHashGraph#addEdge},
     * {@link com.killeent.Graph.DirectedHashGraph#containsEdge}
     * and {@link DirectedGraphTest#removeEdge}.
     */

    /**
     * Tests adding a single edge in the graph.
     */
    @Test
    public void testUndirectedGraphAddSingleEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertTrue(instance.containsEdge(2, 1, 0));
        instance.clear();
    }

    /**
     * Tests adding multiple outgoing edges to the graph.
     */
    @Test
    public void testUndirectedGraphAddMultipleOutEdges() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addEdge(1, 2, 0);
        instance.addEdge(1, 3, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertTrue(instance.containsEdge(2, 1, 0));
        Assert.assertTrue(instance.containsEdge(1, 3, 0));
        Assert.assertTrue(instance.containsEdge(3, 1, 0));
        instance.clear();
    }

    /**
     * Tests removing a single edge.
     */
    @Test
    public void testUndirectedGraphRemoveEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        Assert.assertTrue(instance.containsEdge(1, 2, 0));
        Assert.assertTrue(instance.containsEdge(2, 1, 0));
        Assert.assertTrue(instance.removeEdge(1, 2, 0));
        Assert.assertFalse(instance.containsEdge(1, 2, 0));
        Assert.assertFalse(instance.containsEdge(2, 1, 0));
        instance.clear();
    }

    /**
     * Tests for {@link com.killeent.Graph.DirectedHashGraph#neighbors}
     */

    // todo: refactor first method up top

    /**
     * Tests for an empty neighbors set.
     */
    @Test
    public void testDirectedGraphNoNeighbors() {
        instance.addVertex(1);
        Assert.assertTrue(instance.neighbors(1).isEmpty());
        instance.clear();
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
        Assert.assertTrue(neighbors.contains(new Edge<Integer, Integer>(1, 2, 0)));
        Assert.assertTrue(neighbors.contains(new Edge<Integer, Integer>(1, 3, 0)));

        neighbors = instance.neighbors(2);
        Assert.assertEquals(1, neighbors.size());
        Assert.assertTrue(neighbors.contains(new Edge<Integer, Integer>(2, 1, 0)));

        neighbors = instance.neighbors(3);
        Assert.assertEquals(1, neighbors.size());
        Assert.assertTrue(neighbors.contains(new Edge<Integer, Integer>(3, 1, 0)));

        instance.clear();
    }

}
