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

        int sIndex = 0;         // index into s
        int searchIndex = 0;    // index into search
        int candidate = 0;      // index of beginning of match

        while (sIndex < s.length() - search.length()) {
            if (s.charAt(sIndex) == search.charAt(searchIndex)) {
                // match occurs
                sIndex++;
                searchIndex++;
                if (searchIndex == search.length()) {
                    // we have matched completely, return candidate
                    return candidate;
                }
            } else {
                // mismatch occurs
                if (partial[sIndex] > -1) {
                    // we can fall back in searchIndex
                    searchIndex = partial[sIndex];
                    candidate = sIndex - partial[sIndex];
                } else {
                    // can't fall back, consider the next character as the
                    // start of a potential match
                    sIndex++;
                    searchIndex = 0;
                    candidate = sIndex;
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

}
