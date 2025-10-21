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

public class StudentStatisticsCalculatorTest {

    private int parseMidpoint(String priceRange) {
        String[] parts = priceRange.split("-");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);
        return (int) Math.round((min + max) / 2.0);
    }

    @Test
    public void calculate_typicalAddressBook_returnsExpectedStatistics() {
        List<Person> persons = TypicalPersons.getTypicalAddressBook().getPersonList();
        StatisticsCalculator studentCalculator = new StudentStatisticsCalculator(persons);
        Statistics stats = studentCalculator.calculate();

        List<Person> students = persons.stream()
                .filter(Person::isStudent)
                .toList();

        int expectedTotal = students.size();
        int expectedAveragePrice = (int) Math.round(
                students.stream()
                      .mapToInt(t -> parseMidpoint(t.getPrice().toString()))
                      .average()
                      .orElse(0));
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (Person t : students) {
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
    public void calculate_noStudents_returnsZeroAndNaSubject() {
        AddressBook ab = new AddressBook();
        // add only tutors from TypicalPersons so the student calculator sees no students
        for (Person p : TypicalPersons.getTypicalPersons()) {
            if (p.isTutor()) {
                ab.addPerson(p);
            }
        }

        List<Person> persons = ab.getPersonList();
        StudentStatisticsCalculator studentCalculator = new StudentStatisticsCalculator(persons);
        Statistics stats = studentCalculator.calculate();

        assertEquals(0, stats.getTotalPersons());
        assertEquals(0, stats.getAveragePrice());
        assertEquals("N/A", stats.getMostCommonSubject());
    }
}
