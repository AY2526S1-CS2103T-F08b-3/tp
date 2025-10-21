package seedu.address.model.statistics;

import java.util.LinkedHashMap;
import java.util.Map;

import seedu.address.model.person.Person;

/**
 * Represents statistics about students in the address book.
 */
public class StudentStatistics extends Statistics {
    private final int totalStudents;
    private final int averagePrice;
    private final String mostCommonSubject;
    private final String allSubjects;
    private final int matchedPerson;

    /**
     * Constructs a StudentStatistics object with the given statistics.
     *
     * @param totalStudents       Total number of students.
     * @param averagePrice        Average price (if applicable) for students.
     * @param mostCommonSubject   Most common subject among students.
     * @param matchedPerson       Number of matched persons (students).
     */
    public StudentStatistics(int totalStudents, int averagePrice, String mostCommonSubject, String allSubjects, int matchedPerson) {
        this.totalStudents = totalStudents;
        this.averagePrice = averagePrice;
        this.mostCommonSubject = mostCommonSubject;
        this.allSubjects = allSubjects;
        this.matchedPerson = matchedPerson;
    }

    @Override
    public int getAveragePrice() {
        return averagePrice;
    }

    @Override
    public String getMostCommonSubject() {
        return mostCommonSubject;
    }

    @Override
    public int getMatchedPerson() {
        return matchedPerson;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total Student(s): %d\n", totalStudents));
        sb.append(String.format("Average Price: $%d\n", averagePrice));
        sb.append(String.format("Most Common Subject: %s\n", mostCommonSubject));
        sb.append(String.format("All Subjects: %s\n", allSubjects));
        sb.append(String.format("Matched Person(s): %d", matchedPerson));
        return sb.toString();
    }
}
