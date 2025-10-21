package seedu.address.model.statistics;

/**
 * Marker interface for statistics result objects.
 */
public abstract class Statistics {

    public abstract int getAveragePrice();

    public abstract int getTotalPersons();

    public abstract String getMostCommonSubject();

    public abstract String getAllSubjects();

    public abstract int getMatchedPerson();
}
