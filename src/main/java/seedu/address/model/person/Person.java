package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private boolean isTutor = false;
    private boolean isStudent = false;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private Person matchedPerson;
    private Subject subject;
    private Level level;
    private Price price;
    private boolean isMatched = false;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Subject subject, Level level, Price price,
                   Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, subject, level, price, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.subject = subject;
        this.level = level;
        this.price = price;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public boolean getMatchedStatus() {
        return this.isMatched;
    }

    public void setTutor() {
        this.isTutor = true;
    }

    public void setStudent() {
        this.isStudent = true;
    }

    public boolean isTutor() {
        return isTutor;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setMatchedPerson(Person person) {
        this.matchedPerson = person;
    }

    /**
     * Attempts to match this person with another person.
     *
     * Rules:
     * - If either person is already matched, do nothing.
     * - A match is created only if one is a tutor and the other is a student.
     * - When matched, both persons update their matchedPerson reference to each other.
     * - If conditions are not met, do nothing.
     *
     * @param person the other Person to attempt matching with
     */
    public void matchPerson(Person person) {
        if (this.isMatched || person.getMatchedStatus()) {
            return;
        } else if (isTutor ^ person.isTutor() ) {
            this.matchedPerson = person;
            person.setMatchedPerson(this);
        } else {
            return;
        }
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}

