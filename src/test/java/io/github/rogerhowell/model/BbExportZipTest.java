package io.github.rogerhowell.model;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static io.github.rogerhowell.util.TestUtil.testResourcePath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BbExportZipTest {


    @Test
    public void constructorTest_basicPath() {
        final Path        path     = testResourcePath("/");
        final BbExportZip bbExport = new BbExportZip(path, false);

        assertEquals(path, bbExport.getPath());
    }


    @Test
    public void constructorTest_null() {
        final BbExportZip bbExport = new BbExportZip(null, false);
        assertNull(bbExport.getPath());
    }


    @Test
    public void constructorTest_verifyFileExists_existingFile() {
        final Path path = testResourcePath("empty_zip/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip");

        boolean exists = true;

        try {
            final boolean     verifyFileExists = true;
            final BbExportZip bbExport         = new BbExportZip(path, verifyFileExists);
        } catch (final IllegalArgumentException e) {
            exists = false;
        }

        assertTrue(exists, "File should exist.");
    }


    @Test
    public void constructorTest_verifyFileExists_nonExistentFile() {
        final Path path = testResourcePath("/non-existent.zip");

        boolean exists = true;

        try {
            final boolean     verifyFileExists = true;
            final BbExportZip bbExport         = new BbExportZip(path, verifyFileExists);
        } catch (final IllegalArgumentException e) {
            exists = false;
        }

        assertFalse(exists, "File should not exist!!");
    }
}
