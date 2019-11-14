package io.github.rogerhowell.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


/**
 * TODO: Add tests for "unusual" paths -- double slashes, mixed slashes, unicode chars, escaped chars, directory traversal (`../`), etc
 */
public class FileUtilTest {


    private void doTestPathString(final String pathString) {
        final Path path = Paths.get(pathString);
        final File file = new File(pathString);

        File canonicalFile = null;
        try {
            canonicalFile = file.getCanonicalFile();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final String customToString = FileUtil.pathToNormalisedString(path, "/");

        assumeTrue(canonicalFile != null, "Halting -- error getting canonical file for " + pathString);

        //
        System.out.println();
        System.out.println("pathString = " + pathString);
        System.out.println("path = " + path);
        System.out.println("file = " + file);

        System.out.println("path.toAbsolutePath().toString() = " + path.toAbsolutePath().toString());
        System.out.println("file.getAbsoluteFile().toString() = " + file.getAbsoluteFile().toString());
        System.out.println("canonicalFile.toString() = " + canonicalFile.toString());

        System.out.println("customToString = " + customToString);

        assumeTrue(path.toFile().equals(file));

        final boolean fileExists = Files.exists(path);
        System.out.println("fileExists = " + fileExists);

        assumeTrue(fileExists, "If the file doesn't exist, things like detection if is a directory (thus should have a trailing slash) will fail.");


        //
        System.out.println();
        final boolean isDirectory = Files.isDirectory(path);
        System.out.println("isDirectory = " + isDirectory);
        System.out.println("file.isDirectory() = " + file.isDirectory());

        final boolean pathAndFileFlagAsDirectory = isDirectory == file.isDirectory();
        System.out.println("pathAndFileFlagAsDirectory = " + pathAndFileFlagAsDirectory);
        assumeTrue(pathAndFileFlagAsDirectory);


        //
        System.out.println();
        System.out.println("path.startsWith(Paths.get(\"/\")) = " + path.startsWith(Paths.get("/")));
        System.out.println("path.endsWith(Paths.get(\"/\")) = " + path.endsWith(Paths.get("/")));
        System.out.println("path.isAbsolute(): " + path.isAbsolute());


        //
        System.out.println();
        System.out.println("path.endsWith(File.separator) = " + path.endsWith(File.separator));
        System.out.println("path.endsWith(\"\\\\\") = " + path.endsWith("\\"));
        System.out.println("path.endsWith(\"/\") = " + path.endsWith("/"));
        System.out.println("pathString.endsWith(File.separator) = " + pathString.endsWith(File.separator));
        System.out.println("pathString.endsWith(\"\\\\\") = " + pathString.endsWith("\\"));
        System.out.println("pathString.endsWith(\"/\") = " + pathString.endsWith("/"));

        final boolean pathEndsWithFileSeparator = path.endsWith(File.separator);
        System.out.println("pathEndsWithFileSeparator = " + pathEndsWithFileSeparator);

        final boolean pathStringEndsWithFileSeparator = path.endsWith(File.separator);
        System.out.println("pathStringEndsWithFileSeparator = " + pathStringEndsWithFileSeparator);

        final boolean pathAndPathStringEndWithFileSeparator = pathEndsWithFileSeparator == pathStringEndsWithFileSeparator;
        System.out.println("pathAndPathStringEndWithFileSeparator = " + pathAndPathStringEndWithFileSeparator);
        assumeTrue(pathAndPathStringEndWithFileSeparator);


        //
        System.out.println();
        System.out.println("path.getNameCount() = " + path.getNameCount());
        for (int i = 0; i < path.getNameCount(); i++) {
            final Path pathSegment = path.getName(i);
            System.out.println("pathSegment = " + pathSegment);
        }


        System.out.println();
        System.out.println("pathString = " + pathString);
        System.out.println("customToString = " + customToString);

        System.out.println("pathString.equals(customToString) = " + pathString.equals(customToString));

        assertEquals(pathString, customToString);
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "./",
            "./src/test/resources/"
    })
    public void info_pathParts_withLeadingDot(final String pathString) {
        this.doTestPathString(pathString);
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "/",
            "/src/test/resources/",
    })
    public void info_pathParts_withLeadingSlash(final String pathString) {
        this.doTestPathString(pathString);
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "./",
            "src/test/resources/",
            "./src/test/resources/"
    })
    public void info_pathParts_withTrailingSlash(final String pathString) {
        this.doTestPathString(pathString);
    }


    @Test
    public void test_pathToUtil_emptyString() {
        final Path   path   = Paths.get("");
        final String output = FileUtil.pathToNormalisedString(path);

        assertEquals("", output);
    }


    @Test
    public void test_pathToUtil_noLeadingSlash() {
        final Path   path   = Paths.get("src");
        final String output = FileUtil.pathToNormalisedString(path);

        assertEquals("src/", output);
    }


    /**
     * NOTE: As the `src` dir doesn't exist on this system, it treats the given path as a file
     */
    @Test
    public void test_pathToUtil_withLeadingSlash() {
        final Path   path   = Paths.get("/src");
        final String output = FileUtil.pathToNormalisedString(path);


//        assertEquals("/src/", output);
        assertEquals("/src", output);
    }
}
