package com.killeent.Queue;

/**
 * ArrayQueue is a circular array based implementation of the {@link com.killeent.Queue.Queue}
 * ADT.
 *
 * @author Trevor Killeen (2014)
 */
public class ArrayQueue<T> implements Queue<T> {

    public static final int DEFAULT_INITIAL_SIZE = 16;

    private T[] queue;  // array representing the queue
    private int start;  // the index of the first element in the array
    private int free;   // the index of the next free element in the array

    /**
     * Constructs an ArrayQueue.
     */
    @SuppressWarnings("unchecked")  // initializing a generic array
    public ArrayQueue() {
        queue = (T[]) new Object[DEFAULT_INITIAL_SIZE];
        start = 0;
        free = 0;
    }

    @Override
    public void enqueue(T data) {
        queue[free] = data;
        free = (free + 1) % queue.length;
        if (free == start) {
            resize();
        }
    }

    /**
     * Helper function to resize the queue.
     */
    @SuppressWarnings("unchecked") // initializing a generic array
    private void resize() {
        T[] copy = (T[]) new Object[queue.length * 2];
        int src = start;
        int dst = 0;
        do {
            copy[dst] = queue[src];
            dst++;
            src = (src + 1) % queue.length;
        } while (src != free);
        queue = copy;
    }

    @Override
    public T remove() {
        if (start == free) {
            return null;
        }
        T res = queue[start];
        start = (start + 1) % queue.length;
        return res;
    }

    @Override
    public T peek() {
        if (start == free) {
            return null;
        }
        return queue[start];
    }

    @Override
    public int size() {
        return (free > start) ? free - start : free + (queue.length - start);
    }
}
