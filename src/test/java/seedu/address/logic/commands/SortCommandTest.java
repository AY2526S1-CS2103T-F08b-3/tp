package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

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

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TUTORS);
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_TUTORS, "price");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTutorsByLevel_success() {
        List<String> sortFields = Arrays.asList("l/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TUTORS);
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_TUTORS, "level");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortStudentsByPrice_success() {
        List<String> sortFields = Arrays.asList("p/");
        SortCommand sortCommand = new SortCommand("students", sortFields);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_STUDENTS, "price");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortStudentsByLevel_success() {
        List<String> sortFields = Arrays.asList("l/");
        SortCommand sortCommand = new SortCommand("students", sortFields);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_STUDENTS, "level");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTutorsByPriceThenLevel_success() {
        List<String> sortFields = Arrays.asList("p/", "l/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TUTORS);
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_TUTORS, "price, then level");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortStudentsByLevelThenPrice_success() {
        List<String> sortFields = Arrays.asList("l/", "p/");
        SortCommand sortCommand = new SortCommand("students", sortFields);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_STUDENTS, "level, then price");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
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
    public void equals() {
        List<String> firstSortFields = Arrays.asList("p/");
        List<String> secondSortFields = Arrays.asList("l/");
        List<String> multipleSortFields = Arrays.asList("p/", "l/");

        SortCommand sortFirstCommand = new SortCommand("tutors", firstSortFields);
        SortCommand sortSecondCommand = new SortCommand("tutors", secondSortFields);
        SortCommand sortMultipleCommand = new SortCommand("tutors", multipleSortFields);
        SortCommand sortStudentsCommand = new SortCommand("students", firstSortFields);

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
    }

    @Test
    public void toStringMethod() {
        List<String> sortFields = Arrays.asList("p/", "l/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);
        String expected = SortCommand.class.getCanonicalName()
                + "{role=tutors, sortedBy=[p/, l/]}";
        // Note: This assumes SortCommand has a proper toString() method
        // If not implemented, you may need to add it to SortCommand
    }

    @Test
    public void execute_emptyList_success() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        List<String> sortFields = Arrays.asList("p/");
        SortCommand sortCommand = new SortCommand("tutors", sortFields);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TUTORS);
        expectedModel.sortPersons(sortFields);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS_TUTORS, "price");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }
}
