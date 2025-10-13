package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        List<Person> built = new ArrayList<>();

        // 1) Build all persons & add to model
        for (JsonAdaptedPerson jap : persons) {
            Person p = jap.toModelType();
            if (addressBook.hasPerson(p)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(p);
            built.add(p);
        }

        // 2) Ensure all persons have IDs (assign if coming from old JSON)
        int maxId = built.stream().mapToInt(Person::getPersonId).max().orElse(0);
        int nextId = Math.max(1, maxId + 1);
        for (Person p : built) {
            if (p.getPersonId() == 0) { // or < 1 if you prefer
                p.setPersonId(nextId++);
            }
        }

        // 3) Continue static index from highest ID
        Person.setIndex(built.stream().mapToInt(Person::getPersonId).max().orElse(0) + 1);

        // 4) Lookup by ID
        Map<Integer, Person> byId = built.stream()
                .collect(Collectors.toMap(Person::getPersonId, x -> x));

        // 5) Resolve matches (second pass)
        for (int i = 0; i < persons.size(); i++) {
            JsonAdaptedPerson jap = persons.get(i);
            Integer mateId = jap.getMatchedPersonId();
            if (mateId == null) {
                continue;
            }
            Person me = built.get(i);
            Person mate = byId.get(mateId);
            if (mate != null && me.getMatchedPerson() == null) {
                me.setMatchedPerson(mate);
                if (mate.getMatchedPerson() == null) {
                    mate.setMatchedPerson(me);
                }
            }
        }

        return addressBook;
    }
}
