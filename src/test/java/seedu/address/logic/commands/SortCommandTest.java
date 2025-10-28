package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortTutorsByPrice_success() {
        List<String> sortFields = Arrays.asList("p/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        expectedModel.updateFilteredPersonList(
                expectedModel.getFilterPredicate().and(Model.PREDICATE_SHOW_ALL_TUTORS));
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_TUTORS, "price");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTutorsByLevel_success() {
        List<String> sortFields = Arrays.asList("l/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        expectedModel.updateFilteredPersonList(
                expectedModel.getFilterPredicate().and(Model.PREDICATE_SHOW_ALL_TUTORS));
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_TUTORS, "level");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortStudentsByPrice_success() {
        List<String> sortFields = Arrays.asList("p/");
        SortCommand sortCommand = new SortCommand("students", sortFields);

        expectedModel.updateFilteredPersonList(
                expectedModel.getFilterPredicate().and(Model.PREDICATE_SHOW_ALL_STUDENTS));
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_STUDENTS, "price");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortStudentsByLevel_success() {
        List<String> sortFields = Arrays.asList("l/");
        SortCommand sortCommand = new SortCommand("students", sortFields);

        expectedModel.updateFilteredPersonList(
                expectedModel.getFilterPredicate().and(Model.PREDICATE_SHOW_ALL_STUDENTS));
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_STUDENTS, "level");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTutorsByPriceThenLevel_success() {
        List<String> sortFields = Arrays.asList("p/", "l/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        expectedModel.updateFilteredPersonList(
                expectedModel.getFilterPredicate().and(Model.PREDICATE_SHOW_ALL_TUTORS));
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_TUTORS, "price, then level");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortStudentsByLevelThenPrice_success() {
        List<String> sortFields = Arrays.asList("l/", "p/");
        SortCommand sortCommand = new SortCommand("students", sortFields);

        expectedModel.updateFilteredPersonList(
                expectedModel.getFilterPredicate().and(Model.PREDICATE_SHOW_ALL_STUDENTS));
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_STUDENTS, "level, then price");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_reset_success() {
        SortCommand sortCommand = new SortCommand("reset");

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.sortPersons(List.of());

        String expectedMessage = SortCommand.MESSAGE_SUCCESS_RESET;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_returnsEmptyMessage() {
        model = new ModelManager();

        List<String> sortFields = Arrays.asList("p/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        CommandResult result = sortCommand.execute(model);
        assertEquals("List is empty, there is nothing to sort.", result.getFeedbackToUser());
    }

    @Test
    public void execute_noTutorsFound_returnsNoTutorsMessage() {
        // Assuming getTypicalAddressBook has no tutors, or filter out all tutors
        model.updateFilteredPersonList(person -> false); // Filter out everything first

        List<String> sortFields = Arrays.asList("p/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        CommandResult result = sortCommand.execute(model);
        assertEquals("No tutors found to sort.", result.getFeedbackToUser());
    }

    @Test
    public void execute_noStudentsFound_returnsNoStudentsMessage() {
        // Filter out everything first
        model.updateFilteredPersonList(person -> false);

        List<String> sortFields = Arrays.asList("p/");
        SortCommand sortCommand = new SortCommand("students", sortFields);

        CommandResult result = sortCommand.execute(model);
        assertEquals("No students found to sort.", result.getFeedbackToUser());
    }

    @Test
    public void execute_resetEmptyList_returnsEmptyMessage() {
        model = new ModelManager();

        SortCommand sortCommand = new SortCommand("reset");

        CommandResult result = sortCommand.execute(model);
        assertEquals("List is empty, there is nothing to sort.", result.getFeedbackToUser());
    }

    @Test
    public void buildCriteriaDescription_singleCriteria_correctDescription() {
        SortCommand sortCommand = new SortCommand("tutors", Arrays.asList("p/"));
        assertEquals("price", sortCommand.buildCriteriaDescription());

        sortCommand = new SortCommand("tutors", Arrays.asList("l/"));
        assertEquals("level", sortCommand.buildCriteriaDescription());
    }

    @Test
    public void buildCriteriaDescription_multipleCriteria_correctDescription() {
        SortCommand sortCommand = new SortCommand("tutors", Arrays.asList("p/", "l/"));
        assertEquals("price, then level", sortCommand.buildCriteriaDescription());

        sortCommand = new SortCommand("students", Arrays.asList("l/", "p/"));
        assertEquals("level, then price", sortCommand.buildCriteriaDescription());
    }

    @Test
    public void buildCriteriaDescription_emptyCriteria_returnsEmptyString() {
        SortCommand sortCommand = new SortCommand("reset");
        assertEquals("", sortCommand.buildCriteriaDescription());
    }

    @Test
    public void equals() {
        List<String> firstSortFields = Arrays.asList("p/");
        List<String> secondSortFields = Arrays.asList("l/");
        List<String> multipleSortFields = Arrays.asList("p/", "l/");

        SortCommand sortFirstCommand = new SortCommand("tutors", firstSortFields);
        SortCommand sortSecondCommand = new SortCommand("tutors", secondSortFields);
        SortCommand sortMultipleCommand = new SortCommand("tutors", multipleSortFields);
        SortCommand sortStudentsCommand = new SortCommand("students", firstSortFields);
        SortCommand resetCommand = new SortCommand("reset");

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand("tutors", Arrays.asList("p/"));
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different sort fields -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));

        // different number of sort fields -> returns false
        assertFalse(sortFirstCommand.equals(sortMultipleCommand));

        // different role -> returns false
        assertFalse(sortFirstCommand.equals(sortStudentsCommand));

        // reset command equals itself
        assertTrue(resetCommand.equals(resetCommand));

        // reset command with same role
        SortCommand resetCommandCopy = new SortCommand("reset");
        assertTrue(resetCommand.equals(resetCommandCopy));

        // reset command different from regular sort
        assertFalse(resetCommand.equals(sortFirstCommand));
    }

    @Test
    public void toStringMethod() {
        List<String> sortFields = Arrays.asList("p/", "l/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);
        String expected = SortCommand.class.getCanonicalName()
                + "{role=tutors, sortedBy=[p/, l/]}";
        assertEquals(expected, sortCommand.toString());
    }

    @Test
    public void toStringMethod_reset() {
        SortCommand sortCommand = new SortCommand("reset");
        String expected = SortCommand.class.getCanonicalName()
                + "{role=reset, sortedBy=[]}";
        assertEquals(expected, sortCommand.toString());
    }
}