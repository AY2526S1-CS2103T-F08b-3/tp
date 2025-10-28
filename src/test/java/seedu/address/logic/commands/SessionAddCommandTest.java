package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Session;
import seedu.address.model.person.Subject;
import seedu.address.testutil.PersonBuilder;

/**
 * Full coverage test for SessionAddCommand.
 */
public class SessionAddCommandTest {

    private Model model;
    private Person tutor;
    private Person student;
    private Session session;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();

        tutor = new PersonBuilder()
                .withRole("tutor")
                .withName("Alice Tan")
                .withPhone("91234567")
                .withEmail("alice@tutors.com")
                .withSubject("Mathematics")
                .withPrice("40")
                .build();

        student = new PersonBuilder()
                .withRole("student")
                .withName("Bob Lee")
                .withPhone("98765432")
                .withEmail("bob@students.com")
                .withSubject("Mathematics")
                .withPrice("40")
                .build();

        session = new Session(
                DayOfWeek.MONDAY,
                LocalTime.of(14, 0),
                Duration.ofHours(2),
                new Subject("Mathematics"),
                new Price(40, 40)
        );
    }

    @Test
    public void constructor_validParameters_success() {
        SessionAddCommand command = new SessionAddCommand(Index.fromOneBased(1), session);
        assertEquals(Index.fromOneBased(1), getIndex(command));
        assertEquals(session, getSession(command));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        model.addPerson(tutor);
        SessionAddCommand command = new SessionAddCommand(Index.fromOneBased(5), session);

        assertThrows(CommandException.class, "Invalid person index.", () -> command.execute(model));
    }

    @Test
    public void execute_unmatchedPerson_throwsCommandException() {
        model.addPerson(tutor);
        SessionAddCommand command = new SessionAddCommand(Index.fromOneBased(1), session);

        assertThrows(CommandException.class,
                "Alice Tan is not matched with anyone. Cannot create a session.", () -> command.execute(model));
    }

    @Test
    public void execute_validMatch_success() throws Exception {
        tutor.setMatchedPerson(student);
        student.setMatchedPerson(tutor);

        model.addPerson(tutor);
        model.addPerson(student);

        SessionAddCommand command = new SessionAddCommand(Index.fromOneBased(1), session);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(
                "Added session for %s and their matched partner %s:\n%s",
                tutor.getName(), student.getName(), session);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        List<Person> list = model.getFilteredPersonList();
        Person updatedTutor = list.get(0);
        Person updatedStudent = list.get(1);

        assertEquals(session, updatedTutor.getSession());
        assertEquals(session, updatedStudent.getSession());
        assertEquals(updatedTutor.getMatchedPerson(), updatedStudent);
        assertEquals(updatedStudent.getMatchedPerson(), updatedTutor);
    }

    @Test
    public void execute_clonePreservingId_preservesIdentityFields() throws Exception {
        tutor.setMatchedPerson(student);
        student.setMatchedPerson(tutor);
        model.addPerson(tutor);
        model.addPerson(student);

        SessionAddCommand command = new SessionAddCommand(Index.fromOneBased(1), session);
        command.execute(model);

        Person updatedTutor = model.getFilteredPersonList().get(0);
        assertEquals(tutor.getPersonId(), updatedTutor.getPersonId());
        assertEquals(tutor.getName(), updatedTutor.getName());
        assertEquals(tutor.getEmail(), updatedTutor.getEmail());
    }

    @Test
    public void execute_multipleSessions_updatesLatestSession() throws Exception {
        tutor.setMatchedPerson(student);
        student.setMatchedPerson(tutor);
        model.addPerson(tutor);
        model.addPerson(student);

        Session session1 = new Session(
                DayOfWeek.MONDAY, LocalTime.of(9, 0),
                Duration.ofHours(1), new Subject("Mathematics"), new Price(40, 40));
        SessionAddCommand command1 = new SessionAddCommand(Index.fromOneBased(1), session1);
        command1.execute(model);

        Session session2 = new Session(
                DayOfWeek.TUESDAY, LocalTime.of(10, 0),
                Duration.ofHours(2), new Subject("Mathematics"), new Price(50, 50));
        SessionAddCommand command2 = new SessionAddCommand(Index.fromOneBased(1), session2);
        command2.execute(model);

        Person updatedTutor = model.getFilteredPersonList().get(0);
        Person updatedStudent = model.getFilteredPersonList().get(1);

        assertEquals(session2, updatedTutor.getSession());
        assertEquals(session2, updatedStudent.getSession());
    }

    @Test
    public void equals() {
        Session session1 = new Session(
                DayOfWeek.MONDAY, LocalTime.of(15, 0),
                Duration.ofHours(1), new Subject("Math"), new Price(40, 40));
        Session session2 = new Session(
                DayOfWeek.TUESDAY, LocalTime.of(10, 0),
                Duration.ofHours(2), new Subject("Science"), new Price(50, 50));

        SessionAddCommand command1 = new SessionAddCommand(Index.fromOneBased(1), session1);
        SessionAddCommand command2 = new SessionAddCommand(Index.fromOneBased(1), session1);
        SessionAddCommand command3 = new SessionAddCommand(Index.fromOneBased(2), session2);

        assertTrue(command1.equals(command1)); // same object
        assertTrue(command1.equals(command2)); // same values
        assertFalse(command1.equals(1)); // different type
        assertFalse(command1.equals(null)); // null
        assertFalse(command1.equals(command3)); // different
    }

    private Index getIndex(SessionAddCommand command) {
        try {
            var field = SessionAddCommand.class.getDeclaredField("targetIndex");
            field.setAccessible(true);
            return (Index) field.get(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Session getSession(SessionAddCommand command) {
        try {
            var field = SessionAddCommand.class.getDeclaredField("session");
            field.setAccessible(true);
            return (Session) field.get(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
