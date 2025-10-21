package seedu.address.model.person;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Objects;

/**
 * Represents a tutoring Session between a matched tutor and student.
 * <p>
 * Each person can have at most one active session. A session stores information
 * about the day, start time, duration, subject, and price agreed upon.
 */

public class Session {
    private final DayOfWeek day;
    private final String time;
    private final Duration duration;
    private final String subject;
    private final double price;

    /**
     * Constructs a {@code Session} object.
     *
     * @param day       The day of the week when the session occurs.
     * @param time      The start time of the session (24-hour format, e.g. "15:00").
     * @param duration  The duration of the session as a {@code Duration} object.
     * @param subject   The subject taught in this session.
     * @param price     The price charged for the session.
     */
    public Session(DayOfWeek day, String time, Duration duration, String subject, double price) {
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.subject = subject;
        this.price = price;
    }

    /** Returns the day of the session. */
    public DayOfWeek getDay() {
        return day;
    }

    /** Returns the start time of the session as a string (e.g. "15:00"). */
    public String getTime() {
        return time;
    }

    /** Returns the duration of the session. */
    public Duration getDuration() {
        return duration;
    }

    /** Returns the subject taught in the session. */
    public String getSubject() {
        return subject;
    }

    /** Returns the price of the session. */
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        String formattedDuration = hours > 0
                ? String.format("%dh %02dm", hours, minutes)
                : String.format("%dm", minutes);

        return String.format("%s | %s | %s | %s | %s",
                day, time, formattedDuration, subject, price);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Session)) {
            return false;
        }

        Session s = (Session) other;
        return day.equals(s.day)
                && time.equalsIgnoreCase(s.time)
                && duration.equals(s.duration)
                && subject.equals(s.subject)
                && price == (s.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time.toLowerCase(), duration, subject, price);
    }
}
