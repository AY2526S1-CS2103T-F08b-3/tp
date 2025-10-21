package seedu.address.model.statistics;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;

/**
 * A utility class for calculating statistics about tutors in the address book.
 */
public class TutorStatisticsCalculator {

    private final List<Person> tutors;

    private TutorStatisticsCalculator(List<Person> persons) {
        this.tutors = persons.stream().filter(Person::isTutor).toList();
    }

    /**
     * Calculates statistics about tutors from the given list of persons.
     * @return A TutorStatistics object containing the calculated statistics.
     */
    public TutorStatistics calculate() {
        int totalTutors = tutors.size();
        int averagePrice = getAveragePrice();
        String mostCommonSubject = getMostCommonSubject();
        int matchedTutors = countMatchedTutors(tutors);

        return new TutorStatistics(totalTutors, averagePrice, mostCommonSubject, matchedTutors);
    }

    public int getAveragePrice() {
        if (tutors.isEmpty()) {
            return 0;
        }
        int total = tutors.stream()
                .mapToInt(t -> Integer.parseInt(t.getAveragePrice().toString()))
                .sum();
        return total / tutors.size();
    }

    public String getMostCommonSubject() {
        if (tutors.isEmpty()) {
            return "N/A";
        }

        Map<String, Long> frequencyMap = tutors.stream()
                .collect(Collectors.groupingBy(t -> t.getSubject().toString(), Collectors.counting()));

        return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private int countMatchedTutors(List<Person> tutors) {
        return (int) tutors.stream()
                .filter(Person::isMatched)
                .count();
    }

}
