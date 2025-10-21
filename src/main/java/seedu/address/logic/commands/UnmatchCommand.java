package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unmatches a currently matched tutor–student pair, using the ID of either person.
 */
public class UnmatchCommand extends Command {

    public static final String COMMAND_WORD = "unmatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmatches a person and their paired partner, using the persistent ID of either person.\n"
            + "Parameters: PERSON_ID (positive integer)\n"
            + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_NOT_FOUND = "Person with id #%d not found.";
    public static final String MESSAGE_NOT_MATCHED = "Person with id #%d is not currently matched with anyone.";
    public static final String MESSAGE_SUCCESS = "Unmatched %s %s (id: #%d) and %s %s (id: #%d).";

    private final int personId;

    public UnmatchCommand(int personId) {
        this.personId = personId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the person by ID
        Person target = getById(model, personId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_NOT_FOUND, personId)));

        if (target.getMatchedPerson() == null) {
            throw new CommandException(String.format(MESSAGE_NOT_MATCHED, personId));
        }

        Person matched = target.getMatchedPerson();

        // Clone both persons (preserve IDs, no index increment)
        Person updatedTarget = clonePreservingId(target);
        Person updatedMatched = clonePreservingId(matched);

        // Clear their links
        updatedTarget.unsetMatchedPerson();
        updatedMatched.unsetMatchedPerson();

        // Persist both sides
        model.setPerson(target, updatedTarget);
        model.setPerson(matched, updatedMatched);

        String msg = String.format(MESSAGE_SUCCESS, updatedTarget.getRole(),
                updatedTarget.getName().fullName, updatedTarget.getPersonId(), updatedMatched.getRole(),
                updatedMatched.getName().fullName, updatedMatched.getPersonId());

        return new CommandResult(msg);
    }

    /**
     * Retrieves the Person object associated with the id.
     * @param model {@code Model} which the command should operate on.
     * @param id personId of the person trying to retrieve.
     * @return Person object associated with the given id.
     */
    private Optional<Person> getById(Model model, int id) {
        return model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getPersonId() == id)
                .findFirst();
    }

    /**
     * Creates a clone of the given {@code Person} object while preserving its unique identifier.
     * This method reuses the same {@code personId} from the original code parameters.
     * @param p The {@code Person} to clone.
     * @return A new {@code Person} instance with identical field values and the same {@code personId}.
     */
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
                new HashSet<>(p.getTags()),
                p.getPersonId()
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UnmatchCommand
                && ((UnmatchCommand) other).personId == this.personId);
    }
}
