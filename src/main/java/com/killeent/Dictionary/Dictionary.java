package com.killeent.Dictionary;

import java.util.Set;

/**
 * A dictionary is a collection of key value pairs.
 *
 * @author Trevor Killeen (2015)
 */
public interface Dictionary<K,V> {

    /**
     * Adds the specified key-value pair to the dictionary. If key already is
     * in the dictionary, then its corresponding value is replaced with val.
     *
     * @param key The key to add.
     * @param val The value associated with the key.
     */
    public void add(K key, V val);

    /**
     * Tests whether the specified key is in the dictionary.
     *
     * @param key The key to look for.
     * @return True if key is in the dictionary, otherwise false.
     */
    public boolean containsKey(K key);

    /**
     * Gets the value associated with the specified key.
     *
     * @param key The key associated with the value.
     * @return The value associated with the key, or NULL if the key is not
     * in the dictionary.
     */
    public V get(K key);

    /**
     * Removes the key-value pair specified by key from the table, if it
     * exits.
     *
     * @param key The key in the key-value pair.
     * @return True if the key-value pair was removed, or false if the key
     * was not in the dictionary.
     */
    public boolean remove(K key);

    /**
     * @return A set of all keys in the dictionary.
     */
    public Set<K> keySet();

    /**
     * @return The number of elements in the dictionary. 
     */
    public int size();

}
