package io.github.rogerhowell.validation;

import io.github.rogerhowell.model.BbExportZipTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.nio.file.Paths;

import static io.github.rogerhowell.util.FileUtil.resourcePathTest;
import static io.github.rogerhowell.validation.Validation.DISALLOW_EMPTY_STRING;
import static io.github.rogerhowell.validation.Validation.DISALLOW_NULL;
import static io.github.rogerhowell.validation.Validation.FILE_MUST_EXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {


    private void assertValidation(final Validation validationRule, final Object testTarget, final boolean expectingPass) {
        boolean validationPassed = true;

        try {
            validationRule.validate(testTarget);
        } catch (final ParameterValidationFailException e) {
            validationPassed = false;
        }

        assertEquals(expectingPass, validationPassed,
                     "Expecting " + expectingPass + ", but was " + validationPassed + "." +
                     "\n - Value: " + String.valueOf(testTarget) +
                     "\n - Key:   " + validationRule.getKey()
        );
    }


    private void assertValidationPass(final Validation validationRule, final Object testTarget) {
        this.assertValidation(validationRule, testTarget, true);
    }


    private void assertValidationRejection(final Validation validationRule, final Object testTarget) {
        this.assertValidation(validationRule, testTarget, false);
    }


    @Test
    public void test_disallowEmptyString() {
        this.assertValidationRejection(DISALLOW_EMPTY_STRING, null);
        this.assertValidationRejection(DISALLOW_EMPTY_STRING, "");
        this.assertValidationPass(DISALLOW_EMPTY_STRING, "abc");
        this.assertValidationRejection(DISALLOW_EMPTY_STRING, 10);
        this.assertValidationRejection(DISALLOW_EMPTY_STRING, Paths.get("/"));
    }


    @Test
    public void test_disallowNull() {
        this.assertValidationRejection(DISALLOW_NULL, null);
        this.assertValidationPass(DISALLOW_NULL, "");
        this.assertValidationPass(DISALLOW_NULL, "abc");
        this.assertValidationPass(DISALLOW_NULL, 10);
        this.assertValidationPass(DISALLOW_NULL, Paths.get("/"));
    }


    @Test
    public void test_fileExistsOnDisk() {
        this.assertValidationRejection(FILE_MUST_EXIST, null);
        this.assertValidationRejection(FILE_MUST_EXIST, "");
        this.assertValidationRejection(FILE_MUST_EXIST, "abc");
        this.assertValidationRejection(FILE_MUST_EXIST, 10);

        this.assertValidationPass(FILE_MUST_EXIST, Paths.get("/"));
        this.assertValidationPass(FILE_MUST_EXIST, resourcePathTest(BbExportZipTest.FILE_PATH_EMPTY_ZIP));
        this.assertValidationRejection(FILE_MUST_EXIST, resourcePathTest(BbExportZipTest.FILE_PATH_INVALID_NON_EXISTENT_FILENAME));
        this.assertValidationRejection(FILE_MUST_EXIST, resourcePathTest(BbExportZipTest.FILE_PATH_NON_EXISTENT_ZIP));
    }


    @ParameterizedTest
    @NullAndEmptySource
    public void test_techniques_assertThrows(final String testValue) {
        assertThrows(ParameterValidationFailException.class, () -> DISALLOW_EMPTY_STRING.validate(testValue));
    }


    @Test
    public void test_techniques_enumMethodTest() {
        assertFalse(DISALLOW_EMPTY_STRING.test(null));
        assertFalse(DISALLOW_EMPTY_STRING.test(""));
        assertTrue(DISALLOW_EMPTY_STRING.test("abc"));
    }


    @Test
    public void test_techniques_staticMethod() {
        // Static method
        this.assertValidationRejection(DISALLOW_EMPTY_STRING, null);
        this.assertValidationRejection(DISALLOW_EMPTY_STRING, "");
        this.assertValidationPass(DISALLOW_EMPTY_STRING, "abc");
    }
}
