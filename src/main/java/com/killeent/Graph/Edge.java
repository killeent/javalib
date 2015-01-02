package com.killeent.Graph;

/**
 * Represents an immutable edge in a Graph.
 *
 * @author Trevor Killeen (2014)
 */
public class Edge<V extends Comparable<V>, E extends Comparable<E>> {

    private final V source;
    private final V destination;
    private final E value;

    /**
     * Creates a new edge.
     *
     * @param source The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     * @param value The value of the edge.
     * @throws java.lang.IllegalArgumentException if any of the parameters are null.
     */
    public Edge(V source, V destination, E value) {
        if (source == null || destination == null || value == null) {
            throw new IllegalArgumentException("Edge cannot be created with null values");
        }
        this.source = source;
        this.destination = destination;
        this.value = value;
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
     * @return The value for this vertex
     */
    public E getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Edge))
            return false;
        Edge<?,?> other = (Edge<?,?>) o;
        return this.getDestination().equals(other.getDestination())
                && this.getSource().equals(other.getSource())
                && this.getValue().equals(other.getValue());
    }

    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode();
    }
}
