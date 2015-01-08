package com.killeent.String;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Uses the Knuth-Morris-Pratt algorithm to find the first index of the specified
     * substring in the specified search string.
     *
     * @param s The string to search in.
     * @param search The substring to look for.
     * @throws java.lang.IllegalArgumentException if search or sub is null.
     * @throws java.lang.IllegalArgumentException if search is an empty string.
     * @return The first such index marking the beginning of the substring sub in search if the
     * substring exists, otherwise -1.
     */
    public static int contains(String s, String search) {
        if (s == null || search == null) {
            throw new IllegalArgumentException("null input strings");
        } else if (search.isEmpty()) {
            throw new IllegalArgumentException("empty search string");
        }

        // Construct the partial match table for the search string
        int[] partial = buildTable(search);

        int match = 0; // index of the start of the current match
        int index = 0; // index into W

        while (match + index < s.length()) {
            if (s.charAt(match + index) == search.charAt(index)) {
                // match occurs
                index++;
                if (index == search.length()) {
                    // we have matched completely, return candidate
                    return match;
                }
            } else {
                // mismatch occurs
                if (partial[index] > -1) {
                    // we can fall back in searchIndex
                    match = match + index - partial[index];
                    index = partial[index];
                } else {
                    // can't fall back, consider the next character as the
                    // start of a potential match
                    index = 0;
                    match++;
                }
            }
        }

        return -1;
    }


    /**
     * Construct KMP partial match table for a given search substring.
     *
     * @param search The string to construct a table for.
     * @return The KMP partial match table for the given string.
     */
    private static int[] buildTable(String search) {
        // The Knuth-Morris-Pratt partial match table is used when we find a mismatch (failure)
        // when searching a string for the specified substring. Let i be the index into the
        // substring W where the mismatch occurs. We construct a match table T such that T[i] is
        // the length of the longest possible initial segment of W that ends at W[i - 1] (not
        // including W[0 ... i - 1].

        int[] table = new int[search.length()];

        // by convention, T[0] = -1, T[1] = 0
        table[0] = -1;

        if (search.length() == 1) {
            return table;
        }

        table[1] = 0;

        int segmentIndex = 0;   // index into W
        int index = 2;          // index into T

        while (index < search.length()) {
            if (search.charAt(index - 1) == search.charAt(segmentIndex)) {

                // case 1: the character before i matches the index into W specified by segmentIndex.
                // If this is the first time this happened, then we mark the table[index] as 1 and
                // continue the next index i and segmentIndex. If this is the a subsequent occurrence,
                // we know that the segmentIndex + 1 characters before W[index] match
                // W[0 .. segmentIndex - 1]

                segmentIndex++;
                table[index] = segmentIndex;
                index++;
            } else if (segmentIndex > 0) {

                // case 2: there is a mismatch between W[index - 1] and W[segmentIndex] but there
                // was at least one match before this.
                //
                // We cannot exclude the possibility that some smaller substring preceding
                // index is a match. In particular, we note that T[segmentIndex] represents the
                // length of the longest possible initial segment that ends at W[segmentIndex - 1].
                //
                // If this value is non-zero, it indicates that the current substring we have matched
                // before this index may itself match an initial segment in W, so we fall back

                segmentIndex = table[segmentIndex];
            } else {

                // case 3: there is a mismatch between W[index = 1] and W[segmentIndex] and we have
                // matched nothing before this. Therefore T[index] = 0

                table[index] = 0;
                index++;
            }
        }

        return table;
    }

    /**
     * Given two strings that represent integers, i.e. that match:
     *
     * ^-?[0-9]+$
     *
     * Returns a string representation of result of multiplying the two strings.
     *
     * @param a A string representation of an integer.
     * @param b A string representation of an integer.
     * @throws java.lang.IllegalArgumentException if a or b is null.
     * @throws java.lang.IllegalArgumentException if a or b is not formatted as an integer.
     * @return The string representation of the multiplication of the two input integers.
     */
    public static String multiply(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("null input to string multiplication");
        }
        Pattern integer = Pattern.compile("^-?[0-9]+$");
        Matcher matcher = integer.matcher(a);
        if (!matcher.find()) {
            throw new IllegalArgumentException("input string a is improperly formatted");
        }
        matcher =integer.matcher(b);
        if (!matcher.find()) {
            throw new IllegalArgumentException("input string a is improperly formatted");
        }

        // okay, we have valid input; check and see if our result is negative
        boolean negative = false;
        if (a.charAt(0) == '-') {
            negative = true;
            a = a.substring(1);
        }
        if (b.charAt(0) == '-') {
            negative = !negative;
            b = b.substring(1);
        }

        // we will compute our result as an int[], where position 0 into the array represents
        // the least significant digit in a base10 integer. This array will contain at most
        // ak * bk entries, where ak is the number of digits in a, and bk the number of digits
        // in b. We add 1 to this to make our multiplication easier
        int[] result = new int[a.length() + b.length() + 1];

        // now perform the multiplication: we multiply each digit of number a with the entirety
        // of number b and sum the results; implicitly i and j keep track of the 10s, 100s, 1000s
        // etc.
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {
                result[i + j] +=
                        (a.charAt(a.length() - 1 - i) - '0') *
                        (b.charAt(b.length() - 1 - j) - '0');
                result[i + j + 1] += result[i + j] / 10;
                result[i + j] = result[i + j] % 10;
            }
        }

        // now convert to a string; we start at the highest non-zero index in result
        StringBuilder sb = new StringBuilder();
        int i = result.length - 1;

        while(i >= 0 && result[i] == 0 ) {
            i--;
        }

        // edge case the result is all zeroes
        if (i == -1) {
            return "0";
        }

        while(i >= 0) {
            sb.append(result[i]);
            i--;
        }

        // add in negative if necessary
        if (negative) {
            sb.insert(0, '-');
        }

        return sb.toString();
    }

}
