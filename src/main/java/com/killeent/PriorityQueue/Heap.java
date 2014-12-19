package com.killeent.PriorityQueue;

import java.util.Comparator;

/**
 * A Heap is an array-based implementation of a Priority Queue. The Heap takes an explicit
 * comparator with which to order the objects.
 *
 * An element A has a priority greater than element B iff the compareTo function returns a positive
 * number.
 *
 * @author Trevor Killeen
 */
public class Heap<T> implements PriorityQueue<T> {

    private T[] heap;
    private Comparator<? super T> comparator;
    private int count;

    public static final int DEFAULT_INITIAL_CAPACITY = 32;

    /**
     * Initialize a heap that will use the specified comparator to assign
     * priority values to elements.
     *
     * @param comparator The comparator for the elements.
     */
    public Heap(Comparator<? super T> comparator) {
        this(comparator, DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Initialize a heap that will use the specified comparator to assign
     * priority values to elements, and initially have the specified size.
     *
     * @param comparator The comparator for the elements.
     * @param initialCapacity The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public Heap(Comparator<? super T> comparator, int initialCapacity) {
        this.heap = (T[]) new Object[initialCapacity];
        this.comparator = comparator;
        this.count = 0;
    }

    /**
     * Builds a heap given the specified comparator and element array. The element
     * array is used internally and should not be modified after a call to this constructor
     * except through PriorityQueue API functions.
     *
     * @param comparator The comparator for the elements.
     * @param elements The element array to heapify.
     */
    public Heap(Comparator<? super T> comparator, T[] elements) {
        this.heap = elements;
        this.comparator = comparator;
        this.count = elements.length;

        // perform repeated bubble downs to format the heap
        for(int i = count - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    @Override
    public void add(T element) {
        if (count == heap.length) {
            expandHeap();
        }
        heap[count] = element;
        bubbleUp(count);
        count++;
    }

    /**
     * Doubles the size of the current heap, copying over the old values.
     */
    @SuppressWarnings("unchecked")
    private void expandHeap() {
        T[] copy = (T[]) new Object[heap.length * 2];
        for (int i = 0; i < heap.length; i++) {
            copy[i] = heap[i];
        }
        heap = copy;
    }

    /**
     * Bubbles up the value at index, swapping it repeatedly with its parent as long
     * as it has higher priority than the parent.
     *
     * @param index The index to bubble up.
     */
    private void bubbleUp(int index) {
        int parent = (index - 1) / 2;
        while (parent >= 0 && comparator.compare(heap[parent], heap[index]) < 0) {
            T temp = heap[parent];
            heap[parent] = heap[index];
            heap[index] = temp;
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    @Override
    public T peek() {
        return count != 0 ? heap[0] : null;
    }

    @Override
    public T remove() {
        if (count == 0) {
            return null;
        }
        T result = heap[0];
        count--;
        heap[0] = heap[count];
        bubbleDown(0);
        return result;
    }

    /**
     * Bubbles down the value at index, swapping it repeatedly with its max priority child as
     * long as it has higher priority than the parent.
     *
     * @param index The index to bubble down.
     */
    private void bubbleDown(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;

        // loop before we hit a leaf
        while (leftChild < count) {
            int maxChild;

            // find the max priority child
            if (rightChild < count) {
                maxChild = comparator.compare(heap[leftChild], heap[rightChild]) > 0 ?
                        leftChild :
                        rightChild;
            } else {
                maxChild = leftChild;
            }

            if (comparator.compare(heap[maxChild], heap[index]) > 0) {
                T temp = heap[index];
                heap[index] = heap[maxChild];
                heap[maxChild] = temp;
                index = maxChild;
                leftChild = index * 2 + 1;
                rightChild = index * 2 + 2;
            } else {
                break;
            }
        }
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}
