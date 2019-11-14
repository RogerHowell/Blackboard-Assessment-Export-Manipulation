package io.github.rogerhowell.util;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
     * @param path               Given path
     * @param directorySeparator The separator to use
     * @return A normalised path to the given directory separator.
     */
    public static String pathToString(final Path path, final String directorySeparator) {
        final StringBuilder sb = new StringBuilder();

        if (path.toString().equalsIgnoreCase("")) {
            return "";
        }

        // Handle leading slash character
        if (path.startsWith(Paths.get("/"))) {
            sb.append(directorySeparator);
        }

        // Handle each of the segments
        for (int i = 0; i < path.getNameCount(); i++) {
            final Path pathSegment = path.getName(i);
            sb.append(pathSegment);

            // Only append a directory separator if we're not the final pathSegment
            if (i < path.getNameCount() - 1) {
                sb.append(directorySeparator);
            }
        }

        // If the path is a directory, add a trailing slash
        final boolean isDirectory = Files.isDirectory(path);
        if (isDirectory && path.getNameCount() > 0) {
            sb.append(directorySeparator);
        }

        return sb.toString();
    }


    /**
     * Defaults to using the `/` character, which safely works on Windows/Unix/MacOS
     *
     * @param path Given path
     * @return A normalised path to the given directory separator.
     */
    public static String pathToString(final Path path) {
        final String defaultdirectorySeparator = "/";
        return FileUtil.pathToString(path, defaultdirectorySeparator);
    }


    /**
     * @param path Given path
     * @return A normalised path to the given directory separator.
     */
    public static String pathToString2(final Path path) {
        final String defaultdirectorySeparator = "/";
        return path.toString().replaceAll(File.separator, defaultdirectorySeparator);
    }


    /**
     * @param path               Given path
     * @param directorySeparator The separator to use
     * @return A normalised path to the given directory separator.
     */
    public static String pathToString2(final Path path, final String directorySeparator) {
        return path.toString().replaceAll(File.separator.replaceAll("\\\\", "\\\\\\\\"), directorySeparator);
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
