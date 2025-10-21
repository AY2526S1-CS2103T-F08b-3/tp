package seedu.address.model.statistics;

/**
 * Represents statistics about tutors in the address book.
 */
public class TutorStatistics {
    private final int totalTutors;
    private final int averagePrice;
    private final String mostCommonSubject;
    private final int matchedTutors;

    /**
     * Constructs a TutorStatistics object with the given statistics.
     *
     * @param totalTutors       Total number of tutors.
     * @param averagePrice      Average price of tutors.
     * @param mostCommonSubject Most common subject among tutors.
     * @param matchedTutors     Number of tutors matching specific criteria.
     */
    public TutorStatistics(int totalTutors, int averagePrice, String mostCommonSubject, int matchedTutors) {
        this.totalTutors = totalTutors;
        this.averagePrice = averagePrice;
        this.mostCommonSubject = mostCommonSubject;
        this.matchedTutors = matchedTutors;
    }

    public int getTotalTutors() {
        return totalTutors;
    }

    public int getAveragePrice() {
        return averagePrice;
    }

    public String getMostCommonSubject() {
        return mostCommonSubject;
    }

    public int getMatchedTutors() {
        return matchedTutors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total Tutors: %d\n", totalTutors));
        sb.append(String.format("Average Price: %d\n", averagePrice));
        sb.append(String.format("Most Common Subject: %s\n", mostCommonSubject));
        sb.append(String.format("Matched Tutors: %d\n", matchedTutors));
        return sb.toString();
    }
}
