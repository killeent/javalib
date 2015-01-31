package com.killeent;

import com.killeent.Concurrency.ReadersWriterLock;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link com.killeent.Concurrency.ReadersWriterLock}.
 *
 * @author Trevor Killeen (2015)
 */
public class ReadersWriteLockTest {

    private ReadersWriterLock instance;

    @Before
    public void setup() {
        instance = new ReadersWriterLock();
    }

    /**
     * Tests for an {@link java.lang.IllegalMonitorStateException} when trying
     * to release a read lock we don't hold.
     */
    @Test(expected = IllegalMonitorStateException.class)
    public void testReleaseReadNotHeld() {
        instance.releaseRead();
    }

    /**
     * Tests for false when asking if we hold the lock for reading and we don't.
     */
    @Test
    public void testDoIHoldReadAndIDoNot() {
        Assert.assertFalse(instance.doIHoldReadLock());
    }

    /**
     * Tests acquiring the lock for reading, checking that we hold the lock
     * and then releasing it
     */
    @Test
    public void testSimpleRead() {
        instance.acquireRead();
        Assert.assertTrue(instance.doIHoldReadLock());
        instance.releaseRead();
    }

    /**
     * Test that read is re-entrant.
     */
    @Test
    public void testReadReentrant() {
        Assert.assertFalse(instance.doIHoldReadLock());
        instance.acquireRead();
        Assert.assertTrue(instance.doIHoldReadLock());
        instance.acquireRead();
        Assert.assertTrue(instance.doIHoldReadLock());
        instance.releaseRead();
        Assert.assertTrue(instance.doIHoldReadLock());
        instance.releaseRead();
        Assert.assertFalse(instance.doIHoldReadLock());
    }

    /**
     * Tests for an {@link java.lang.IllegalStateException} when trying to
     * acquire a write lock while holding a read lock.
     */
    @Test
    public void testAcquireWriteWhileHoldingRead() {
        try {
            instance.acquireRead();
            instance.acquireWrite();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        } finally {
            if (instance.doIHoldWriteLock()) {
                instance.releaseWrite();
            }
            if (instance.doIHoldReadLock()) {
                instance.releaseRead();
            }
        }
    }

    /**
     * Tests for an {@link java.lang.IllegalMonitorStateException} when trying
     * to release a write lock we don't hold.
     */
    @Test(expected = IllegalMonitorStateException.class)
    public void testReleaseWriteNotHeld() {
        instance.releaseWrite();
    }

    /**
     * Tests for false when asking if we hold the lock for writing and we don't.
     */
    @Test
    public void testDoIHoldWriteAndIDoNot() {
        Assert.assertFalse(instance.doIHoldWriteLock());
    }

    /**
     * Tests acquiring the lock for writing, checking that we hold the lock
     * and then releasing it
     */
    @Test
    public void testSimpleWrite() {
        instance.acquireWrite();
        Assert.assertTrue(instance.doIHoldWriteLock());
        instance.acquireWrite();
    }

    /**
     * Test that write is re-entrant.
     */
    @Test
    public void testWriteReentrant() {
        Assert.assertFalse(instance.doIHoldWriteLock());
        instance.acquireWrite();
        Assert.assertTrue(instance.doIHoldWriteLock());
        instance.acquireWrite();
        Assert.assertTrue(instance.doIHoldWriteLock());
        instance.releaseWrite();
        Assert.assertTrue(instance.doIHoldWriteLock());
        instance.releaseWrite();
        Assert.assertFalse(instance.doIHoldWriteLock());
    }

    /**
     * Tests that we can acquire the read lock when holding the write lock.
     */
    @Test
    public void testAcquireWriteThenRead() {
        Assert.assertFalse(instance.doIHoldWriteLock());
        instance.acquireWrite();
        Assert.assertTrue(instance.doIHoldWriteLock());
        instance.acquireRead();
        Assert.assertTrue(instance.doIHoldReadLock());
        instance.releaseRead();
        Assert.assertFalse(instance.doIHoldReadLock());
        instance.releaseWrite();
        Assert.assertFalse(instance.doIHoldWriteLock());
    }

}
