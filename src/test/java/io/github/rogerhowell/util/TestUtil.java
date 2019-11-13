package io.github.rogerhowell.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtil {

    public static final Path TEST_RESOURCE_ROOT = Paths.get("src/test/resources");


    /**
     * Utility function for fetching files relative to the test resource root.
     *
     * This enables the path to be given as relative to the test resource root, thus enabling IDE support
     * for the given path (as a string), while allowing the path to be resolved against the project root.
     *
     * @param path
     * @return
     */
    public static Path testResourcePath(final String path) {
        return TestUtil.TEST_RESOURCE_ROOT.resolve(path);
    }

}
