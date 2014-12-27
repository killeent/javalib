package com.killeent;

import com.killeent.Queue.ArrayQueue;
import com.killeent.Queue.Queue;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for implementations of {@link com.killeent.Queue.Queue}.
 *
 * @author Trevor Killeen (2014).
 */
public class QueueTest {

    private Queue<Integer> instance;

    @Before
    public void setUp() {
        instance = new ArrayQueue<Integer>();
    }

    /**
     * Tests that we get an {@link java.lang.IllegalArgumentException} when trying
     * to add null data to the instance.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testQueueEnqueueNull() {
        instance.enqueue(null);
    }

    /**
     * Tests that we get null when removing from an empty queue.
     */
    @Test
    public void testQueueRemoveWhenEmpty() {
        Assert.assertNull(instance.remove());
    }

    /**
     * Tests that we get null when peeking an empty queue.
     */
    @Test
    public void testQueuePeekWhenEmpty() {
        Assert.assertNull(instance.peek());
    }

    /**
     * Tests that we get a size of 0 when querying an empty queue.
     */
    @Test
    public void testQueueSizeWhenEmpty() {
        Assert.assertEquals(0, instance.size());
    }

    /**
     * Tests simple enqueue, peek and remove of a single element.
     */
    @Test
    public void testQueueOpsSingleElement() {
        instance.enqueue(1);
        Assert.assertEquals(1, instance.size());
        Assert.assertEquals(1, instance.peek().intValue());
        Assert.assertEquals(1, instance.size());
        Assert.assertEquals(1, instance.remove().intValue());
        Assert.assertEquals(0, instance.size());
    }

    /**
     * Tests simple enqueue, peek and remove of multiple elements.
     */
    @Test
    public void testQueueOpsMultipleElements() {
        instance.enqueue(1);
        instance.enqueue(2);
        Assert.assertEquals(2, instance.size());
        Assert.assertEquals(1, instance.peek().intValue());
        Assert.assertEquals(2, instance.size());
        Assert.assertEquals(1, instance.remove().intValue());
        Assert.assertEquals(1, instance.size());
        Assert.assertEquals(2, instance.peek().intValue());
        Assert.assertEquals(1, instance.size());
        Assert.assertEquals(2, instance.remove().intValue());
        Assert.assertEquals(0, instance.size());
    }

}
