package seedu.address.storage;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Price;
import seedu.address.model.person.Session;
import seedu.address.model.person.Subject;

/**
 * Jackson-friendly version of {@link Session}.
 * Persists Session as:
 * {
 *   "day": "MONDAY",
 *   "time": "15:00",
 *   "durationMinutes": 90,
 *   "subject": "Mathematics",
 *   "price": "40-50"
 * }
 */
public class JsonAdaptedSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Session's %s field is missing!";

    private final String day;
    private final String time;
    private final Long durationMinutes;
    private final String subject;
    private final String price;

    /**
     * Constructs a {@code JsonAdaptedSession} with the provided values.
     * This constructor is used by Jackson when deserializing JSON into a {@code JsonAdaptedSession}.
     *
     * @param day           The day of the week (e.g. "MONDAY").
     * @param time          The session start time (e.g. "15:00").
     * @param durationMinutes The duration of the session in minutes.
     * @param subject       The subject of the session (e.g. "Mathematics").
     * @param price         The price range for the session (e.g. "40-50").
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("day") String day,
                              @JsonProperty("time") String time,
                              @JsonProperty("durationMinutes") Long durationMinutes,
                              @JsonProperty("subject") String subject,
                              @JsonProperty("price") String price) {
        this.day = day;
        this.time = time;
        this.durationMinutes = durationMinutes;
        this.subject = subject;
        this.price = price;
    }

    /**
     * Creates a {@code JsonAdaptedSession} from a given {@code Session}.
     * This constructor is used to convert a {@code Session} object into a Jackson-friendly representation.
     *
     * @param source The source {@code Session} object to be converted.
     */
    public JsonAdaptedSession(Session source) {
        this.day = source.getDay().name();
        this.time = source.getTime().toString();
        this.durationMinutes = source.getDuration().toMinutes();
        this.subject = source.getSubject().toString();
        this.price = source.getPrice().toString();
    }

    /**
     * Converts this Jackson-friendly object into the model's {@link Session}.
     *
     * @return A {@code Session} object that corresponds to the JSON data.
     * @throws IllegalValueException if any field is missing or invalid.
     */
    public Session toModelType() throws IllegalValueException {
        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "day"));
        }
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "time"));
        }
        if (durationMinutes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "durationMinutes"));
        }
        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "subject"));
        }
        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "price"));
        }

        DayOfWeek modelDay;
        try {
            modelDay = DayOfWeek.valueOf(day.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalValueException("Invalid day value for session: " + day);
        }

        LocalTime modelTime;
        try {
            modelTime = LocalTime.parse(time);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid time value for session: " + time);
        }

        if (durationMinutes <= 0) {
            throw new IllegalValueException("Session durationMinutes must be positive");
        }
        Duration modelDuration = Duration.ofMinutes(durationMinutes);
        Subject modelSubject = new Subject(subject);
        int intPrice = Integer.parseInt(price);
        Price modelPrice = new Price(intPrice, intPrice);

        return new Session(modelDay, modelTime, modelDuration, modelSubject, modelPrice);
    }

}
