package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents an hourly rate (in integer dollars) as either a single value or a range [min-max], inclusive.
 * Examples: "35", "30-45".
 */
public class Price {
    public static final String MESSAGE_CONSTRAINTS =
            "Price must be a non-negative integer or a range min-max (e.g., 35 or 30-45) with min <= max.";
    public static final String VALIDATION_REGEX = "\\d+|\\d+\\s*-\\s*\\d+";

    private final int min; // inclusive
    private final int max; // inclusive

    /**
     * Constructs a Price object.
     * @param min A valid min of the price range.
     * @param max A valid max of the price range.
     */
    public Price(int min, int max) {
        if (min < 0 || max < 0 || min > max) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.min = min;
        this.max = max;
    }

    /**
     * Returns true if the given price string is valid.
     * @param test String inputted.
     * @return boolean.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     *  * Calls the constructor when passed in a string.
     * Accepts "35", "035", "35-35", "30-45" (spaces allowed around '-')
     * @param text Inputted String to call the constructor.
     * @return A new Price Object.
     */
    public static Price parse(String text) {
        requireNonNull(text);
        String s = text.trim();
        if (s.matches("\\d+")) {
            int v = Integer.parseInt(s);
            return new Price(v, v);
        }
        if (s.matches("\\d+\\s*-\\s*\\d+")) {
            String[] parts = s.split("-");
            int a = Integer.parseInt(parts[0].trim());
            int b = Integer.parseInt(parts[1].trim());
            return new Price(a, b);
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }
    /**
     * Checks if price range fully cover the other.
     * Useful for checking budget ⊆ tutor price range, etc.
     * @param other Another Price object to compare to.
     * @return True if this price range fully covers the other.
     */
    public boolean includes(Price other) {
        return this.min <= other.min && this.max >= other.max;
    }
    /**
     * Check if price range intersects.
     * Useful for quick compatibility checks.
     * @param other Another Price object to compare to.
     * @return True if the ranges intersect at all.
     */
    public boolean overlaps(Price other) {
        return this.min <= other.max && other.min <= this.max;
    }
    /**
     * Check if a value is within the price range.
     * @param value Value to check.
     * @return True if a single value falls within this range.
     */
    public boolean includesSingle(int value) {
        return value >= min && value <= max;
    }
    public boolean isSingle() {
        return min == max;
    }

    /**
     * Gets the average price as a single Price object to be used for statistical purposes.
     * @return Price object with average price as both min and max.
     */
    public Price getAveragePrice() {
        int average = (min + max) / 2;
        return new Price(average, average);
    }

    @Override public String toString() {
        return isSingle() ? Integer.toString(min) : (min + "-" + max);
    }

    @Override public int hashCode() {
        return 31 * min + max;
    }

    @Override public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        Price other = (Price) o;
        return this.min == other.min && this.max == other.max;
    }
}

