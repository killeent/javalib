package com.killeent;

import com.killeent.Graph.SimpleLabeledGraph;
import com.killeent.Graph.DirectedGraph;

/**
 * Tests for the {@link com.killeent.Graph.DirectedGraph}.
 *
 * @author Trevor Killeen (2014)
 */
public class DirectedGraphTest extends BaseSimpleLabeledGraphTest {
    @Override
    protected SimpleLabeledGraph<Integer> createInstance() {
        return new DirectedGraph<Integer>();
    }

    /**
     * Tests specific to the {@link com.killeent.Graph.DirectedGraph}.
     */

    /**
     * Tests for {@link com.killeent.Graph.DirectedGraph#addEdge} and
     * {@link DirectedGraphTest#removeEdge}
     */
}
