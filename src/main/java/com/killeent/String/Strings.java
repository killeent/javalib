package com.killeent.String;

/**
 * Various algorithms that operate over {@link java.lang.String}.
 *
 * @author Trevor Killeen (2014)
 */
public class Strings {

    /**
     * Returns true if the specified string is a palindrome.
     *
     * @param input The string to consider.
     * @throws java.lang.IllegalArgumentException if input is null.
     * @return True if input is a palindrome, otherwise false.
     */
    public static boolean isPalindrome(String input) {
        if (input == null) {
            throw new IllegalArgumentException("string cannot be null");
        }

        int i = 0;
        int j = input.length() - 1;

        while (i < j) {
            if (input.charAt(i) != input.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

}
