package dukecooks.model.profile.person;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents User's weight.
 */
public class Weight {
    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain numeric characters and represented by kilograms";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final int weight;

    public final String timestamp;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight.
     * @param datetime The timestamp of latest input of user's weight.
     */
    public Weight(String weight, String datetime) {
        requireNonNull(weight);
        AppUtil.checkArgument(isValidNumber(weight), MESSAGE_CONSTRAINTS);
        this.weight = Integer.parseInt(weight);
        this.timestamp = datetime;
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(weight);
    }

}
