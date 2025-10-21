package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Session;

/**
 * Adds a tutoring session for a matched tutor-student pair.
 */
public class SessionAddCommand extends Command {

    public static final String COMMAND_WORD = "sessionadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a session to the specified person (must be matched).\n"
            + "Format: sessionadd INDEX d/DAY t/TIME dur/DURATION s/SUBJECT p/PRICE\n"
            + "Example: sessionadd 1 d/Monday t/16:00 dur/02:00 s/Mathematics p/40";

    private final Index targetIndex;
    private final Session session;

    /**
     * Constructor for instantiating a MatchCommand Object.
     * @param targetIndex target index of the person on the displayed list.
     * @param session Session object to be assigned to the person.
     */
    public SessionAddCommand(Index targetIndex, Session session) {
        this.targetIndex = targetIndex;
        this.session = session;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (targetIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException("Invalid person index.");
        }

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        Person matchedPerson = person.getMatchedPerson();

        if (matchedPerson == null) {
            throw new CommandException(person.getName() + " is not matched with anyone. Cannot create a session.");
        }

        person.setSession(session);
        matchedPerson.setSession(session);
        model.setPerson(person, person);
        model.setPerson(matchedPerson, matchedPerson);

        return new CommandResult(String.format(
                "Added session for %s and their matched partner %s:\n%s",
                person.getName(), matchedPerson.getName(), session));
    }
}
