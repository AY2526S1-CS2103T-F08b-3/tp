package seedu.address.model.util;


import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person("tutor", new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Subject("Mathematics"), Level.parse("2-5"),
                    Price.parse("30-45"), Collections.emptySet(), 1),

            new Person("student", new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Subject("Science"), Level.parse("2"),
                    Price.parse("30"), Collections.emptySet(), 2),

            new Person("tutor", new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Subject("English"), Level.parse("3-6"),
                    Price.parse("35-50"), Collections.emptySet(), 3),

            new Person("student", new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Subject("Mathematics"), Level.parse("3"),
                    Price.parse("35"), Collections.emptySet(), 4),

            new Person("tutor", new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Subject("Science"), Level.parse("1-4"),
                    Price.parse("30-60"), Collections.emptySet(), 5),

            new Person("student", new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Subject("English"), Level.parse("4"),
                    Price.parse("40"), Collections.emptySet(), 6),

            new Person("tutor", new Name("Meera Tan"), new Phone("98112233"),
                    new Email("meera@example.com"), new Address("Blk 22 Toa Payoh Lorong 5, #03-12"),
                    new Subject("Mathematics"), Level.parse("1-3"), Price.parse("20-35"),
                    Collections.emptySet(), 7),

            new Person("tutor", new Name("Samuel Ong"), new Phone("96554433"),
                    new Email("samuelong@example.com"), new Address("Blk 120 Jurong East Street 13, #10-21"),
                    new Subject("English"), Level.parse("1-4"), Price.parse("25-40"),
                    Collections.emptySet(), 8),

            new Person("student", new Name("Jia Wei Lim"), new Phone("88990011"),
                    new Email("jiawei.lim@example.com"), new Address("Blk 18 Bishan Street 24, #05-19"),
                    new Subject("Mathematics"), Level.parse("2"), Price.parse("25"),
                    Collections.emptySet(), 9),

            new Person("student", new Name("Priya Nair"), new Phone("87991234"),
                    new Email("priyanair@example.com"), new Address("Blk 210 Ang Mo Kio Ave 10, #07-08"),
                    new Subject("Mathematics"), Level.parse("5"), Price.parse("40"),
                    Collections.emptySet(), 10),

            new Person("student", new Name("Hannah Lee"), new Phone("90112233"),
                    new Email("hannah.lee@example.com"), new Address("Blk 55 Bukit Batok East Ave 5, #02-09"),
                    new Subject("Science"), Level.parse("1"), Price.parse("35"),
                    Collections.emptySet(), 11),

            new Person("student", new Name("Marcus Goh"), new Phone("83445566"),
                    new Email("marcusgoh@example.com"), new Address("Blk 77 Hougang Ave 3, #14-02"),
                    new Subject("Science"), Level.parse("4"), Price.parse("55"),
                    Collections.emptySet(), 12),

            new Person("student", new Name("Zhi Yuan Koh"), new Phone("94556677"),
                    new Email("zhiyuan.koh@example.com"), new Address("Blk 9 Yishun Ring Road, #12-27"),
                    new Subject("English"), Level.parse("3"), Price.parse("35"),
                    Collections.emptySet(), 13),

            new Person("student", new Name("Nurul Aisyah"), new Phone("87776655"),
                    new Email("nurul.aisyah@example.com"), new Address("Blk 3 Pasir Ris Drive 6, #04-15"),
                    new Subject("English"), Level.parse("2"), Price.parse("28"),
                    Collections.emptySet(), 14),

            new Person("student", new Name("Wei Ming Tan"), new Phone("91334455"),
                    new Email("weiming.tan@example.com"), new Address("Blk 402 Sembawang Drive, #08-41"),
                    new Subject("Mathematics"), Level.parse("1"), Price.parse("22"),
                    Collections.emptySet(), 15),

            new Person("student", new Name("Chloe Ng"), new Phone("96778899"),
                    new Email("chloeng@example.com"), new Address("Blk 66 Punggol Field, #16-18"),
                    new Subject("Science"), Level.parse("3"), Price.parse("30"),
                    Collections.emptySet(), 16)

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
