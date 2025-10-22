package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person}'s price range overlaps with any of the given prices.
 * Used to filter persons based on their price range during a recommend operation.
 */
public class OverlappingPricePredicate implements Predicate<Person> {
    private final List<Price> prices;

    public OverlappingPricePredicate(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public boolean test(Person person) {
        return prices.stream()
                .anyMatch(price -> person.getPrice().overlaps(price));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OverlappingPricePredicate)) {
            return false;
        }

        OverlappingPricePredicate otherOverlappingPricePredicate = (OverlappingPricePredicate) other;
        return prices.equals(otherOverlappingPricePredicate.prices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("prices", prices).toString();
    }
}
