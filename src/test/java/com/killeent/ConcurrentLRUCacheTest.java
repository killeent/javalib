package com.killeent;

import com.killeent.Concurrency.ConcurrentLRUCache;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for the {@link com.killeent.Concurrency.ConcurrentLRUCache}.
 *
 * @author Trevor Killeen (2015).
 */
public class ConcurrentLRUCacheTest {

    /**
     * Tests a simple add, get and remove of the LRU cache.
     */
    @Test
    public void testLRUCacheSingleItem() {
        ConcurrentLRUCache<Integer, Integer> cache =
                new ConcurrentLRUCache<Integer, Integer>(1);
        cache.add(1, 2);
        Assert.assertEquals(2, cache.get(1).intValue());
        Assert.assertTrue(cache.remove(1));
        Assert.assertNull(cache.get(1));
    }

    /**
     * Tests interleaved adds and removes of items from the LRU
     * Cache.
     */
    @Test
    public void testLRUCacheComplex() {
        ConcurrentLRUCache<Integer, Integer> cache =
                new ConcurrentLRUCache<Integer, Integer>(3);
        cache.add(1, 2);
        cache.add(3, 4);
        cache.add(5, 6);

        // get item at front of cache
        Assert.assertEquals(2, cache.get(1).intValue());

        // get item at middle of cache
        Assert.assertEquals(4, cache.get(3).intValue());

        // get item at end of cache
        Assert.assertEquals(6, cache.get(5).intValue());

        // remove item at beginning of cache
        Assert.assertTrue(cache.remove(5));
        cache.add(5, 6);

        // remove item in middle of cache
        Assert.assertTrue(cache.remove(3));
        cache.add(3, 4);

        // remove item at end of cache
        Assert.assertTrue(cache.remove(1));
        cache.clear();
    }

    /**
     * Tests the eviction policy is LRU.
     */
    @Test
    public void testLRUCacheEviction() {
        ConcurrentLRUCache<Integer, Integer> cache =
                new ConcurrentLRUCache<Integer, Integer>(3);
        cache.add(1, 2);
        cache.add(3, 4);
        cache.add(5, 6);

        // (1, 2) should be evicted
        cache.add(7, 8);
        Assert.assertNull(cache.get(1));

        // 'get' operation moves to the front of list. (5, 6) should
        // be evicted
        Assert.assertEquals(4, cache.get(3).intValue());
        cache.add(1, 2);
        Assert.assertNull(cache.get(5));

        // 'add' operation moves to the front of list if key
        // is already in list. (3, 4) should be removed
        cache.add(7, 9);
        cache.add(10, 12);
        Assert.assertNull(cache.get(3));
    }

}
