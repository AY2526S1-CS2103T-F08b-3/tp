package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private Predicate<Person> currentPredicate = PREDICATE_SHOW_ALL_PERSONS;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public List<Person> getAllPersonList() {
        return addressBook.getPersonList();
    }

    @Override
    public String getRepeatedEntry(Person person) {
        return addressBook.getRepeatedEntry(person);
    }

    @Override
    public Person getRepeatedPerson(Person person) {
        return addressBook.getRepeatedPerson(person);
    };

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    @Override
    public void sortPersons(List<String> sortedBy) {
        requireNonNull(sortedBy);

        Comparator<Person> comparator = null;

        for (int i = 0; i < sortedBy.size(); i++) {
            Comparator<Person> criteriaComparator = getComparatorForCriteria(sortedBy.get(i));

            if (comparator == null) {
                comparator = criteriaComparator;
            } else {
                comparator = comparator.thenComparing(criteriaComparator);
            }
        }

        if (comparator == null) {
            return;
        }

        addressBook.sortPersons(comparator);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        this.currentPredicate = predicate; // store the predicate
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public Predicate<Person> getFilterPredicate() {
        return currentPredicate;
    }

    /**
     * Creates a comparator for Person objects based on price or level.
     * Uses the first number for hyphenated values.
     *
     * @param criteria "p/" for price, "l/" for level comparison
     * @return Comparator for sorting Persons
     */
    public Comparator<Person> getComparatorForCriteria(String criteria) {
        switch(criteria) {
        case "p/":
            return Comparator.comparingInt(person -> {
                String priceString = person.getPrice().toString();
                if (priceString.contains("-")) {
                    return Integer.parseInt(priceString.split("-")[0]);
                }
                return Integer.parseInt(priceString);
            });
        case "l/":
            return Comparator.comparingInt(person -> {
                String levelString = person.getLevel().toString();
                if (levelString.contains("-")) {
                    return Integer.parseInt(levelString.split("-")[0]);
                }
                return Integer.parseInt(levelString);
            });
        default:
            return (p1, p2) -> 0;
        }
    }

}
