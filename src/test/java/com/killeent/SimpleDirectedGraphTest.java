package com.killeent;

import com.killeent.Graph.LabeledGraph;
import com.killeent.Graph.SimpleDirectedGraph;

/**
 * Tests for the {@link com.killeent.Graph.SimpleDirectedGraph}.
 *
 * @author Trevor Killeen (2014)
 */
public class SimpleDirectedGraphTest extends BaseLabeledGraphTest {
    @Override
    protected LabeledGraph<Integer> createInstance() {
        return new SimpleDirectedGraph<Integer>();
    }

    /**
     * Tests specific to the {@link com.killeent.Graph.SimpleDirectedGraph}.
     */

    /**
     * Tests for {@link com.killeent.Graph.SimpleDirectedGraph#addEdge} and
     * {@link com.killeent.SimpleDirectedGraphTest#removeEdge}
     */
}
