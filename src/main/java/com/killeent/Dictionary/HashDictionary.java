package com.killeent.Dictionary;

import java.util.*;

/**
 * A Hash Table implementation of a Dictionary.
 *
 * @author Trevor Killeen (2015)
 */
public class HashDictionary<K,V> implements Dictionary<K,V> {

    // Internally, we represent the Dictionary as a fixed size array
    // of Linked Lists. Implicitly this means we use Separate Chaining
    // to resolve collisions.
    //
    // We use modular hashing to generate an array index from the result
    // of the key's hashCode function. In order to effectively disperse
    // the keys in the table, the table size must be prime.
    //
    // We automatically resize the table when the load factor is 1.

    private static final int DEFAULT_INITIAL_SIZE = 17;
    private static final float DEFAULT_LOAD_FACTOR = 1.0f;

    // tracks the primes less than the size of our table
    private List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17);

    // we need to store key-value pairs in our tables so let's make a class to do so
    private static class Entry<K,V>
    {
        public K key;
        public V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // the table itself
    private LinkedList<Entry<K,V>>[] table;

    // number of elements in the table
    private int count;

    /**
     * Constructs a new HashDictionary -> a hash table based implementation
     * of a dictionary.
     */
    @SuppressWarnings("unchecked")  // generic array creation
    public HashDictionary() {
        table = (LinkedList<Entry<K,V>>[]) new LinkedList[DEFAULT_INITIAL_SIZE];
        count = 0;
    }

    @Override
    public void add(K key, V val) {
        if (key == null || val == null) {
            throw new IllegalArgumentException("null args");
        }

        LinkedList<Entry<K,V>> bucket = table[hash(key)];

        if (bucket == null) {
            bucket = new LinkedList<Entry<K, V>>();
            table[hash(key)] = bucket;
        }

        for (Entry<K,V> entry : bucket) {
            if (entry.key.equals(key)) {
                // simply update the value
                entry.value = val;
                return;
            }
        }

        // we didn't find it, add it to the end
        bucket.add(new Entry<K, V>(key, val));
        count++;

        resizeIfNecessary();
    }

    /**
     * Computes the index into our array from the specified key.
     *
     * @param key The key to compute an index for.
     * @return The index into the array.
     */
    private int hash(K key) {
        // simply the key's hashcode mod the table size!
        return key.hashCode() % table.length;
    }

    /**
     * We resize the table if we exceed the specified load factor. We do so
     * by calculating a prime roughly twice the size of the table's current
     * size and copying over.
     */
    @SuppressWarnings("unchecked")  // generic array creation
    private void resizeIfNecessary() {
        if (count * 1.0 / table.length > DEFAULT_LOAD_FACTOR) {
            int size = calculateDoublePrime(table.length);
            if (size == -1) {
                // we are at our maximum table size
                return;
            }

            LinkedList<Entry<K,V>>[] copy = (LinkedList<Entry<K,V>>[]) new LinkedList[size];

            // we need to rehash every key-value pair in the existing table
            for (int i = 0; i < table.length; i++) {
                for (Entry<K,V> entry : table[i]) {
                    int index = hash(entry.key);
                    if (copy[index] == null) {
                        copy[index] = new LinkedList<Entry<K, V>>();
                    }
                    copy[hash(entry.key)].add(entry);
                }
            }

            table = copy;
        }
    }

    /**
     * Calculates a prime that is >2x the specified prime.
     *
     * @param curr The prime to compare against.
     * @return A prime >2x the current prime, or -1 if we would overflow
     * when calculating this prime.
     */
    private int calculateDoublePrime(int curr) {
        // off-by-one case
        if (curr == Integer.MAX_VALUE) {
            return -1;
        }

        int candidate = curr + 1;
        while (candidate < Integer.MAX_VALUE) {
            // even numbers are not prime
            if (candidate % 2 == 0) {
                candidate++;
                continue;
            }

            // iterate over primes less than the square root of candidate
            double root = Math.sqrt(candidate);
            boolean foundFactor = false;
            for (Integer prime : primes) {
                if (prime > root) {
                    break;
                }
                if (candidate % prime == 0) {
                    // not a prime
                    foundFactor = true;
                }
            }

            if (foundFactor) {
                candidate++;
                continue;
            }

            // candidate is not divisible by a prime less than its square root
            // so candidate is prime. Add to primes list and check and see if
            // it is big enough
            primes.add(candidate);
            if (candidate > curr * 2) {
                return candidate;
            } else {
                // keep searching
                candidate++;
            }
        }

        // we ran out of primes
        return -1;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }

        List<Entry<K,V>> bucket = table[hash(key)];

        if (bucket == null) {
            return null;
        }

        for (Entry<K,V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }

        List<Entry<K,V>> bucket = table[hash(key)];

        if (bucket == null) {
            return false;
        }

        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).key.equals(key)) {
                bucket.remove(i);
                count--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<K>(count);
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry<K,V> entry : table[i]) {
                    result.add(entry.key);
                }
            }
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                table[i].clear();
            }
        }
        count = 0;
    }
}
