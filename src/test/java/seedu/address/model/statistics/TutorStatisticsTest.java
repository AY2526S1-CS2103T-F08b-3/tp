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

public class TutorStatisticsTest {

    private int parseMidpoint(String priceRange) {
        String[] parts = priceRange.split("-");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);
        return (int) Math.round((min + max) / 2.0);
    }

    @Test
    public void constructor_getters_returnValues() {
        List<Person> persons = TypicalPersons.getTypicalPersons();
        List<Person> tutors = persons.stream().filter(Person::isTutor).collect(Collectors.toList());

        int totalTutors = tutors.size();
        int averagePrice = (int) Math.round(
                tutors.stream()
                      .mapToInt(t -> parseMidpoint(t.getPrice().toString()))
                      .average()
                      .orElse(0));

        Map<String, Integer> freq = new LinkedHashMap<>();
        for (Person t : tutors) {
            String subj = t.getSubject().toString();
            freq.put(subj, freq.getOrDefault(subj, 0) + 1);
        }
        String mostCommonSubject = freq.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        int matchedTutors = 0;

        TutorStatistics stats = new TutorStatistics(totalTutors, averagePrice, mostCommonSubject, matchedTutors);

        assertEquals(totalTutors, stats.getTotalTutors());
        assertEquals(averagePrice, stats.getAveragePrice());
        assertEquals(mostCommonSubject, stats.getMostCommonSubject());
        assertEquals(matchedTutors, stats.getMatchedTutors());
    }

    @Test
    public void toString_containsFormattedInfo() {
        List<Person> tutors = TypicalPersons.getTypicalPersons().stream()
                .filter(Person::isTutor)
                .collect(Collectors.toList());

        int totalTutors = tutors.size();
        int averagePrice = (int) Math.round(
                tutors.stream()
                      .mapToInt(t -> parseMidpoint(t.getPrice().toString()))
                      .average()
                      .orElse(0));
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (Person t : tutors) {
            String subj = t.getSubject().toString();
            freq.put(subj, freq.getOrDefault(subj, 0) + 1);
        }
        String mostCommonSubject = freq.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        TutorStatistics stats = new TutorStatistics(totalTutors, averagePrice, mostCommonSubject, 0);
        String output = stats.toString();

        assertTrue(output.contains(String.format("Total Tutors: %d", totalTutors)));
        assertTrue(output.contains(String.format("Average Price: %d", averagePrice)));
        assertTrue(output.contains(String.format("Most Common Subject: %s", mostCommonSubject)));
        assertTrue(output.contains("Matched Tutors: 0"));
    }

    @Test
    public void toString_handlesNullSubject() {
        TutorStatistics stats = new TutorStatistics(0, 0, null, 0);
        String output = stats.toString();

        assertTrue(output.contains("Most Common Subject: null"));
    }
}
