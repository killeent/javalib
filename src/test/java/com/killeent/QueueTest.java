package com.killeent;

import com.killeent.Queue.ArrayQueue;
import com.killeent.Queue.Queue;
import org.junit.Before;

/**
 * Tests for implementations of {@link com.killeent.Queue.Queue}.
 *
 * @author Trevor Killeen (2014).
 */
public class QueueTest {

    private Queue<Integer> queue;

    @Before
    public void setUp() {
        queue = new ArrayQueue<Integer>();
    }

}
