package io.github.rogerhowell.model;

import io.github.rogerhowell.exceptions.ParameterValidationFailException;
import io.github.rogerhowell.testing.JsonableTest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.nio.file.Path;

import static io.github.rogerhowell.util.FileUtil.resourcePathTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BbExportZipTest implements JsonableTest {

    private static final String FILE_PATH_EMPTY_ZIP        = "empty_zip/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip";
    private static final String FILE_PATH_NON_EXISTENT_ZIP = "NON-EXISTING-DIR/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip";
    private static final String FILE_PATH_INVALID_FILENAME = "NON-EXISTING-DIR/invalid_filename.zip";


    @Test
    public void test_constructor_basicInvalidPath() {
        final Path path = resourcePathTest(BbExportZipTest.FILE_PATH_INVALID_FILENAME);

        boolean isValid = true;
        try {
            new BbExportZip(path, false);
        } catch (final ParameterValidationFailException e) {
            isValid = false;
        }

        assertFalse(isValid);
    }


    @Test
    public void test_constructor_basicValidPath() {
        final Path        path     = resourcePathTest(BbExportZipTest.FILE_PATH_EMPTY_ZIP);
        final BbExportZip bbExport = new BbExportZip(path, false);

        assertEquals(path, bbExport.getPath());
    }


    @Test
    public void test_constructor_nullPath() {
        final Path path = null;

        boolean isValid = true;
        try {
            new BbExportZip(path, false);
        } catch (final ParameterValidationFailException e) {
            isValid = false;
        }

        assertFalse(isValid);
    }


    @Test
    public void test_constructor_verifyFileExists_existingFile() {
        final String pathString = BbExportZipTest.FILE_PATH_EMPTY_ZIP;
        final Path   path       = resourcePathTest(pathString);

        boolean fileFound = true;

        try {
            final boolean verifyFileExists = true;
            new BbExportZip(path, verifyFileExists);
        } catch (final ParameterValidationFailException e) {
            fileFound = false;
        }

        assertTrue(fileFound,
                   "File must exist." +
                   "\n - pathString:   " + pathString +
                   "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }


    @Test
    public void test_constructor_verifyFileExists_nonExistentFile() {
        final String pathString = BbExportZipTest.FILE_PATH_NON_EXISTENT_ZIP;
        final Path   path       = resourcePathTest(pathString);

        boolean fileFound = true;

        try {
            final boolean verifyFileExists = true;
            new BbExportZip(path, verifyFileExists);
        } catch (final ParameterValidationFailException e) {
            fileFound = false;
        }

        assertFalse(fileFound,
                    "File must **NOT** exist!!" +
                    "\n - pathString:   " + pathString +
                    "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }


    @Override
    @Test
    public void test_getSchema_nonNullReturn() {
        final Path        path     = resourcePathTest(BbExportZipTest.FILE_PATH_EMPTY_ZIP);
        final BbExportZip bbExport = new BbExportZip(path, true);

        final Schema schema = bbExport.getSchema();

        assertNotNull(schema);
    }


    @Override
    @Test
    public void test_getSchema_schemaExecutesWithoutError() {
        final Path        path     = resourcePathTest(BbExportZipTest.FILE_PATH_EMPTY_ZIP);
        final BbExportZip bbExport = new BbExportZip(path, true);

        final Schema     schema     = bbExport.getSchema();
        final JSONObject jsonObject = bbExport.toJson();

        try {
            schema.validate(jsonObject);
        } catch (final ValidationException e) {
            fail("ERROR: Schema validation failed with message: " +
                 "\n - Message: " + e.getMessage() +
                 "\n - Child Messages: " +
                 "\n     - " + String.join("\n     - ", e.getAllMessages()) +
                 ""
            );
        }
    }


    @Override
    public void test_toJson_nonNullReturn() {
        final Path        path       = resourcePathTest(BbExportZipTest.FILE_PATH_EMPTY_ZIP);
        final BbExportZip bbExport   = new BbExportZip(path, true);
        final JSONObject  jsonObject = bbExport.toJson();

        assertNotNull(jsonObject);
    }


    @Test
    public void test_toJson_genericFileVerifyFalse() {
        final Path        path     = resourcePathTest(BbExportZipTest.FILE_PATH_EMPTY_ZIP);
        final BbExportZip bbExport = new BbExportZip(path, false);

        //language=JSON
        final String expected = "{\n" +
                                "  \"task_name\": \"Empty20Task\",\n" +
                                "  \"path\": \"src/test/resources/empty_zip/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip\",\n" +
                                "  \"cohort_year\": \"2019\",\n" +
                                "  \"file_exists\": true,\n" +
                                "  \"is_file_existence_checked\": false,\n" +
                                "  \"module_name\": \"CS9999\",\n" +
                                "  \"export_timestamp\": \"2019-11-08T21:41:57.000Z\",\n" +
                                "  \"export_timestamp_epoch_millis\": 1573249317000\n" +
                                "}";

        final JSONObject actual = bbExport.toJson();
        System.out.println("actual.toString(2) = " + actual.toString(2));
        JSONAssert.assertEquals(expected, actual, true);
    }


    @Test
    public void test_toJson_genericFileVerifyTrue() {
        final Path        path     = resourcePathTest(BbExportZipTest.FILE_PATH_EMPTY_ZIP);
        final BbExportZip bbExport = new BbExportZip(path, true);

        //language=JSON
        final String expected = "{\n" +
                                "  \"task_name\": \"Empty20Task\",\n" +
                                "  \"path\": \"src/test/resources/empty_zip/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip\",\n" +
                                "  \"cohort_year\": \"2019\",\n" +
                                "  \"file_exists\": true,\n" +
                                "  \"is_file_existence_checked\": true,\n" +
                                "  \"module_name\": \"CS9999\",\n" +
                                "  \"export_timestamp\": \"2019-11-08T21:41:57.000Z\",\n" +
                                "  \"export_timestamp_epoch_millis\": 1573249317000\n" +
                                "}";

        final JSONObject actual = bbExport.toJson();
        System.out.println("actual.toString(2) = " + actual.toString(2));
        JSONAssert.assertEquals(expected, actual, true);
    }

}
