package seedu.address.model.statistics;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;

/**
 * A utility class for calculating statistics about students in the address book.
 */
public class StudentStatisticsCalculator extends StatisticsCalculator {

    private final List<Person> students;

    public StudentStatisticsCalculator(List<Person> persons) {
        this.students = persons.stream().filter(Person::isStudent).toList();
    }

    /**
     * Calculates statistics about students from the given list of persons.
     * @return A StudentStatistics object containing the calculated statistics.
     */
    @Override
    public StudentStatistics calculate() {
        int totalStudents = students.size();
        int averagePrice = getAveragePrice();
        String mostCommonSubject = getMostCommonSubject();
        String allSubjects = getAllSubjects();
        int matchedStudents = countMatchedStudents(students);

        return new StudentStatistics(totalStudents, averagePrice, mostCommonSubject, allSubjects, matchedStudents);
    }

    private int getAveragePrice() {
        if (students.isEmpty()) {
            return 0;
        }
        int total = students.stream()
                .mapToInt(s -> Integer.parseInt(s.getAveragePrice().toString()))
                .sum();
        return total / students.size();
    }

    private String getMostCommonSubject() {
        if (students.isEmpty()) {
            return "N/A";
        }

        Map<String, Long> frequencyMap = students.stream()
                .collect(Collectors.groupingBy(s -> s.getSubject().toString(), Collectors.counting()));

        return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getAllSubjects() {
        Map<String, Integer> subjectCounts = new java.util.LinkedHashMap<>();

        for (Person p : students) {
            String subject = p.getSubject().toString();
            subjectCounts.put(subject, subjectCounts.getOrDefault(subject, 0) + 1);
        }

        if (subjectCounts.isEmpty()) {
            return "N/A";
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<String, Integer> entry : subjectCounts.entrySet()) {
            if (!isFirst) {
                sb.append(", ");
            }
            isFirst = false;
            sb.append(String.format("%s (%d)", entry.getKey(), entry.getValue()));
        }
        return sb.toString().trim();
    }

    private int countMatchedStudents(List<Person> students) {
        return (int) students.stream()
                .filter(Person::isMatched)
                .count();
    }

}
