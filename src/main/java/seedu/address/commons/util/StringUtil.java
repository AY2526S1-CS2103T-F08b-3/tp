package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} represents an invalid index integer
     * e.g. "-1", "0", "-100", etc. <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "1", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isInvalidIndexInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return (value < 0 && !s.startsWith("+")) || value == 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} represents a valid role
     * e.g. "tutors"/ "students"  <br>
     * Will return false for any other invalid string input
     * e.g. empty string, "tutor", "student", and " tutor " (untrimmed), "stu dent" (contains whitespace)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isValidRole(String s) {
        requireNonNull(s);
        return s.equals("tutors") || s.equals("students");
    }

    /**
     * Returns true if {@code s} represents a valid individual role
     * e.g. "tutor"/ "student"  <br>
     * Will return false for any other invalid string input
     * e.g. empty string, "tutors", "students", and " tutor " (untrimmed), "stu dent" (contains whitespace)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isValidIndivRole(String s) {
        requireNonNull(s);
        return s.equals("tutor") || s.equals("student");
    }

    /**
     * Returns the input word with the first letter capitalized and the rest in lowercase.
     * e.g. "mathematics" becomes "Mathematics"
     */
    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

}
