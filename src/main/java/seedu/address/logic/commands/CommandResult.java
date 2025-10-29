package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Stats information should be shown to the user. */
    private final boolean showStats;

    /** The application should exit. */
    private final boolean exit;

    /** optional payload for stats window content. */
    private final String statsText;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showStats, boolean exit) {
        this(feedbackToUser, null, showHelp, showStats, exit);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * with {@code statsText} other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, String statsText, boolean showHelp, boolean showStats, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.statsText = statsText;
        this.showHelp = showHelp;
        this.showStats = showStats;
        this.exit = exit;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * Returns the optional stats text payload for the stats window. May be null.
     */
    public String getStatsText() {
        return statsText;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && Objects.equals(statsText, otherCommandResult.statsText)
                && showHelp == otherCommandResult.showHelp
                && showStats == otherCommandResult.showStats
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, statsText, showHelp, showStats, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
