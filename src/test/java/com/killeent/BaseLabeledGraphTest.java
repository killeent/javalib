package com.killeent;

import com.killeent.Graph.LabeledGraph;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Tests for {@link com.killeent.Graph.LabeledGraph} ADT that should hold for any implementation.
 *
 * @author Trevor Killeen (2014)
 */
public abstract class BaseLabeledGraphTest {

    private LabeledGraph<Integer> instance;

    protected abstract LabeledGraph<Integer> createInstance();

    @Before
    public void setUp() {
        instance = createInstance();
    }

    /**
     * Tests for {@link com.killeent.Graph.LabeledGraph#addVertex},
     * {@link com.killeent.Graph.LabeledGraph#containsVertex}.
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
     * Tests for {@link com.killeent.Graph.LabeledGraph#vertices}.
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
