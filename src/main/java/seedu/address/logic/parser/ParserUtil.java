package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_ROLE = "Invalid role.";

    /**
     * Parses {@code role} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static String parseRole(String role) throws ParseException {
        String trimmedRole = role.trim();
        if (!StringUtil.isValidRole(trimmedRole)) {
            throw new ParseException(MESSAGE_INVALID_ROLE);
        }
        return trimmedRole;
    }

    /**
     * Parses {@code role} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static String parseIndivRole(String role) throws ParseException {
        String trimmedRole = role.trim();
        if (!StringUtil.isValidIndivRole(trimmedRole)) {
            throw new ParseException(MESSAGE_INVALID_ROLE);
        }
        return trimmedRole;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses a {@code String level} into a {@code Level}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code level} is invalid.
     */
    public static Level parseLevel(String level) throws ParseException {
        requireNonNull(level);
        String trimmedLevel = level.trim();
        return Level.parse(trimmedLevel);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        return Price.parse(trimmedPrice);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String day} into a {@code DayOfWeek}.
     * Accepts full names (e.g., Monday, Tuesday) case-insensitive.
     *
     * @throws ParseException if the input is not a valid day name.
     */
    public static DayOfWeek parseDay(String day) throws ParseException {
        requireNonNull(day);
        try {
            return DayOfWeek.valueOf(day.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid day! Use a full day name (e.g. Monday, Tuesday, Wednesday).");
        }
    }

    /**
     * Parses a {@code String time} into a {@code LocalTime}.
     * Accepts 24-hour format HH:mm (e.g., 15:30).
     *
     * @throws ParseException if the input does not conform to HH:mm.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        try {
            return LocalTime.parse(time.trim(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid time format! Use HH:mm (e.g. 15:30).");
        }
    }

    /**
     * Parses a {@code String duration} into a {@code Duration}.
     * Accepts 24-hour format HH:mm (e.g., 1:30).
     *
     * @throws ParseException if the input does not conform to HH:mm.
     */
    public static Duration parseDuration(String durationStr) throws ParseException {
        requireNonNull(durationStr);
        String trimmed = durationStr.trim();

        if (!trimmed.matches("\\d{1,2}:\\d{2}")) {
            throw new ParseException("Invalid duration format! Use HH:mm (e.g. dur/01:30).");
        }

        try {
            final long totalMinutes = getTotalMinutes(trimmed);

            return Duration.ofMinutes(totalMinutes);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid duration numbers! Use HH:mm (e.g. dur/ 01:30).");
        }
    }

    private static long getTotalMinutes(String trimmed) throws ParseException {
        String[] parts = trimmed.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        if (hours < 0 || minutes < 0 || minutes >= 60) {
            throw new ParseException("Invalid duration! Hours must be >= 0 and minutes between 00–59.");
        }

        long totalMinutes = hours * 60L + minutes;
        if (totalMinutes == 0) {
            throw new ParseException("Duration must be greater than 00:00.");
        }
        return totalMinutes;
    }


}
