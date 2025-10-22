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
        var list = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= list.size()) {
            throw new CommandException("Invalid person index.");
        }

        Person a = list.get(targetIndex.getZeroBased());
        Person b = a.getMatchedPerson();
        if (b == null) {
            throw new CommandException(a.getName() + " is not matched with anyone. Cannot create a session.");
        }

        // 1) Clone both, preserving IDs (constructor order matches your sample)
        Person aClone = clonePreservingId(a);
        Person bClone = clonePreservingId(b);

        // 2) Attach the same session to both clones
        aClone.setSession(session);
        bClone.setSession(session);

        // 3) Re-link the matched references to the *clones*, not the old objects
        aClone.setMatchedPerson(bClone);
        bClone.setMatchedPerson(aClone);

        // 4) Replace in model to trigger UI updates
        model.setPerson(a, aClone);
        model.setPerson(b, bClone);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(
                "Added session for %s and their matched partner %s:\n%s",
                aClone.getName(), bClone.getName(), session));
    }

    /** Your cloning helper — preserves personId and copies fields/tags. */
    private Person clonePreservingId(Person p) {
        return new Person(
                p.getRole(),
                p.getName(),
                p.getPhone(),
                p.getEmail(),
                p.getAddress(),
                p.getSubject(),
                p.getLevel(),
                p.getPrice(),
                new java.util.HashSet<>(p.getTags()),
                p.getPersonId() // non-incrementing constructor
        );
    }
}
