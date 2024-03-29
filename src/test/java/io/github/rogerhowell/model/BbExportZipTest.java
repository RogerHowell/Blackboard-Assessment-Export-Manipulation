package io.github.rogerhowell.model;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.nio.file.Path;

import static io.github.rogerhowell.util.FileUtil.resourcePathTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BbExportZipTest {


    @Test
    public void test_constructor_basicPath() {
        final Path        path     = resourcePathTest("/");
        final BbExportZip bbExport = new BbExportZip(path, false);

        assertEquals(path, bbExport.getPath());
    }


    @Test
    public void test_constructor_null() {
        final BbExportZip bbExport = new BbExportZip(null, false);
        assertNull(bbExport.getPath());
    }


    @Test
    public void test_constructor_verifyFileExists_existingFile() {
        final String pathString = "empty_zip/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip";
        final Path   path       = resourcePathTest(pathString);

        boolean exists = true;

        try {
            final boolean     verifyFileExists = true;
            final BbExportZip bbExport         = new BbExportZip(path, verifyFileExists);
        } catch (final IllegalArgumentException e) {
            exists = false;
        }

        // Check parent dir
        final File parentDir = path.getParent().toFile();
        if (!parentDir.exists()) {
            fail("Parent dir should exist." +
                 "\n - pathString:   " + pathString +
                 "\n - absolutePath: " + path.toAbsolutePath().toString()
            );
        }

        // Check / list the contents of the parent dir
        final File[] files = parentDir.listFiles();
        if (files == null) {
            fail("Parent dir should contain more than zero files." +
                 "\n - parentDir:    " + parentDir +
                 "\n - absoluteFile: " + parentDir.getAbsoluteFile().toString()
            );
        }

        // Output list of files in parent dir, **IF** the searched-for file is not found:
        if (!exists) {
            System.out.println("parentDir:    " + parentDir);
            for (final File file : files) {
                System.out.println(" \\-- file = " + file);
            }
        }

        assertTrue(exists,
                   "File must exist." +
                   "\n - pathString:   " + pathString +
                   "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }


    @Test
    public void test_constructor_verifyFileExists_nonExistentFile() {
        final String pathString = "/non-existent.zip";
        final Path   path       = resourcePathTest(pathString);

        boolean exists = true;

        try {
            final boolean     verifyFileExists = true;
            final BbExportZip bbExport         = new BbExportZip(path, verifyFileExists);
        } catch (final IllegalArgumentException e) {
            exists = false;
        }

        assertFalse(exists,
                    "File must **NOT** exist!!" +
                    "\n - pathString:   " + pathString +
                    "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }


    @Test
    public void test_schemaValidation_getSchema() {
        final Path        path     = resourcePathTest("/");
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


    @Test
    public void test_toJson_genericFileVerifyFalse() {
        final Path        path     = resourcePathTest("/");
        final BbExportZip bbExport = new BbExportZip(path, false);

        final String expected = "{\n" +
                                "  \"path\": \"src/test/resources/\",\n" +
                                "  \"file_exists\": true,\n" +
                                "  \"is_file_existence_checked\": false\n" +
                                "}";

        final JSONObject actual = bbExport.toJson();
        JSONAssert.assertEquals(expected, actual, true);
    }


    @Test
    public void test_toJson_genericFileVerifyTrue() {
        final Path        path     = resourcePathTest("/");
        final BbExportZip bbExport = new BbExportZip(path, true);

        final String expected = "{\n" +
                                "  \"path\": \"src/test/resources/\",\n" +
                                "  \"file_exists\": true,\n" +
                                "  \"is_file_existence_checked\": true\n" +
                                "}";
        final JSONObject actual = bbExport.toJson();
        JSONAssert.assertEquals(expected, actual, true);
    }

}
