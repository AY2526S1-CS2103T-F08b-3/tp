package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Level or a range of Levels for a Person.
 */
public class Level {
    public static final String MESSAGE_CONSTRAINTS =
            "Level must be a positive integer or a range start-end (e.g., 3 or 1-6) with start <= end.";
    public static final String VALIDATION_REGEX = "\\d+|\\d+\\s*-\\s*\\d+";

    private final int start; // inclusive
    private final int end; // inclusive

    /**
     * Constructs a Level object with the given min and max values.
     * @param start inclusive start of the level range.
     * @param end inclusive end of the level range.
     */
    public Level(int start, int end) {
        if (start <= 0 || end <= 0 || start > end) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Returns true if the given level string is valid.
     * @param levelString Level inputted.
     * @return boolean.
     */
    public static boolean isValidLevel(String levelString) {
        requireNonNull(levelString);
        try {
            Level level = parse(levelString);
            return level.start > 0 && level.end > 0
                    && level.start <= 6 && level.end <= 6
                    && level.start <= level.end;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Calls the constructor when passed in a string.
     * Accepts "3", "03", "3-3", "1-6" (spaces allowed around '-').
     * @param text Inputted String to call the constructor.
     * @return A new Price Object.
     */
    public static Level parse(String text) {
        requireNonNull(text);
        String s = text.trim();
        // Single value?
        if (s.matches("\\d+")) {
            int v = Integer.parseInt(s);
            return new Level(v, v);
        }
        // Range?
        if (s.matches("\\d+\\s*-\\s*\\d+")) {
            String[] parts = s.split("-");
            int a = Integer.parseInt(parts[0].trim());
            int b = Integer.parseInt(parts[1].trim());
            return new Level(a, b);
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }
    /**
     * Checks if the range of Level covers the other Level.
     * @param other The other level object.
     * @returnTrue if this range covers the other (e.g., tutor covers student).
     */
    public boolean includes(Level other) {
        return this.start <= other.start && this.end >= other.end;
    }

    /**
     * Checks if this level range intersects with another level range.
     * @param other The other level object.
     * @return True if the ranges overlap at all.
     */
    public boolean intersects(Level other) {
        return this.start <= other.end && this.end >= other.start;
    }

    /**
     * Check if the Level value is a single value (mainly for students).
     * @return true if it is a single value.
     */
    public boolean isSingle() {
        return start == end;
    }

    /** Store compactly: "3" if single, else "1-6". */
    @Override public String toString() {
        return isSingle() ? Integer.toString(start) : (start + "-" + end);
    }

    @Override public int hashCode() {
        return 31 * start + end;
    }
    @Override public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Level)) {
            return false;
        }
        Level other = (Level) o;
        return start == other.start && end == other.end;
    }
}
