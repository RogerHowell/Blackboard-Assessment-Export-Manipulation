package io.github.rogerhowell.model;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BbExportZipTest {


    @Test
    public void constructorTest_basicPath() {
        final Path        path     = Paths.get("/");
        final BbExportZip bbExport = new BbExportZip(path);

        assertEquals(path, bbExport.getPath());
    }


    @Test
    public void constructorTest_null() {
        final BbExportZip bbExport = new BbExportZip(null);
        assertNull(bbExport.getPath());
    }


    @Test
    public void constructorTest_verifyFileExists_existingFile() {
        final Path path = Paths.get("empty_zip/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip");

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
        final Path path = Paths.get("/non-existent.zip");

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
