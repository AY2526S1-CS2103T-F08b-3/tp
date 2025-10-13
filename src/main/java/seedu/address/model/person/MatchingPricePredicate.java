package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person}'s price range overlaps or includes
 * any of the given prices. Used to filter persons based on their price range.
 */
public class MatchingPricePredicate implements Predicate<Person> {
    private final List<Price> prices;

    public MatchingPricePredicate(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public boolean test(Person person) {
        return prices.stream()
                .anyMatch(price -> person.getPrice().includes(price) || person.getPrice().overlaps(price));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MatchingPricePredicate)) {
            return false;
        }

        MatchingPricePredicate otherMatchingPricePredicate = (MatchingPricePredicate) other;
        return prices.equals(otherMatchingPricePredicate.prices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("prices", prices).toString();
    }
}

