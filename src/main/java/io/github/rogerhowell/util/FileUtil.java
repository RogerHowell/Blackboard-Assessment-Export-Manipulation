package io.github.rogerhowell.util;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static final Path RESOURCE_ROOT      = Paths.get("src/main/resources");
    public static final Path TEST_RESOURCE_ROOT = Paths.get("src/test/resources");


    /**
     * Returns a JSONObject using the contents of the file specified.
     *
     * @param pathToFile A string showing the path **relative to the resource root, but starting with a `/` ...**.
     * @return a JSONObject using the contents of the file specified.
     */
    public static JSONObject jsonObjectFromResourcePath(final String pathToFile) {

//        final Path path = FileUtil.resourcePath(pathToFile);
//        try (final InputStream inputStream = new FileInputStream(path.toFile())) {

        try (final InputStream inputStream = FileUtil.class.getResourceAsStream(pathToFile)) {
            return new JSONObject(new JSONTokener(inputStream));
        } catch (final IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Given path to json file could not be accessed (missing, invalid permissions, or perhaps not relative to the resource root?)");
        }
    }


    /**
     * Utility function for fetching files relative to the main resource root.
     *
     * This enables the path to be given as relative to the main resource root, thus enabling IDE support
     * for the given path (as a string), while allowing the path to be resolved against the project root.
     *
     * @param path
     * @return
     */
    public static Path resourcePath(final String path) {
        return FileUtil.RESOURCE_ROOT.resolve(path);
    }


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
        return FileUtil.TEST_RESOURCE_ROOT.resolve(path);
    }
}
