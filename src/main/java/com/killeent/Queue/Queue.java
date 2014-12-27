package com.killeent.Queue;

/**
 * A queue is a FIFO (First-in First Out) ADT.
 *
 * @author Trevor Killeen
 */
public interface Queue<T> {

    /**
     * Adds the specified data to the end of the queue.
     *
     * @param data The data to add.
     */
    void enqueue(T data);

    /**
     * Removes and returns the data at the front of the queue.
     *
     * @return The data at the front. Returns null if the queue is empty.
     */
    T remove();

    /**
     * Returns (but does not remove) the data at the front of the queue.
     *
     * @return The data at the front. Returns null if the queue is empty.
     */
    T peek();

    /**
     * @return The number of elements in the queue.
     */
    int size();

}
