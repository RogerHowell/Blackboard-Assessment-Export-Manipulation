package io.github.rogerhowell.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static io.github.rogerhowell.util.TestUtil.testResourcePath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        final String pathString = "empty_zip/gradebook_2019_CS9999_Empty20Task_2019-11-08-21-41-57.zip";
        final Path   path       = testResourcePath(pathString);

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

        // Output list of files in dir:

        System.out.println("parentDir:    " + parentDir);
        for (final File file : files) {
            System.out.println(" \\-- file = " + file);
        }

        assertTrue(exists,
                   "File should exist." +
                   "\n - pathString:   " + pathString +
                   "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }


    @Test
    public void constructorTest_verifyFileExists_nonExistentFile() {
        final String pathString = "/non-existent.zip";
        final Path   path       = testResourcePath(pathString);

        boolean exists = true;

        try {
            final boolean     verifyFileExists = true;
            final BbExportZip bbExport         = new BbExportZip(path, verifyFileExists);
        } catch (final IllegalArgumentException e) {
            exists = false;
        }

        assertFalse(exists,
                    "File should **NOT** exist!!" +
                    "\n - pathString:   " + pathString +
                    "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }
}
