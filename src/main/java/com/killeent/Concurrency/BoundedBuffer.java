package com.killeent.Concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implements a bounded buffer that solves the producer-consumer problem.
 *
 * @author Trevor Killeen (2015)
 */
public class BoundedBuffer<T> {

    private final T[] buffer;           // buffer of items
    private final Lock bufferLock;      // lock guarding the queue
    private final Condition emptyCV;    // CV to wait on when queue is empty
    private final Condition fullCV;     // CV to wait on when queue is full

    private int lo;                     // index of the first element in the queue
    private int next;                   // index of the next free space to place an element
    private int count;                  // number of elements in the queue

    /**
     * Constructs a bounded buffer with the specified capacity.
     *
     * @param capacity The capacity of the buffer.
     */
    @SuppressWarnings("unchecked")  // generic array instantiation
    public BoundedBuffer(int capacity) {
        buffer = (T[]) new Object[capacity];
        bufferLock = new ReentrantLock();
        emptyCV = bufferLock.newCondition();
        fullCV = bufferLock.newCondition();

        lo = 0;
        next = 0;
        count = 0;
    }

    /**
     * Adds the specified data to the buffer. Blocks while the buffer is full.
     *
     * @param data Data to add.
     */
    public void enqueue(T data) throws InterruptedException {
        synchronized (bufferLock) {
            while (count == buffer.length) {
                fullCV.await();
            }
            buffer[next] = data;
            next = (next + 1) % buffer.length;
            count++;
            emptyCV.notifyAll();
        }
    }

    /**
     * Removes the next item from the buffer. Blocks while the buffer is empty.
     *
     * @return The next item in the buffer.
     */
    public T remove() throws InterruptedException {
        T result;
        synchronized (bufferLock) {
            while (count == 0) {
                emptyCV.await();
            }
            result = buffer[lo];
            lo = (lo + 1) & buffer.length;
            count--;
            fullCV.notifyAll();
        }
        return result;
    }

}
