package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class StudentStatisticsTest {

    private int parseMidpoint(String priceRange) {
        String[] parts = priceRange.split("-");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);
        return (int) Math.round((min + max) / 2.0);
    }

    @Test
    public void constructor_getters_returnValues() {
        List<Person> persons = TypicalPersons.getTypicalPersons();
        List<Person> students = persons.stream().filter(Person::isStudent).collect(Collectors.toList());

        int totalStudents = students.size();
        int averagePrice = (int) Math.round(
                students.stream()
                      .mapToInt(t -> parseMidpoint(t.getPrice().toString()))
                      .average()
                      .orElse(0));

        Map<String, Integer> freq = new LinkedHashMap<>();
        for (Person t : students) {
            String subj = t.getSubject().toString();
            freq.put(subj, freq.getOrDefault(subj, 0) + 1);
        }

        String mostCommonSubject = freq.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        String allSubjects = String.join(", ",
                freq.entrySet().stream()
                    .map(e -> e.getKey() + " (" + e.getValue() + ")")
                    .collect(Collectors.toList()));

        int matchedPersons = 0;

        Statistics stats = new StudentStatistics(totalStudents, averagePrice, mostCommonSubject,
                allSubjects, matchedPersons);

        assertEquals(totalStudents, stats.getTotalPersons());
        assertEquals(averagePrice, stats.getAveragePrice());
        assertEquals(mostCommonSubject, stats.getMostCommonSubject());
        assertEquals(allSubjects, stats.getAllSubjects());
        assertEquals(matchedPersons, stats.getMatchedPerson());
    }

    @Test
    public void toString_containsFormattedInfo() {
        List<Person> students = TypicalPersons.getTypicalPersons().stream()
                .filter(Person::isStudent)
                .collect(Collectors.toList());

        int totalStudents = students.size();
        int averagePrice = (int) Math.round(
                students.stream()
                      .mapToInt(t -> parseMidpoint(t.getPrice().toString()))
                      .average()
                      .orElse(0));

        Map<String, Integer> freq = new LinkedHashMap<>();
        for (Person t : students) {
            String subj = t.getSubject().toString();
            freq.put(subj, freq.getOrDefault(subj, 0) + 1);
        }

        String mostCommonSubject = freq.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        String allSubjects = String.join(", ",
                freq.entrySet().stream()
                        .map(e -> e.getKey() + " (" + e.getValue() + ")")
                        .collect(Collectors.toList()));

        Statistics stats = new StudentStatistics(totalStudents, averagePrice, mostCommonSubject, allSubjects, 0);
        String output = stats.toString();

        assertTrue(output.contains(String.format("Total Students: %d", totalStudents)));
        assertTrue(output.contains(String.format("Average Price: $%d", averagePrice)));
        assertTrue(output.contains(String.format("Most Common Subject: %s", mostCommonSubject)));
        assertTrue(output.contains(String.format("All Subjects: %s", allSubjects)));
        assertTrue(output.contains("Matched Persons: 0"));
    }

    @Test
    public void toString_handlesNullSubject() {
        StudentStatistics stats = new StudentStatistics(0, 0, null, "", 0);
        String output = stats.toString();

        assertTrue(output.contains("Most Common Subject: null"));
    }
}
