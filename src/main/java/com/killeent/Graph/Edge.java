package com.killeent.Graph;

/**
 * Represents an immutable edge in a Graph.
 *
 * @author Trevor Killeen
 */
public class Edge<V extends Comparable<V>, E extends Comparable<E>>  {

    private final V source;
    private final V destination;
    private final E edgeValue;

    /**
     * Creates a new edge.
     *
     * @param source The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     * @param edgeValue The value of the edge.
     */
    public Edge(V source, V destination, E edgeValue) {
        if (source == null || destination == null || edgeValue == null) {
            throw new IllegalArgumentException("Edge cannot be created with null values");
        }
        this.source = source;
        this.destination = destination;
        this.edgeValue = edgeValue;
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

    /**
     * @return The edge value for this edge.
     */
    public E getEdgeValue() {
        return edgeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Edge))
            return false;
        Edge<?,?> other = (Edge<?, ?>) o;
        return this.getDestination().equals(other.getDestination())
                && this.getEdgeValue().equals(other.getEdgeValue())
                && this.getSource().equals(other.getSource());
    }

    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode() + edgeValue.hashCode();
    }
}
