package com.killeent.Graph;

/**
 * Represents an unweighted, immutable edge in a Graph.
 *
 * @author Trevor Killeen (2014)
 */
public class Edge<V extends Comparable<V>> {

    private final V source;
    private final V destination;

    /**
     * Creates a new edge.
     *
     * @param source The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     * @throws java.lang.IllegalArgumentException if any of the parameters are null.
     */
    public Edge(V source, V destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Edge cannot be created with null values");
        }
        this.source = source;
        this.destination = destination;
    }

    /**
     * @return The source vertex for this edge.
     */
    public V getSource() {
        return source;
    }

    /**
     * @return The destination vertex for this edge.
     */
    public V getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Edge))
            return false;
        Edge<?> other = (Edge<?>) o;
        return this.getDestination().equals(other.getDestination())
                && this.getSource().equals(other.getSource());
    }

    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode();
    }
}
