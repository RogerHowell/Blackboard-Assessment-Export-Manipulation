package io.github.rogerhowell.util;

import io.github.rogerhowell.exceptions.ParameterValidationFailException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    public static final Path RESOURCE_ROOT_MAIN = Paths.get("src/main/resources");
    public static final Path RESOURCE_ROOT_TEST = Paths.get("src/test/resources");

    public static final String DIRECTORY_SEPARATOR_DEFAULT = "/";
    public static final String DIRECTORY_SEPARATOR_SYSTEM  = File.separator;

    public static final Charset CHARSET_DEFAULT = StandardCharsets.UTF_8;


    /**
     * @param dir Directory to be created (including parent directories, if required)
     */
    public static void createDirectoryIfNotExists(final File dir) {
        final boolean isMkDirsSuccessful = dir.mkdirs();
        if (!isMkDirsSuccessful && !dir.exists()) {
            throw new IllegalStateException("Making destination directory not successful, and destination directory does not already exist (or is not accessible).");
        }
    }


    /**
     * Utility function to determine if the given path refers to a file that exists on disk.
     *
     * @param path Path to file to examine.
     * @return True if the file exists on disk.
     */
    public static Boolean fileExistsOnDisk(final Path path) {
        return (path != null) && Files.exists(path);
    }


    /**
     * Utility function to determine if the filename of the given path matches the given pattern.
     *
     * @param path    Path to file to examine.
     * @param pattern Pattern to test against the given path.
     * @return True if the filename of the given path matches the pattern.
     */
    public static boolean filenameMatchesPattern(final Path path, final String pattern) {
        return FileUtil.getFilename(path).matches(pattern);
    }


    /**
     * Utility function to return the contents of the given file path as a String.
     * See {@link #getFileContentsAsString(Path, Charset)} if wanting to specify a charset other than the default (currently UTF-8)
     *
     * @param filePath Path to the file whose text contents are wanted.
     * @return The string contents of the file.
     */
    public static String getFileContentsAsString(final Path filePath) throws IOException {
        return FileUtil.getFileContentsAsString(filePath, FileUtil.CHARSET_DEFAULT);
    }


    /**
     * Utility function to return the contents of the given file path as a String.
     * See {@link #getFileContentsAsString(Path)} if wanting to the default charset (currently UTF-8)
     *
     * Note -- Files.readString is JDK 11
     *
     * @param filePath Path to the file whose text contents are wanted.
     * @param charset  Charset of the file.
     * @return The string contents of the file.
     */
    public static String getFileContentsAsString(final Path filePath, final Charset charset) throws IOException {
        // Note -- Files.readString is JDK 11
//        return Files.readString(filePath, charset);

        // Note -- Files.lines is JDK 8 onwards
        return Files.lines(filePath).collect(Collectors.joining("\n"));
    }


    /**
     * Utility function to return the file extension of the given file path.
     *
     * @param path The path to the file.
     * @return The file extension - defined as "the content after the last dot (.) character", else "an empty string"
     */
    public static String getFileExtension(final Path path, final boolean includeDot) {
        final Path   filename       = path.getFileName();
        final String filenameString = filename.toString();
        final int    i              = filenameString.lastIndexOf('.');

        if (i < 0) {
            return "";
        }

        final int startChar = includeDot ? i : i + 1;
        return filenameString.substring(startChar);
    }


    /**
     * Utility function to return the filename of the given file path.
     *
     * @param path Path to file to examine.
     * @return The filename of the given file path.
     */
    public static String getFilename(final Path path) {
        return path.getFileName().toString();
    }


    /**
     * Utility function to get a path relative to the given base directory.
     *
     * Will throw an IllegalArgumentException if the new path sits outside of the given base directory.
     *
     * @param baseDirectory
     * @param path
     * @return
     */
    public static Path getRelativePathNormalised(final Path baseDirectory, final Path path) {
        return FileUtil.getRelativePathNormalised(baseDirectory, path.toString());
    }


    /**
     * Utility function to get a path relative to the given base directory.
     *
     * Will throw an IllegalArgumentException if the new path sits outside of the given base directory.
     *
     * @param baseDirectory
     * @param pathString
     * @return
     */
    public static Path getRelativePathNormalised(final Path baseDirectory, final String pathString) {
        if (!Files.isDirectory(baseDirectory)) {
            throw new ParameterValidationFailException("Given path to the base directory MUST be a directory.");
        }

        try {
            final Path    newPathNormalised = Paths.get(baseDirectory.toString(), pathString).normalize();
            final boolean isFileOutsideDir  = !newPathNormalised.equals(baseDirectory) && !FileUtil.isFileWithinBaseDirectory(baseDirectory, newPathNormalised);
            if (isFileOutsideDir) {
                throw new ParameterValidationFailException(
                        "Given path MUST resolve to being within the given base directory." +
                        "\n - Base Dir: " + baseDirectory.toString() +
                        "\n - Given path: " + pathString +
                        "\n - New path: " + newPathNormalised.toString()
                );
            }

            return newPathNormalised;
        } catch (final IOException e) {
            throw new IllegalArgumentException("ERROR: Cannot test due to IOException", e);
        }
    }


    /**
     * Utility function to perform a case-insensitive test to see if the given extension matches the extension of the given file.
     *
     * @param path      Path to file to examine.
     * @param extension Extension to be tested against the given file path.
     * @return True if the given extension matches, else false.
     */
    public static boolean hasFileExtension(final Path path, final String extension) {
        final String actualExtension = FileUtil.getFileExtension(path, false);
        return actualExtension.equalsIgnoreCase(extension);
    }


    /**
     * Utility function to determine whether the given file exists within the given directory.
     *
     * Returns true if the given file is contained within the given baseDirectory
     * Currently does a string comparison of canonical paths
     * Alternative implementations could include relativising path objects and seeing if the relative path starts with `../`
     */
    public static boolean isFileWithinBaseDirectory(final File baseDirectory, final File file) throws IOException {
        final String destDirPath  = baseDirectory.getCanonicalPath();
        final String destFilePath = file.getCanonicalPath();

        return (destFilePath.startsWith(destDirPath + FileUtil.DIRECTORY_SEPARATOR_SYSTEM));
    }


    /**
     * Utility function to determine whether the given file exists within the given directory.
     *
     * Returns true if the given file is contained within the given baseDirectory
     * Currently does a string comparison of canonical paths
     * Alternative implementations could include relativising path objects and seeing if the relative path starts with `../`
     */
    public static boolean isFileWithinBaseDirectory(final Path baseDirectory, final Path file) throws IOException {
        return FileUtil.isFileWithinBaseDirectory(baseDirectory.toFile(), file.toFile());
    }


    /**
     * Returns a JSONObject using the contents of the file specified.
     *
     * @param pathToFile A string showing the path **relative to the resource root, but starting with a `/` ...**.
     * @return a JSONObject using the contents of the file specified.
     */
    public static JSONObject jsonObjectFromResourcePath(final String pathToFile) {
        try {
            final Path   path     = FileUtil.resourcePathMain(pathToFile);
            final String contents = FileUtil.getFileContentsAsString(path);
            return new JSONObject(contents);
        } catch (final IOException e) {
            throw new ParameterValidationFailException(
                    "Given path to json file could not be accessed " +
                    "(perhaps missing, invalid permissions, or not relative to the resource root?)",
                    e
            );
        }
    }


    /**
     * Utility function to list the immediate children of the given directory path.
     *
     * @param dirPath The given directory path
     * @return A list of `Path` objects representing the immediate children of the given directory.
     */
    public static List<Path> listDirContents(final Path dirPath) {
        if (dirPath == null) {
            throw new ParameterValidationFailException("Directory contents not found - The given directory is null.");
        }
        if (!FileUtil.fileExistsOnDisk(dirPath)) {
            throw new ParameterValidationFailException("Directory contents not found - The given directory does not exist on disk.");
        }

        // "Returns null if this abstract pathname does not denote a directory, or if an I/O error occurs."
        final File[] dirFileContents = dirPath.toFile().listFiles();
        if (dirFileContents == null) {
            // Test done above for null/non-existent directory, thus presume an IO error.
            throw new ParameterValidationFailException("Directory contents not found - Already checked for a null/non-existent dir, thus this is likely due to an I/O error of unknown type.");
        }

        return Stream.of(dirFileContents)
                     .map(File::toPath)
                     .collect(Collectors.toList());
    }


    /**
     * Utility function to return a list of the directories **directly** within the given directory.
     *
     * @param dirPath Path to the parent directory.
     * @return Collection of the directories contained within the given directory.
     */
    public static List<Path> listDirsInDir(final Path dirPath) {
        return FileUtil.listDirContents(dirPath).stream()
                       .filter(Files::isDirectory)
                       .collect(Collectors.toList());
    }


    /**
     * Utility function to return a list of the regular files **directly** within the given directory.
     *
     * @param dirPath Path to the parent directory.
     * @return Collection of the regular files contained within the given directory.
     */
    public static List<Path> listFilesInDir(final Path dirPath) {
        return FileUtil.listDirContents(dirPath).stream()
                       .filter(Files::isRegularFile)
                       .collect(Collectors.toList());
    }


    /**
     * Utility function to convert the given path into a normalised String.
     * Defaults to using the `/` character, which safely works on Windows/Unix/MacOS
     *
     * See {@link #pathToNormalisedString(Path, String)} if wanting to manually specify the directory separator to use.
     *
     * @param path Given path.
     * @return A normalised path string to the given directory separator.
     */
    public static String pathToNormalisedString(final Path path) {
        return FileUtil.pathToNormalisedString(path, FileUtil.DIRECTORY_SEPARATOR_DEFAULT);
    }


    /**
     * Utility function to convert the given path into a normalised String.
     * See {@link #pathToNormalisedString(Path)} if wanting to use the default system directory separator.
     *
     * @param path               Given path
     * @param directorySeparator The separator to use
     * @return A normalised path string to the given directory separator.
     */
    public static String pathToNormalisedString(final Path path, final String directorySeparator) {
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
     * Utility function for fetching files relative to the main resource root.
     *
     * This enables the path to be given as relative to the main resource root, thus enabling IDE support
     * for the given path (as a string), while allowing the path to be resolved against the project root.
     *
     * @param path
     * @return
     */
    public static Path resourcePathMain(final String path) {
        return FileUtil.getRelativePathNormalised(FileUtil.RESOURCE_ROOT_MAIN, path);
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
    public static Path resourcePathTest(final String path) {
        return FileUtil.getRelativePathNormalised(FileUtil.RESOURCE_ROOT_TEST, path);

    }
}
