package com.killeent.Misc;

/**
 * A tuple of three objects.
 *
 * @author Trevor Killeen (2014)
 */
public class Triple<T> {

    private T first;
    private T second;
    private T third;

    /**
     * Constructs a new triple (null, null, null).
     */
    public Triple() {
    }

    /**
     * Constructs a new triple with the specified elements.
     */
    public Triple(T first, T second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
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

    public T getThird() {
        return third;
    }

    public void setThird(T third) {
        this.third = third;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode() + third.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (! (obj instanceof Triple<?>)) {
            return false;
        }
        Triple<?> other = (Triple<?>) obj;
        return (first == other.first || (first != null && first.equals(other.first))) &&
                (second == other.second || (second != null && second.equals(other.second))) &&
                        (third == other.third || (third != null && third.equals(other.third)));


    }

}
