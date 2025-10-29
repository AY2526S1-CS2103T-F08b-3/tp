package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.StatisticsCalculator;
import seedu.address.model.statistics.StudentStatisticsCalculator;
import seedu.address.model.statistics.TutorStatisticsCalculator;

/**
 * Displays statistics about the persons in the address book.
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays statistics about the persons in the address book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_STATS_MESSAGE = "Opened stats window.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        StatisticsCalculator tutorCalc = new TutorStatisticsCalculator(model.getAllPersonList());
        StatisticsCalculator studentCalc = new StudentStatisticsCalculator(model.getAllPersonList());
        Statistics tutorStats = tutorCalc.calculate();
        Statistics studentStats = studentCalc.calculate();

        String result = "=================== CONNECTED STATISTICS ===================\n"
                + "TUTORS\n"
                + "-----------------------------------------------------\n"
                + tutorStats + "\n"
                + "======================================================\n"
                + "STUDENTS\n"
                + "-----------------------------------------------------\n"
                + studentStats + "\n"
                + "======================================================";

        // feedback shown in output box remains SHOWING_STATS_MESSAGE,
        // full stats string is passed as the statsText payload for the stats window
        return new CommandResult(SHOWING_STATS_MESSAGE, result, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof StatsCommand;
    }
}
