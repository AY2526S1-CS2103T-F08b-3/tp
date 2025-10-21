package seedu.address.model.statistics;

/**
 * Represents statistics about tutors in the address book.
 */
public class TutorStatistics extends Statistics {
    private final int totalTutors;
    private final int averagePrice;
    private final String mostCommonSubject;
    private final int matchedPerson;

    /**
     * Constructs a TutorStatistics object with the given statistics.
     *
     * @param totalTutors       Total number of tutors.
     * @param averagePrice      Average price of tutors.
     * @param mostCommonSubject Most common subject among tutors.
     * @param matchedPerson     Number of matched persons (tutors).
     */
    public TutorStatistics(int totalTutors, int averagePrice, String mostCommonSubject, int matchedPerson) {
        this.totalTutors = totalTutors;
        this.averagePrice = averagePrice;
        this.mostCommonSubject = mostCommonSubject;
        this.matchedPerson = matchedPerson;
    }

    @Override
    public int getTotalPersons() {
        return totalTutors;
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
        sb.append(String.format("Total Tutor(s): %d\n", totalTutors));
        sb.append(String.format("Average Price: $%d\n", averagePrice));
        sb.append(String.format("Most Common Subject: %s\n", mostCommonSubject));
        sb.append(String.format("Matched Person(s): %d", matchedPerson));
        return sb.toString();
    }
}
