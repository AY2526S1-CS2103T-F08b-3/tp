package seedu.address.logic.commands;

import seedu.address.model.Model;

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
        // Implementation for displaying statistics goes here

        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof StatsCommand;
    }
}
