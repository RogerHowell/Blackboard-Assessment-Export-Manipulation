package io.github.rogerhowell.validation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public enum Validation {

    DISALLOW_EMPTY_STRING(
            "disallow_empty_string",
            "Value must be of type `String` and not be equal to an empty string `\"\"`.",
            value -> (value instanceof String) && !value.equals("")
    ),

    DISALLOW_NULL(
            "disallow_null",
            "Value must not be null.",
            Objects::nonNull
    ),

    FILE_MUST_EXIST(
            "file_exists_on_disk",
            "Parameter must be an object of type `Path` and it must exist on disk.",
            value -> ((value instanceof Path) && Files.exists((Path) value))
    ),

    FILE_MUST_NOT_EXIST(
            "file_exists_on_disk",
            "Parameter must be an object of type `Path` and it must exist on disk.",
            value -> ((value instanceof Path) && Files.notExists((Path) value))
    ),

    MUST_BE_DIRECTORY(
            "must_be_directory",
            "Parameter must be an object of type `Path` and it must be a directory.",
            value -> ((value instanceof Path) && Files.isDirectory((Path) value))
    ),

    MUST_BE_REGULAR_FILE(
            "must_be_regular_file",
            "Parameter must be an object of type `Path` and it must be a regular file.",
            value -> ((value instanceof Path) && Files.isRegularFile((Path) value))
    );


    private final String            key;
    private final String            description;
    private final Predicate<Object> predicate;


    Validation(final String key, final String description, final Predicate<Object> predicate) {
        this.key = key;
        this.description = description;
        this.predicate = predicate;

    }


    /**
     * Helper function to test against a value against a collection of validations (e.g. not null, and must exist).
     *
     * See {@link #validatePredicates(List, Object)} if you just want to throw an exception on rejection.
     *
     * @param validations
     * @param testTarget
     * @return True if all validations pass, else false.
     */
    static boolean testPredicates(final List<Validation> validations, final Object testTarget) {
        return validations.stream().allMatch(validation -> validation.test(testTarget));
    }


    /**
     * Helper function to test against a value against a collection of validations (e.g. not null, and must exist).
     *
     * Throws an exception if any validations fail.
     *
     * See {@link #testPredicates(List, Object)} if you just want to test and get a true/false response, rather than throw exceptions on fail.
     *
     * @param validations
     * @param testTarget
     */
    static void validatePredicates(final List<Validation> validations, final Object testTarget) {
        validations.forEach(validation -> {
            validation.validate(testTarget);
        });
    }


    public String getDescription() {
        return this.description;
    }


    public String getKey() {
        return this.key;
    }


    public Predicate<Object> getPredicate() {
        return this.predicate;
    }


    public boolean test(final Object testSubject) {
        return this.predicate.test(testSubject);
    }


    public void validate(final Object testSubject) {
        this.validate(testSubject, null);
    }


    public void validate(final Object testSubject, final String message) {
        if (!this.test(testSubject)) {
            final String m = this.getDescription() + ((message != null) ? message : "");
            throw new ParameterValidationFailException(m);
        }
    }

}
