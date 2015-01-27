package com.killeent.Concurrency;

import com.sun.javafx.beans.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A synchronized LRU (Least-Recently Used) Cache is capacity-limited cache of key-value
 * pairs with the following eviction policy:
 *
 * If we need to open space up in the cache for a new object, we evict the
 * object that was accessed furthest in the past.
 *
 * @author Trevor Killeen (2015)
 */
public class ConcurrentLRUCache<K,V> {

    // Internally, we represent the LRU Cache using a Linked Hash Map. The Linked List
    // tracks the order (in terms of LRU) of the items in the cache. For example, the
    // element at the end of the list is the LRU item.
    //
    // We use the Hash Table to be able to quickly find the position of the entry in
    // the list (so we can update its position upon access, or remove it).

    // Our Linked List is doubly-linked and composed of List Nodes
    private class ListNode {

        public ListNode prev;
        public ListNode next;
        public K key;
        public V data;

        public ListNode(ListNode prev, ListNode next, @NonNull K key, @NonNull V data) {
            this.prev = prev;
            this.next = next;
            this.key = key;
            this.data = data;
        }
    }

    // The Hash Table itself
    private final Map<K, ListNode> cache = new HashMap<K, ListNode>();

    // Capacity limits
    private final int maxCapacity;

    // Store the Linked List head and tail
    private ListNode head = null;
    private ListNode tail = null;

    /**
     * Creates a new LRU cache with the specified maximum capacity.
     *
     * @param maxCapacity Maximum capacity of objects in the cache.
     * @throws java.lang.IllegalArgumentException if maxCapacity is less than or
     * equal to zero.
     */
    public ConcurrentLRUCache(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("maxCapacity must be at least 1");
        }
        this.maxCapacity = maxCapacity;
    }

    /**
     * Adds the specified key-value pair to the cache. If the key is
     * already in the cache we simply update the value associated
     * with the key.
     *
     * @param key The key.
     * @param val The val.
     */
    public synchronized void add(@NonNull K key, @NonNull V val) {
        if (cache.size() == maxCapacity) {
            evictItem();
        }
        if (cache.containsKey(key)) {
            removeNodeFromList(cache.get(key));
        }
        cache.put(key, insertLinkedList(key, val));
    }

    /**
     * Returns the value associated with the key from the cache,
     * if it exists.
     *
     * @param key The key of the item.
     * @return The value associated with the key, or null if it does not exist.
     */
    public synchronized V get(@NonNull K key) {
        return cache.containsKey(key) ? cache.get(key).data : null;
    }

    /**
     * Removes the key-value pair associated with the key from the cache
     * if it exits.
     *
     * @param key The key of the item.
     * @return True if the item exits in the cache and was remove, otherwise false.
     */
    public synchronized boolean remove(@NonNull K key) {
        if (!cache.containsKey(key)) {
            return false;
        }
        removeNodeFromList(cache.get(key));
        return true;
    }

    /**
     * Evicts the LRU item from the cache.
     */
    private synchronized void evictItem() {
        K evictKey = tail.key;
        removeNodeFromList(tail);
        cache.remove(evictKey);
    }

    /**
     * Removes the specified node from the Linked List.
     */
    private synchronized void removeNodeFromList(ListNode item) {
        if (item == head && item == tail) {
            head = tail = null;
        } else if (item == head) {
            head = item.next;
            head.prev = null;
        } else if (item == tail) {
            tail = item.prev;
            tail.next = null;
        } else {
            item.prev.next = item.next;
            item.next.prev = item.prev;
        }
    }

    /**
     * Inserts the specified key-value pair into the Linked List and
     * returns a reference to it.
     */
    private synchronized ListNode insertLinkedList(K key, V val) {
        if (head == tail) {
            head = tail = new ListNode(null, null, key, val);
        } else {
            head = new ListNode(null, head, key, val);
        }
        return head;
    }

}
