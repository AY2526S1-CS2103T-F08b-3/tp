package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MatchCommandTest {

    @Test
    public void constructor_validIds_success() {
        MatchCommand matchCommand = new MatchCommand(1, 2);
        assertEquals(1, getFirstId(matchCommand));
        assertEquals(2, getSecondId(matchCommand));
    }

    @Test
    public void execute_sameId_throwsCommandException() {
        Model model = new ModelManager();
        MatchCommand matchCommand = new MatchCommand(1, 1);

        assertThrows(CommandException.class,
                MatchCommand.MESSAGE_SAME_ID, () -> matchCommand.execute(model));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Model model = new ModelManager();

        Person tutor = new PersonBuilder()
                .withRole("tutor")
                .withName("Alice")
                .build();

        model.addPerson(tutor);
        int tutorId = tutor.getPersonId();

        MatchCommand matchCommand = new MatchCommand(tutorId, 9999);

        assertThrows(CommandException.class,
                String.format(MatchCommand.MESSAGE_NOT_FOUND, 9999),
                () -> matchCommand.execute(model));
    }

    @Test
    public void equals() {
        MatchCommand matchCommand1 = new MatchCommand(1, 2);
        MatchCommand matchCommand2 = new MatchCommand(1, 2);
        MatchCommand matchCommand3 = new MatchCommand(2, 3);

        // same object -> returns true
        assertTrue(matchCommand1.equals(matchCommand1));

        // same values -> returns true
        assertTrue(matchCommand1.equals(matchCommand2));

        // different types -> returns false
        assertFalse(matchCommand1.equals(1));

        // null -> returns false
        assertFalse(matchCommand1.equals(null));

        // different ids -> returns false
        assertFalse(matchCommand1.equals(matchCommand3));
    }

    // Helper methods to access private fields for testing
    private int getFirstId(MatchCommand command) {
        try {
            var field = MatchCommand.class.getDeclaredField("firstId");
            field.setAccessible(true);
            return (int) field.get(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getSecondId(MatchCommand command) {
        try {
            var field = MatchCommand.class.getDeclaredField("secondId");
            field.setAccessible(true);
            return (int) field.get(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}