package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;


/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setRole(person.getRole());
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the subject for this EditPersonDescriptorBuilder using the given string.
     *
     * @param subjectString the subject name to set
     * @return this builder with the subject field set
     */
    public EditPersonDescriptorBuilder withSubject(String subjectString) {
        descriptor.setSubject(new Subject(subjectString));
        return this;
    }


    /**
     * Sets the level for this EditPersonDescriptorBuilder using the given string.
     * Accepts a positive integer or a range (e.g. "3" or "1-6").
     *
     * @param levelString the level string to set
     * @return this builder with the level field set
     */
    public EditPersonDescriptorBuilder withLevel(String levelString) {
        if (levelString == null || levelString.isBlank()) {
            descriptor.setLevel(new Level(1, 1));
            return this;
        }

        try {
            int start;
            int end;

            if (levelString.contains("-")) {
                String[] parts = levelString.split("-");
                start = Integer.parseInt(parts[0].trim());
                end = Integer.parseInt(parts[1].trim());
            } else {
                start = Integer.parseInt(levelString.trim());
                end = start;
            }

            if (start <= 0 || end <= 0 || start > end) {
                // fall back to a safe default
                descriptor.setLevel(new Level(1, 1));
            } else {
                descriptor.setLevel(new Level(start, end));
            }

        } catch (NumberFormatException e) {
            descriptor.setLevel(new Level(1, 1));
        }

        return this;
    }


    /**
     * Sets the price for this EditPersonDescriptorBuilder using the given string.
     * Accepts a positive integer or a range (e.g. "30" or "20-40").
     *
     * @param priceString the price string to set
     * @return this builder with the price field set
     */
    public EditPersonDescriptorBuilder withPrice(String priceString) {
        if (priceString == null || priceString.isBlank()) {
            descriptor.setPrice(new Price(1, 1));
            return this;
        }

        try {
            int start;
            int end;

            if (priceString.contains("-")) {
                String[] parts = priceString.split("-");
                start = Integer.parseInt(parts[0].trim());
                end = Integer.parseInt(parts[1].trim());
            } else {
                start = Integer.parseInt(priceString.trim());
                end = start;
            }

            if (start <= 0 || end <= 0 || start > end) {
                descriptor.setPrice(new Price(1, 1));
            } else {
                descriptor.setPrice(new Price(start, end));
            }

        } catch (NumberFormatException e) {
            descriptor.setPrice(new Price(1, 1));
        }

        return this;
    }



    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
