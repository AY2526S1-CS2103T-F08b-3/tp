package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests for {@code RecommendCommand}.
 */
public class RecommendCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        RecommendCommand firstCommand =
                new RecommendCommand(INDEX_FIRST_PERSON, true, true, true);
        RecommendCommand secondCommand =
                new RecommendCommand(INDEX_SECOND_PERSON, true, true, true);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        RecommendCommand firstCommandCopy =
                new RecommendCommand(INDEX_FIRST_PERSON, true, true, true);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_noCriteria_recommendTutors() {
        // FIONA is a student, recommend tutors based on all criteria
        RecommendCommand command =
                new RecommendCommand(INDEX_SIXTH_PERSON, false, false, false);
        String expectedMessage = RecommendCommand.MESSAGE_SUCCESS_TUTORS;
        assertCommandSuccess(command, model, expectedMessage, model);
        List<Person> result = model.getFilteredPersonList();
        assertTrue(result.stream().allMatch(Person::isTutor));
    }

    @Test
    public void execute_noCriteria_recommendStudents() {
        // ALICE is a tutor, recommend students based on all criteria
        RecommendCommand command =
                new RecommendCommand(INDEX_FIRST_PERSON, false, false, false);
        String expectedMessage = RecommendCommand.MESSAGE_SUCCESS_STUDENTS;
        assertCommandSuccess(command, model, expectedMessage, model);
        List<Person> result = model.getFilteredPersonList();
        assertTrue(result.stream().allMatch(Person::isStudent));
    }

    @Test
    public void execute_withCriteria_recommendTutors() {
        // FIONA is a student, recommend tutors based on subject only
        RecommendCommand command =
                new RecommendCommand(INDEX_SIXTH_PERSON, true, false, false);
        String expectedMessage = RecommendCommand.MESSAGE_SUCCESS_TUTORS;
        assertCommandSuccess(command, model, expectedMessage, model);
        List<Person> result = model.getFilteredPersonList();
        assertTrue(result.stream().allMatch(Person::isTutor));
    }

    @Test
    public void execute_withCriteria_recommendStudents() {
        // ALICE is a tutor, recommend students based on level only
        RecommendCommand command =
                new RecommendCommand(INDEX_FIRST_PERSON, false, true, false);
        String expectedMessage = RecommendCommand.MESSAGE_SUCCESS_STUDENTS;
        assertCommandSuccess(command, model, expectedMessage, model);
        List<Person> result = model.getFilteredPersonList();
        assertTrue(result.stream().allMatch(Person::isStudent));
    }

    @Test
    public void execute_noMatch() {
        // FIONA is a student, recommend tutors with impossible criteria (using FIONA's index)
        RecommendCommand command =
                new RecommendCommand(INDEX_SIXTH_PERSON, true, true, true);
        String expectedMessage = RecommendCommand.MESSAGE_SUCCESS_TUTORS;
        expectedModel.updateFilteredPersonList(p -> false); // No match
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        RecommendCommand command =
                new RecommendCommand(INDEX_FIRST_PERSON, true, false, true);
        String expected =
                new RecommendCommand(INDEX_FIRST_PERSON, true, false, true).toString();
        assertEquals(expected, command.toString());
    }
}
