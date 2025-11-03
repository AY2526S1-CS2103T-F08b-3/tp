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
            + "Format: sessionadd INDEX d/DAY t/TIME dur/DURATION sbj/SUBJECT p/PRICE\n"
            + "Example: sessionadd 1 d/Monday t/16:00 dur/02:00 sbj/Mathematics p/40";

    public static final String MESSAGE_SUBJECT_MISMATCH =
            "Cannot add session: subject mismatch.\nPerson’s subject: %s\nSession’s subject: %s";
    public static final String MESSAGE_PRICE_OUT_OF_RANGE = "Cannot add session: price mismatch.\n Tutor’s price range:"
            + " %s\n Student’s price range: %s\n Session price: %s";
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
        ensureDurationWithinDay(session);
        ensureSameSubject(a, session);
        ensurePriceWithinIntersection(a, b, session);

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
                "Updated session for %s and their matched partner %s:\n%s",
                aClone.getName(), bClone.getName(), session));
    }
    /** Ensures the session duration is between 00:01 and 24:00. */
    private void ensureDurationWithinDay(Session s) throws CommandException {
        var d = s.getDuration();
        if (d.isNegative() || d.isZero() || d.compareTo(java.time.Duration.ofHours(24)) > 0) {
            throw new CommandException("Cannot add session: duration must be between 00:01 and 24:00.");
        }
    }
    /**
     * Ensures the session subject matches the person's subject.
     * If the subjects differ, this method throws a {@link CommandException}
     * and the session will not be added.
     * @param person  The person the session is being added to.
     * @param session The session to validate.
     * @throws CommandException if {@code session.subject} does not equal {@code person.subject}.
     */
    private void ensureSameSubject(Person person, Session session) throws CommandException {
        // Adjust getters to your API; e.g., person.getSubject(), session.getSubject()
        if (!person.getSubject().equals(session.getSubject())) {
            throw new CommandException(String.format(
                    MESSAGE_SUBJECT_MISMATCH, person.getSubject(), session.getSubject()
            ));
        }
    }
    /**
     * Ensures the session price is within BOTH matched persons' price ranges.Throws a detailed mismatch message if not.
     */
    private void ensurePriceWithinIntersection(Person t, Person s, Session sess) throws CommandException {
        int val = sess.getPrice().isSingle() ? Integer.parseInt(sess.getPrice().toString())
                : (Integer.parseInt(sess.getPrice().toString().split("-")[0])
                + Integer.parseInt(sess.getPrice().toString().split("-")[1])) / 2;
        if (!t.getPrice().includesSingle(val) || !s.getPrice().includesSingle(val)) {
            throw new CommandException(String.format(MESSAGE_PRICE_OUT_OF_RANGE, t.getPrice(), s.getPrice(), val));
        }
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SessionAddCommand)) {
            return false;
        }

        SessionAddCommand otherCommand = (SessionAddCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && session.equals(otherCommand.session);
    }
}
