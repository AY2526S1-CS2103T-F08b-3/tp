package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.storage.JsonAdaptedSession.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Price;
import seedu.address.model.person.Session;
import seedu.address.model.person.Subject;

public class JsonAdaptedSessionTest {

    private static final String VALID_DAY = "MONDAY";
    private static final String VALID_TIME = "14:00";
    private static final Long VALID_DURATION = 90L;
    private static final String VALID_SUBJECT = "Mathematics";
    private static final String VALID_PRICE = "50";

    private static final String INVALID_DAY = "INVALIDDAY";
    private static final String INVALID_TIME = "25:00";
    private static final Long INVALID_DURATION = -1L;
    private static final String INVALID_PRICE = "not_a_number";

    @Test
    public void toModelType_validSessionDetails_returnsSession() throws Exception {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, VALID_TIME, VALID_DURATION, VALID_SUBJECT, VALID_PRICE
        );
        Session modelSession = session.toModelType();

        assertEquals(DayOfWeek.MONDAY, modelSession.getDay());
        assertEquals(LocalTime.of(14, 0), modelSession.getTime());
        assertEquals(Duration.ofMinutes(90), modelSession.getDuration());
        assertEquals(new Subject(VALID_SUBJECT), modelSession.getSubject());
        assertEquals(new Price(50, 50), modelSession.getPrice());
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                null, VALID_TIME, VALID_DURATION, VALID_SUBJECT, VALID_PRICE
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "day");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, null, VALID_DURATION, VALID_SUBJECT, VALID_PRICE
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "time");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, VALID_TIME, null, VALID_SUBJECT, VALID_PRICE
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "durationMinutes");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, VALID_TIME, VALID_DURATION, null, VALID_PRICE
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "subject");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, VALID_TIME, VALID_DURATION, VALID_SUBJECT, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "price");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                INVALID_DAY, VALID_TIME, VALID_DURATION, VALID_SUBJECT, VALID_PRICE
        );
        assertThrows(IllegalValueException.class, session::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, INVALID_TIME, VALID_DURATION, VALID_SUBJECT, VALID_PRICE
        );
        assertThrows(IllegalValueException.class, session::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, VALID_TIME, INVALID_DURATION, VALID_SUBJECT, VALID_PRICE
        );
        String expectedMessage = "Session durationMinutes must be positive";
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_zeroDuration_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(
                VALID_DAY, VALID_TIME, 0L, VALID_SUBJECT, VALID_PRICE
        );
        String expectedMessage = "Session durationMinutes must be positive";
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void constructor_validSession_success() {
        Session validSession = new Session(
                DayOfWeek.MONDAY,
                LocalTime.of(14, 0),
                Duration.ofMinutes(90),
                new Subject(VALID_SUBJECT),
                new Price(50, 50)
        );

        JsonAdaptedSession jsonSession = new JsonAdaptedSession(validSession);

        // Verify the fields are correctly set
        try {
            Session reconstructedSession = jsonSession.toModelType();
            assertEquals(validSession.getDay(), reconstructedSession.getDay());
            assertEquals(validSession.getTime(), reconstructedSession.getTime());
            assertEquals(validSession.getDuration(), reconstructedSession.getDuration());
            assertEquals(validSession.getSubject(), reconstructedSession.getSubject());
            assertEquals(validSession.getPrice(), reconstructedSession.getPrice());
        } catch (IllegalValueException e) {
            throw new AssertionError("Valid session should not throw exception", e);
        }
    }

    @Test
    public void toModelType_caseInsensitiveDay_returnsSession() throws Exception {
        JsonAdaptedSession session = new JsonAdaptedSession(
                "monday", VALID_TIME, VALID_DURATION, VALID_SUBJECT, VALID_PRICE
        );
        Session modelSession = session.toModelType();
        assertEquals(DayOfWeek.MONDAY, modelSession.getDay());
    }
}