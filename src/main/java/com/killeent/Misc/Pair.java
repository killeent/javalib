package com.killeent.Misc;

/**
 * A tuple of two objects.
 *
 * @author Trevor Killeen (2015)
 */
public class Pair<T> {

    private T first;
    private T second;

    /**
     * Constructs a new pair (null, null).
     */
    public Pair() {
    }

    /**
     * Constructs a new triple with the specified elements.
     */
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (! (obj instanceof Pair<?>)) {
            return false;
        }
        Pair<?> other = (Pair<?>) obj;
        return (first == other.first || (first != null && first.equals(other.first))) &&
                (second == other.second || (second != null && second.equals(other.second)));
    }

}

