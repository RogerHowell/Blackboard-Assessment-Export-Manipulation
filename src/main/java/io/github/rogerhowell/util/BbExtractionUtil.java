package io.github.rogerhowell.util;


import io.github.rogerhowell.validation.ParameterValidationFailException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BbExtractionUtil {


    public static final String REGEX_FILE_CONTENTS = "" +
                                                     "Name: ?(.*)\\((.*)\\)[\\n\\r]+" +
                                                     "Assignment: ?(.*)[\\n\\r]+" +
                                                     "Date Submitted: ?(.*)[\\n\\r]+" +
                                                     "Current Mark: ?(.*)[\\n\\r]+" +
                                                     "Submission Field:[\\n\\r]+" +
                                                     "(.*)[\\n\\r]+" +
                                                     "Comments:[\\n\\r]+" +
                                                     "(.*)[\\n\\r]+" +
                                                     "Files:[\\n\\r]+" +
                                                     "(.*)[\\n\\r]+" +
                                                     "";

    public static final String REGEX_FILE_ENTRY_STRING = "\\s*Original filename: (.*)\\s*Filename: (.*)";

    public static final String REGEX_SUBMISSION_FILE_NAME_PATTERN = "([a-zA-Z0-9 ]+)_([a-z0-9]+)_attempt_(\\d{4})-(\\d{2})-(\\d{2})-(\\d{2})-(\\d{2})-(\\d{2})\\.txt";

    public static final String REGEX_BB_EXPORT_FILE_NAME_PATTERN = "gradebook_([a-zA-Z0-9 ]+)_([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_(\\d{4})-(\\d{2})-(\\d{2})-(\\d{2})-(\\d{2})-(\\d{2})\\.zip";

    public static final ZoneId DEFAULT_TIMEZONE = ZoneId.of("Europe/London");


    public static final DateTimeFormatter BB_EXPORT_FILE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    public static final DateTimeFormatter ISO_INSTANT_MILLIS = new DateTimeFormatterBuilder().parseCaseInsensitive()
                                                                                             .appendInstant(3)
                                                                                             .toFormatter();


    public static ZonedDateTime bbExportFile_TimestampToZonedDateTime(final String filename) {
        final Matcher matcher = BbExtractionUtil.getMatcher(BbExtractionUtil.REGEX_BB_EXPORT_FILE_NAME_PATTERN, filename);

        final int year       = Integer.parseInt(matcher.group(4), 10);
        final int month      = Integer.parseInt(matcher.group(5), 10);
        final int dayOfMonth = Integer.parseInt(matcher.group(6), 10);
        final int hour       = Integer.parseInt(matcher.group(7), 10);
        final int minute     = Integer.parseInt(matcher.group(8), 10);
        final int second     = Integer.parseInt(matcher.group(9), 10);

        final ZonedDateTime zonedDateTime = ZonedDateTime.of(
                year, month, dayOfMonth,
                hour, minute, second, 0,
                BbExtractionUtil.DEFAULT_TIMEZONE
        );

        return zonedDateTime;
    }


    public static String bbExportFile_cohortYear(final String filename) {
        final Matcher matcher = BbExtractionUtil.getMatcher(BbExtractionUtil.REGEX_BB_EXPORT_FILE_NAME_PATTERN, filename);
        return matcher.group(1);
    }


    public static String bbExportFile_moduleCode(final String filename) {
        final Matcher matcher = BbExtractionUtil.getMatcher(BbExtractionUtil.REGEX_BB_EXPORT_FILE_NAME_PATTERN, filename);
        return matcher.group(2);
    }


    public static String bbExportFile_taskName(final String filename) {
        final Matcher matcher = BbExtractionUtil.getMatcher(BbExtractionUtil.REGEX_BB_EXPORT_FILE_NAME_PATTERN, filename);
        return matcher.group(3);
    }


    /**
     * Utility function to regenerate an expected zip filename for a Blackboard assessment export file, given the relevant data.
     *
     * Given components, joined with an underscore character (`_`).
     * Example: gradebook_2019_CS9999_Task201_2019-11-08-21-41-57.zip
     * Note that `20` is an encoded space (`%20`) with the percent symbol stripped.
     */
    public static String constructFilename(final String cohortYear, final String moduleCode, final String encodedTaskName, final String exportTimestampString) {
        final String   delimeter  = "_";
        final String[] components = new String[] { "gradebook", cohortYear, moduleCode, encodedTaskName, exportTimestampString };

        return String.join(delimeter, components);
    }


    /**
     * Utility function to encode a string in the same way that Blackboard appears to do so.
     *
     * Note Blackboard seems to encode spaces as `%20` (an encoded space char), BUT THEN strips the `%`.
     *
     * @param string Input string
     * @return Encoded string
     */
    public static String encodeAsFilename(final String string) {
        return string.replaceAll(" ", "%20").replaceAll("%", "");
    }


    private static Matcher getMatcher(final String regex, final String input) {
        final Pattern foo        = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher    = foo.matcher(input);
        final boolean matchFound = matcher.matches();

        if (input != null && !matchFound) {
            throw new ParameterValidationFailException("Input doesn't match the required pattern. -- " +
                                                       "\n - Pattern: " + regex +
                                                       "\n - Input:   " + input);
        }

        return matcher;
    }


    /**
     * Utility function to generate a zoned date time in the default timezone.
     *
     * Default timezone: Europe/London (see {@link #DEFAULT_TIMEZONE} )
     * Default nanoseconds: 0
     *
     * @param year       Year
     * @param month      Month of year (1=Jan, 12-Dec)
     * @param dayOfMonth Day of the month
     * @param hour       Hour of the day
     * @param minute     Minute of the hour
     * @param second     Second of the minute.
     * @return JS ZonedDateTime object.
     */
    public static ZonedDateTime toZonedDateTime(final int year, final int month, final int dayOfMonth,
                                                final int hour, final int minute, final int second) {
        return ZonedDateTime.of(
                year, month, dayOfMonth,
                hour, minute, second, 0,
                BbExtractionUtil.DEFAULT_TIMEZONE
        );
    }


}
