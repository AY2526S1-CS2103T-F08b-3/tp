package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.statistics.TutorStatistics;
import seedu.address.model.statistics.TutorStatisticsCalculator;

/**
 * Displays statistics about the persons in the address book.
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays statistics about the persons in the address book.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        TutorStatisticsCalculator tutorCalc = new TutorStatisticsCalculator(model.getAddressBook().getPersonList());
        TutorStatistics tutorStats = tutorCalc.calculate();
        String studentStats = "students hereeeeeeeeeeeee"; //todo: implement student statistics

        String result = "================ CONNECTED STATISTICS ================\n"
                + "Tutors | Students\n"
                + "-----------------------------------------------------\n"
                + tutorStats + "\n"
                + studentStats + "\n"
                + "======================================================\n";

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof StatsCommand;
    }
}
