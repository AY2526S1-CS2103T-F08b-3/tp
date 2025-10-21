package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.StatisticsCalculator;
import seedu.address.model.statistics.StudentStatisticsCalculator;
import seedu.address.model.statistics.TutorStatisticsCalculator;
import seedu.address.testutil.TypicalPersons;

public class StatsCommandTest {

    @Test
    public void execute_showsStatisticsAndResetsFilteredList() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        StatsCommand command = new StatsCommand();

        StatisticsCalculator tutorCalc =
                new TutorStatisticsCalculator(model.getAddressBook().getPersonList());

        StatisticsCalculator studentCalc =
                new StudentStatisticsCalculator(model.getAddressBook().getPersonList());
        Statistics tutorStats = tutorCalc.calculate();
        Statistics studentStats = studentCalc.calculate();

        String expected = "================ CONNECTED STATISTICS ================\n"
                + "TUTORS\n"
                + "-----------------------------------------------------\n"
                + tutorStats + "\n"
                + "======================================================\n"
                + "STUDENTS\n"
                + "-----------------------------------------------------\n"
                + studentStats + "\n"
                + "======================================================";

        CommandResult result = command.execute(model);

        assertEquals(expected, result.getFeedbackToUser());
        // model's filtered list should be reset to show all persons
        assertEquals(model.getAddressBook().getPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void equals_behavior() {
        StatsCommand first = new StatsCommand();
        StatsCommand second = new StatsCommand();

        // same object
        assertEquals(first, first);
        // different instances but same type -> considered equal by StatsCommand
        assertEquals(first, second);
        // not equal to null or other types
        assertNotEquals(first, null);
        assertNotEquals(first, new Object());
    }
}
