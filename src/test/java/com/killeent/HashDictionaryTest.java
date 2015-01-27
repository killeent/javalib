package com.killeent;

import com.killeent.Dictionary.Dictionary;
import com.killeent.Dictionary.HashDictionary;

/**
 * Tests for {@link com.killeent.Dictionary.HashDictionary}.
 *
 * @author Trevor Killeen (2015)
 */
public class HashDictionaryTest extends BaseDictionaryTest {
    @Override
    protected Dictionary<Integer, Integer> createInstance() {
        return new HashDictionary<Integer, Integer>();
    }

    // todo: add implementation specific tests
}
