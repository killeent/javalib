package com.killeent;

import com.killeent.Dictionary.Dictionary;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Base test class for anything implementing {@link com.killeent.Dictionary.Dictionary}.
 *
 * @author Trevor Killeen (2015)
 */
public abstract class BaseDictionaryTest {

    protected Dictionary<Integer, Integer> instance;

    protected abstract Dictionary<Integer, Integer> createInstance();

    @Before
    public void setUp() {
        instance = createInstance();
    }

    /**
     * Tests for {@link com.killeent.Dictionary.Dictionary#add(Object, Object)}
     * and {@link com.killeent.Dictionary.Dictionary#containsKey(Object)}.
     */

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when passing
     * in a null key.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDictionaryAddNullKey() {
        instance.add(null, 1);
    }

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when passing
     * in a null value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDictionaryAddNullValue() {
        instance.add(0, null);
    }

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when passing
     * in a null key.
     */
    @Test
    public void testDictionaryContainsNullKey() {
        instance.containsKey(1);
    }

    /**
     * Tests contains on a missing key.
     */
    @Test
    public void testDictionaryContainsMissingKey() {
        Assert.assertFalse(instance.containsKey(1));
    }

    /**
     * Tests a simple add and contains.
     */
    @Test
    public void testDictionarySimpleAddContains() {
        instance.add(1, 1);
        Assert.assertTrue(instance.containsKey(1));
        Assert.assertFalse(instance.containsKey(2));
        instance.clear();
    }

    /**
     * Tests for {@link com.killeent.Dictionary.Dictionary#get(Object)}.
     */

    /**
     * Tests for an {@link java.lang.IllegalArgumentException} when trying
     * to get a null key.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDictionaryGetNullKey() {
        instance.get(null);
    }

    /**
     * Tests for getting a key not in the Dictionary.
     */
    @Test
    public void testDictionaryGetMissingKey() {
        Assert.assertNull(instance.get(1));
    }

    /**
     * Tests a simple get from the dictionary.
     */
    @Test
    public void testDictionaryGetSimple() {
        instance.add(1, 2);
        Assert.assertEquals(2, instance.get(1).intValue());
        instance.clear();
    }

    /**
     * Tests that add updates the value in the dictionary if it
     * already exits.
     */
    @Test
    public void testDictionaryAddUpdateValue() {
        instance.add(1, 2);
        Assert.assertEquals(2, instance.get(1).intValue());
        instance.add(1, 3);
        Assert.assertEquals(3, instance.get(1).intValue());
        instance.clear();
    }

    /**
     * Tests for {@link com.killeent.Dictionary.Dictionary#remove(Object)}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} when removing a
     * null key.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDictionaryRemoveNullKey() {
        instance.remove(null);
    }

    /**
     * Tests for removing a key not in the Dictionary.
     */
    @Test
    public void testDictionaryRemoveMissingKey() {
        Assert.assertFalse(instance.remove(1));
    }

    /**
     * Tests for successfully removing a key value pair from the Dictionary.
     */
    @Test
    public void testDictionaryRemoveSuccessful() {
        instance.add(1, 2);
        Assert.assertTrue(instance.containsKey(1));
        Assert.assertTrue(instance.remove(1));
        Assert.assertFalse(instance.containsKey(1));
    }

    /**
     * Tests for {@link com.killeent.Dictionary.Dictionary#keySet()}.
     */

    /**
     * Tests getting the key set of an empty Dictionary.
     */
    @Test
    public void testDictionaryKeySetEmpty() {
        Assert.assertTrue(instance.keySet().isEmpty());
    }

    /**
     * Tests getting the key set of a single element Dictionary.
     */
    @Test
    public void testDictionaryKeySetSingleElement() {
        instance.add(1, 2);
        Set<Integer> keySet = instance.keySet();
        Assert.assertEquals(1, keySet.size());
        Assert.assertTrue(keySet.contains(1));
        instance.clear();
    }

    /**
     * Tests getting the key set of a multi element Dictionary.
     */
    @Test
    public void testDictionaryKeySetMultiElement() {
        instance.add(1, 2);
        instance.add(3, 4);
        instance.add(5, 6);
        Set<Integer> keySet = instance.keySet();
        Assert.assertEquals(3, keySet.size());
        Assert.assertTrue(keySet.contains(1));
        Assert.assertTrue(keySet.contains(3));
        Assert.assertTrue(keySet.contains(5));
        instance.clear();
    }

    /**
     * Tests for {@link com.killeent.Dictionary.Dictionary#size()}.
     */

    /**
     * Tests getting the size of an empty Dictionary.
     */
    @Test
    public void testDictionarySizeEmpty() {
        Assert.assertEquals(0, instance.size());
    }

    /**
     * Tests getting the size of a single element Dictionary.
     */
    @Test
    public void testDictionarySizeSingleElement() {
        instance.add(1, 2);
        Assert.assertEquals(1, instance.size());
        instance.clear();
    }

    /**
     * Tests getting the size of a multi element Dictionary.
     */
    @Test
    public void testDictionarySizeMultiElement() {
        instance.add(1, 2);
        instance.add(3, 4);
        instance.add(5, 6);
        Assert.assertEquals(3, instance.size());
        instance.clear();
    }

}
