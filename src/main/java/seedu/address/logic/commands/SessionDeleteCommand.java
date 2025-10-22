package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes the current session from both matched persons.
 */
public class SessionDeleteCommand extends Command {

    public static final String COMMAND_WORD = "sessiondelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the session of the specified person (must be matched).\n"
            + "Format: sessiondelete INDEX";

    private final Index targetIndex;

    /**
     * Constructor to instantiate a SessionDeleteCommand object.
     * @param targetIndex target index of the person on the displayed list.
     */
    public SessionDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (targetIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException("Invalid person index. Choose a person from within the displayed list.");
        }

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        Person matchedPerson = person.getMatchedPerson();

        if (matchedPerson == null) {
            throw new CommandException(person.getName() + " is not matched with anyone. Cannot delete a session.");
        }

        if (person.getSession() == null) {
            throw new CommandException("No active session to delete for " + person.getName() + ".");
        }

        person.clearSession();
        matchedPerson.clearSession();
        model.setPerson(person, person);
        model.setPerson(matchedPerson, matchedPerson);

        return new CommandResult(String.format(
                "Deleted session for %s and their matched partner %s.",
                person.getName(), matchedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SessionDeleteCommand)) {
            return false;
        }

        SessionDeleteCommand otherCommand = (SessionDeleteCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode();
    }
}
