package seedu.address.model.statistics;

/**
 * Calculator interface for producing Statistics results.
 */
public abstract class StatisticsCalculator {
    /**
     * Calculate and return the statistics result.
     * @return calculated statistics
     */
    public abstract Statistics calculate();
}
