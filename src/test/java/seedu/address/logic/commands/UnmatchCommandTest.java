package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnmatchCommand.
 */
public class UnmatchCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        int invalidId = 99999;
        UnmatchCommand unmatchCommand = new UnmatchCommand(invalidId);

        assertCommandFailure(unmatchCommand, model,
                String.format(UnmatchCommand.MESSAGE_NOT_FOUND, invalidId));
    }

    @Test
    public void execute_personNotMatched_throwsCommandException() {
        Person tutor = new PersonBuilder().withRole("tutor").build();
        model.addPerson(tutor);

        UnmatchCommand unmatchCommand = new UnmatchCommand(tutor.getPersonId());

        assertCommandFailure(unmatchCommand, model,
                String.format(UnmatchCommand.MESSAGE_NOT_MATCHED, tutor.getPersonId()));
    }

    @Test
    public void equals() {
        UnmatchCommand unmatchFirstCommand = new UnmatchCommand(1);
        UnmatchCommand unmatchSecondCommand = new UnmatchCommand(2);

        // same object -> returns true
        assertTrue(unmatchFirstCommand.equals(unmatchFirstCommand));

        // same values -> returns true
        UnmatchCommand unmatchFirstCommandCopy = new UnmatchCommand(1);
        assertTrue(unmatchFirstCommand.equals(unmatchFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmatchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmatchFirstCommand.equals(null));

        // different person ID -> returns false
        assertFalse(unmatchFirstCommand.equals(unmatchSecondCommand));
    }
}

