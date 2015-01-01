package com.killeent;

import com.killeent.String.Strings;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Tests for {@link com.killeent.String.Strings}.
 *
 * @author Trevor Killeen (2014)
 */
public class StringsTest {

    /**
     * Tests for {@link com.killeent.String.Strings#isPalindrome(String)}.
     */

    /**
     * Tests for {@link java.lang.IllegalArgumentException} if input is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsPalindromeNullString() {
        Strings.isPalindrome(null);
    }

    /**
     * Tests that a single character string is a palindrome.
     */
    @Test
    public void testIsPalindromeSingleCharacter() {
        Assert.assertTrue(Strings.isPalindrome("a"));
    }

    /**
     * Tests various even length strings for palindromicity.
     */
    @Test
    public void testIsPalindromeEvenLengthInput() {
        Assert.assertTrue(Strings.isPalindrome("aa"));
        Assert.assertFalse(Strings.isPalindrome("ab"));
        Assert.assertFalse(Strings.isPalindrome("ba"));
        Assert.assertTrue(Strings.isPalindrome("aaaa"));
        Assert.assertTrue(Strings.isPalindrome("abba"));
        Assert.assertFalse(Strings.isPalindrome("aaba"));
        Assert.assertTrue(Strings.isPalindrome("abjkaakjba"));
        Assert.assertFalse(Strings.isPalindrome("abcdefghij"));
    }

    /**
     * Tests various odd length strings for palindromicity.
     */
    @Test
    public void testIsPalindromeOddLengthInput() {
        Assert.assertTrue(Strings.isPalindrome("aca"));
        Assert.assertFalse(Strings.isPalindrome("acb"));
        Assert.assertFalse(Strings.isPalindrome("bca"));
        Assert.assertTrue(Strings.isPalindrome("aacaa"));
        Assert.assertTrue(Strings.isPalindrome("abcba"));
        Assert.assertFalse(Strings.isPalindrome("aacba"));
        Assert.assertTrue(Strings.isPalindrome("abjkacakjba"));
        Assert.assertFalse(Strings.isPalindrome("abcdecfghij"));
    }

    /**
     * Tests for {@link com.killeent.String.Strings#contains(String, String)}.
     */

    /**
     * Tests for a {@link java.lang.IllegalArgumentException} for passing a null string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStringContainsNullString() {
        Strings.contains(null, "hi");
    }

    /**
     * Tests for a {@link java.lang.IllegalArgumentException} for passing a null search string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStringContainsNullSearchString() {
        Strings.contains("hi", null);
    }

    /**
     * Tests for a {@link java.lang.IllegalArgumentException} for passing an empty search string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStringContainsEmptySearchString() {
        Strings.contains("hi", "");
    }

    /**
     * Tests a variety of scenarios for contains.
     */
    @Test
    public void testStringContainsComprehensive() {
        List<String> sStrings = new ArrayList<String>();
        List<String> searchStrings = new ArrayList<String>();
        List<Integer> expected = new ArrayList<Integer>();
        List<String> error = new ArrayList<String>();

        // exact match
        sStrings.add("hello");
        searchStrings.add("hello");
        expected.add(0);
        error.add("contains failed on exact match");

        // match at beginning of string
        sStrings.add("abcac");
        searchStrings.add("abc");
        expected.add(0);
        error.add("contains failed on match at beginning");

        // match at end of string
        sStrings.add("defabc");
        searchStrings.add("abc");
        expected.add(3);
        error.add("contains failed on match at end of string");

        // match in middle of string
        sStrings.add("dabcd");
        searchStrings.add("abc");
        expected.add(1);
        error.add("contains failed on match at middle of string");

        // match (single character)
        sStrings.add("abcdef");
        searchStrings.add("e");
        expected.add(4);
        error.add("contains failed on match single character");

        // no match (single character)
        sStrings.add("abcdef");
        searchStrings.add("j");
        expected.add(-1);
        error.add("contains found invalid match on single character");

        // no match (multiple characters)
        sStrings.add("abcdef");
        searchStrings.add("hij");
        expected.add(-1);
        error.add("contains found invalid match on multiple characters");

        // no match (partial match exists)
        sStrings.add("abcdef");
        searchStrings.add("deg");
        expected.add(-1);
        error.add("contains found invalid match when only a partial match occurs");

        // first match
        sStrings.add("abcabcabc");
        searchStrings.add("cab");
        expected.add(2);
        error.add("contains did not find the first match that occurred out of many");

        // edge (almost a match)
        sStrings.add("aaaaaaaaaaaaaaab");
        searchStrings.add("aaaaaaaaaaaaaaac");
        expected.add(-1);
        error.add("contains failed on an almost match");

        runStringContainsTests(sStrings, searchStrings, expected, error);
    }

    /**
     * Runs the comprehensive String contains test.
     */
    private void runStringContainsTests(List<String> sStrings,
                                        List<String> searchStrings,
                                        List<Integer> expected,
                                        List<String> error) {
        for (int i = 0; i < sStrings.size(); i++) {
            Assert.assertEquals(error.get(i),
                    (int) expected.get(i),
                    (int) Strings.contains(sStrings.get(i), searchStrings.get(i)));
        }
    }

}
