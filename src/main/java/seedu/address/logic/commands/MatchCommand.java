package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Matches a student and tutor together using their persistent IDs.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Matches a student with a tutor by their persistent IDs (in any order).\n"
            + "Parameters: ID1 ID2 (both positive integers)\n"
            + "Example: " + COMMAND_WORD + " 3 12";

    public static final String MESSAGE_NOT_FOUND = "Person with id #%d not found.";
    public static final String MESSAGE_SAME_ID = "You must provide two different IDs.";
    public static final String MESSAGE_INVALID_PAIR =
            "Both IDs must refer to one student and one tutor (order does not matter).";
    public static final String MESSAGE_ALREADY_MATCHED =
            "One or both persons are already matched.";
    public static final String MESSAGE_SUCCESS =
            "Matched student %s (id: #%d) with tutor %s (id: #%d)";

    private final int firstId;
    private final int secondId;

    /**
     * Constructor for instantiating a MatchCommand Object.
     * @param firstId Id of first person to be matched.
     * @param secondId Id of second person to be matched.
     */
    public MatchCommand(int firstId, int secondId) {
        this.firstId = firstId;
        this.secondId = secondId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (firstId == secondId) {
            throw new CommandException(MESSAGE_SAME_ID);
        }

        Person p1 = getById(model, firstId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_NOT_FOUND, firstId)));
        Person p2 = getById(model, secondId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_NOT_FOUND, secondId)));

        // Determine which is tutor and which is student (order-agnostic)
        Person tutor;
        Person student;
        if (p1.isTutor() && p2.isStudent()) {
            tutor = p1;
            student = p2;
        } else if (p1.isStudent() && p2.isTutor()) {
            student = p1;
            tutor = p2;
        } else {
            throw new CommandException(MESSAGE_INVALID_PAIR);
        }

        if (tutor.getMatchedPerson() != null || student.getMatchedPerson() != null
                || tutor.getMatchedStatus() || student.getMatchedStatus()) {
            throw new CommandException(MESSAGE_ALREADY_MATCHED);
        }

        // Clone both and preserve IDs (non-incrementing constructor)
        Person editedTutor = clonePreservingId(tutor);
        Person editedStudent = clonePreservingId(student);

        // Link both ways
        editedTutor.setMatchedPerson(editedStudent);
        editedStudent.setMatchedPerson(editedTutor);

        // Update model
        model.setPerson(tutor, editedTutor);
        model.setPerson(student, editedStudent);

        String msg = String.format(MESSAGE_SUCCESS,
                editedStudent.getName().fullName, editedStudent.getPersonId(),
                editedTutor.getName().fullName, editedTutor.getPersonId());
        return new CommandResult(msg);
    }

    private Optional<Person> getById(Model model, int id) {
        return model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getPersonId() == id)
                .findFirst();
    }

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
                p.getPersonId() // non-incrementing constructor
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MatchCommand
                && ((MatchCommand) other).firstId == this.firstId
                && ((MatchCommand) other).secondId == this.secondId);
    }
}
