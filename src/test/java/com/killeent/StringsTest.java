package com.killeent;

import com.killeent.String.Strings;
import junit.framework.Assert;
import org.junit.Test;

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
}
