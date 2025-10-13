package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Subject object for a Person.
 */
@SuppressWarnings("checkstyle:Regexp")
public class Subject {
    public static final String MESSAGE_CONSTRAINTS = "Subject cannot be blank.";
    public static final String VALIDATION_REGEX = "[A-Za-z ]+";
    public final String subject;

    /**
     * Constructs a Subject object.
     * @param subject A valid Subject.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        String s = subject.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.subject = s;
    }
    public static boolean isValidSubject(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    @Override public String toString() {
        return subject;
    }
    @Override public int hashCode() {
        return subject.hashCode();
    }
    @Override public boolean equals(Object o) {
        return (o == this) || (o instanceof Subject && subject.equals(((Subject) o).subject));
    }
}
