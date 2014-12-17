package com.killeent.PriorityQueue;

/**
 * A Priority Queue is ADT where each element has an associated priority. Elements are removed
 * from the queue in order of highest priority to lowest priority.
 *
 * @author Trevor Killeen (2014)
 */
public interface PriorityQueue<T> {

    /**
     * Adds the specified element to the queue.
     *
     * @param element The element to add.
     */
    public void add(T element);

    /**
     * Returns (but does not remove) the element with the highest priority from the queue, if
     * one exists.
     *
     * @return null if the queue is empty. Otherwise, returns the element with the highest priority
     * in the queue.
     */
    public T peek();

    /**
     * Removes and returns the element with the highest priority from the queue, if
     * one exists.
     *
     * @return null if the queue is empty. Otherwise, returns the element with the highest priority
     * in the queue.
     */
    public T remove();

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements in the queue.
     */
    public int size();

    /**
     * Convenience method to check if the queue is empty.
     *
     * @return true if the queue is empty, otherwise false.
     */
    public boolean isEmpty();

}
