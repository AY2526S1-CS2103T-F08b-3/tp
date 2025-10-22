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
 * Tests for SessionDeleteCommand.
 */
public class SessionDeleteCommandTest {

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
    public void constructor_validIndex_success() {
        SessionDeleteCommand command = new SessionDeleteCommand(Index.fromOneBased(1));
        assertEquals(Index.fromOneBased(1), getIndex(command));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        model.addPerson(tutor);
        SessionDeleteCommand command = new SessionDeleteCommand(Index.fromOneBased(3));

        assertThrows(CommandException.class,
                "Invalid person index. Choose a person from within the displayed list.", () -> command.execute(model));
    }

    @Test
    public void execute_unmatchedPerson_throwsCommandException() {
        model.addPerson(tutor);
        SessionDeleteCommand command = new SessionDeleteCommand(Index.fromOneBased(1));

        assertThrows(CommandException.class,
                "Alice Tan is not matched with anyone. Cannot delete a session.", () -> command.execute(model));
    }

    @Test
    public void execute_personWithoutSession_throwsCommandException() {
        tutor.setMatchedPerson(student);
        student.setMatchedPerson(tutor);

        model.addPerson(tutor);
        model.addPerson(student);

        SessionDeleteCommand command = new SessionDeleteCommand(Index.fromOneBased(1));

        assertThrows(CommandException.class,
                "No active session to delete for Alice Tan.", () -> command.execute(model));
    }

    @Test
    public void execute_validMatchWithSession_success() throws Exception {
        tutor.setMatchedPerson(student);
        student.setMatchedPerson(tutor);

        tutor.setSession(session);
        student.setSession(session);

        model.addPerson(tutor);
        model.addPerson(student);

        SessionDeleteCommand command = new SessionDeleteCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(
                "Deleted session for %s and their matched partner %s.",
                tutor.getName(), student.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());
        List<Person> list = model.getFilteredPersonList();
        Person updatedTutor = list.get(0);
        Person updatedStudent = list.get(1);

        assertEquals(null, updatedTutor.getSession());
        assertEquals(null, updatedStudent.getSession());
        assertEquals(updatedTutor.getMatchedPerson(), updatedStudent);
        assertEquals(updatedStudent.getMatchedPerson(), updatedTutor);
    }

    @Test
    public void equals() {
        SessionDeleteCommand command1 = new SessionDeleteCommand(Index.fromOneBased(1));
        SessionDeleteCommand command2 = new SessionDeleteCommand(Index.fromOneBased(1));
        SessionDeleteCommand command3 = new SessionDeleteCommand(Index.fromOneBased(2));
        assertTrue(command1.equals(command1));
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(1));
        assertFalse(command1.equals(null));
        assertFalse(command1.equals(command3));
    }
    private Index getIndex(SessionDeleteCommand command) {
        try {
            var field = SessionDeleteCommand.class.getDeclaredField("targetIndex");
            field.setAccessible(true);
            return (Index) field.get(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
