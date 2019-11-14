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
    );


    private final String            key;
    private final String            description;
    private final Predicate<Object> predicate;


    Validation(final String key, final String description, final Predicate<Object> predicate) {
        this.key = key;
        this.description = description;
        this.predicate = predicate;

    }


    static void testPredicate(final Validation validation, final Object testTarget) {
        final boolean result = validation.getPredicate().test(testTarget);
        if (!result) {
            throw new ParameterValidationFailException("");
        }
    }


    static void testPredicates(final List<Validation> validations, final Object testTarget) {
        validations.forEach(validation -> {
            Validation.testPredicate(validation, testTarget);
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
        if (!this.test(testSubject)) {
            throw new ParameterValidationFailException(this.getDescription());
        }
    }

}
