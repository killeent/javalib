package com.killeent;

import com.killeent.Graph.SimpleLabeledGraph;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Tests for {@link com.killeent.Graph.SimpleLabeledGraph} ADT that should hold for any implementation.
 *
 * @author Trevor Killeen (2014)
 */
public abstract class BaseSimpleLabeledGraphTest {

    protected SimpleLabeledGraph<Integer, Integer> instance;

    protected abstract SimpleLabeledGraph<Integer, Integer> createInstance();

    @Before
    public void setUp() {
        instance = createInstance();
    }

    /**
     * Tests for {@link com.killeent.Graph.SimpleLabeledGraph#addVertex},
     * {@link com.killeent.Graph.SimpleLabeledGraph#containsVertex}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to add a
     * null vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphAddNullVertex() {
        instance.addVertex(null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to check for a
     * null vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphContainsNullVertex() {
        instance.addVertex(null);
    }

    /**
     * Tests adding and checking for single vertex to the Graph.
     */
    @Test
    public void testLabeledGraphAddSingleVertex() {
        instance.addVertex(1);
        Assert.assertTrue(instance.containsVertex(1));
        instance.clear();
    }

    /**
     * Tests checking for a vertex not in the Graph.
     */
    @Test
    public void testLabeledGraphContainsMissingVertex() {
        Assert.assertFalse(instance.containsVertex(1));
    }

    /**
     * Tests adding and checking for multiple vertices in the Graph.
     */
    @Test
    public void testLabeledGraphAddMultipleVertices() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        Assert.assertTrue(instance.containsVertex(1));
        Assert.assertTrue(instance.containsVertex(2));
        Assert.assertTrue(instance.containsVertex(3));
        instance.clear();
    }

    /**
     * Illegal argument tests for {@link com.killeent.Graph.SimpleLabeledGraph#addEdge},
     * {@link com.killeent.Graph.SimpleLabeledGraph#containsEdge},
     * {@link com.killeent.Graph.SimpleLabeledGraph#removeEdge} and
     * {@link com.killeent.Graph.SimpleLabeledGraph#neighbors}.
     *
     * See implementation tests for actual tests of these functions with valid values.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to add an edge
     * from null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphAddEdgeNullA() {
        instance.addEdge(null, 1, 0);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to add an edge
     * to null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphAddEdgeNullB() {
        instance.addEdge(null, 1, 0);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to add an edge
     * from a missing vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphAddEdgeMissingA() {
        instance.addVertex(2);
        instance.addEdge(1, 2, 0);
        instance.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to add an edge
     * to a missing vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphAddEdgeMissingB() {
        instance.addVertex(1);
        instance.addEdge(1, 2, 0);
        instance.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to add a
     * self edge.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphAddSelfEdge() {
        instance.addVertex(1);
        instance.addEdge(1, 1, 0);
        instance.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to check for an edge
     * from null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphContainsEdgeNullA() {
        instance.containsEdge(null, 1, 0);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to check for an edge
     * to null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphContainsEdgeNullB() {
        instance.containsEdge(1, null, 0);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to remove an edge
     * from null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphRemoveEdgeNullA() {
        instance.removeEdge(null, 1, 0);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to remove an edge
     * to null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphRemoveEdgeNullB() {
        instance.removeEdge(null, 1, 0);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to remove an edge
     * from a missing vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphRemoveEdgeMissingA() {
        instance.addVertex(2);
        instance.removeEdge(1, 2, 0);
        instance.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to remove an edge
     * to a missing vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphRemoveEdgeMissingB() {
        instance.addVertex(1);
        instance.removeEdge(1, 2, 0);
        instance.clear();
    }

    /**
     * Tests that removing an nonexistent edge returns false.
     */
    @Test
    public void testLabeledGraphRemoveNonexistentEdge() {
        instance.addVertex(1);
        instance.addVertex(2);
        Assert.assertFalse(instance.removeEdge(1, 2, 0));
        instance.clear();
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to get the neighbors
     * of a null vertex.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphNeighborsNullVertex() {
        instance.neighbors(null);
    }

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when trying to get the neighbors
     * of a vertex not in the graph.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLabeledGraphNeighborsMissingVertex() {
        instance.neighbors(1);
    }

    /**
     * Tests for {@link com.killeent.Graph.SimpleLabeledGraph#vertices}.
     */

    /**
     * Tests that an empty graph returns an empty vertex set.
     */
    @Test
    public void testLabeledGraphEmptyVertexSet() {
        Assert.assertTrue(instance.vertices().isEmpty());
    }

    /**
     * Tests adding multiple vertices in the Graph and making sure they
     * are in the vertex set.
     */
    @Test
    public void testLabeledGraphMultiElementVertexSet() {
        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        Set<Integer> vertices = instance.vertices();
        Assert.assertTrue(vertices.contains(1));
        Assert.assertTrue(vertices.contains(2));
        Assert.assertTrue(vertices.contains(3));
        instance.clear();
    }

}
