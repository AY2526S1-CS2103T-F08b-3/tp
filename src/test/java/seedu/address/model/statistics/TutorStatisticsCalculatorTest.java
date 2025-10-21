// src/test/java/seedu/address/model/statistics/TutorStatisticsCalculatorTest.java
package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class TutorStatisticsCalculatorTest {

    private int parseMidpoint(String priceRange) {
        String[] parts = priceRange.split("-");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);
        return (int) Math.round((min + max) / 2.0);
    }

    @Test
    public void calculate_typicalAddressBook_returnsExpectedStatistics() {
        List<Person> persons = TypicalPersons.getTypicalAddressBook().getPersonList();
        TutorStatisticsCalculator tutorCalculator = new TutorStatisticsCalculator(persons);
        Statistics stats = tutorCalculator.calculate();

        List<Person> tutors = persons.stream()
                .filter(Person::isTutor)
                .toList();

        int expectedTotal = tutors.size();
        int expectedAveragePrice = (int) Math.round(
                tutors.stream()
                      .mapToInt(t -> parseMidpoint(t.getPrice().toString()))
                      .average()
                      .orElse(0));
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (Person t : tutors) {
            String subj = t.getSubject().toString();
            freq.put(subj, freq.getOrDefault(subj, 0) + 1);
        }
        int maxCount = freq.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        Set<String> topSubjects = freq.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        assertEquals(expectedTotal, stats.getTotalPersons());
        assertEquals(expectedAveragePrice, stats.getAveragePrice());
        assertTrue(topSubjects.contains(stats.getMostCommonSubject()));
    }

    @Test
    public void calculate_noTutors_returnsZeroAndNaSubject() {
        AddressBook ab = new AddressBook();
        // add only students from TypicalPersons
        for (Person p : TypicalPersons.getTypicalPersons()) {
            if (p.isStudent()) {
                ab.addPerson(p);
            }
        }

        List<Person> persons = ab.getPersonList();
        TutorStatisticsCalculator tutorCalculator = new TutorStatisticsCalculator(persons);
        Statistics stats = tutorCalculator.calculate();

        assertEquals(0, stats.getTotalPersons());
        assertEquals(0, stats.getAveragePrice());
        assertEquals("N/A", stats.getMostCommonSubject());
    }
}
