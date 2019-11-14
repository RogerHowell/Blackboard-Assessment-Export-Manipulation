package exploratory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assumptions.assumeTrue;


/**
 * Exploratory tests examining whether the direction of slashes is relevant for getting paths across systems.
 */
public class PathTest {

    private static final String SYSTEM_PATH_SEPARATOR = File.separator;


    @Test
    public void info_printSystemSeparator() {
        System.out.println("File.separator = " + File.separator);
    }


    @ParameterizedTest
    @ValueSource(strings = { "./", ".\\" })
    public void info_testCurrentDirPath(final String pathString) {
        System.out.println("Paths.get(" + pathString + ") = " + Paths.get(pathString));
    }


    @ParameterizedTest
    @ValueSource(strings = { "/", "\\" })
    public void info_testRootPath(final String pathString) {
        System.out.println("Paths.get(\"" + pathString + "\") = " + Paths.get(pathString));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "src\\test\\resources",
            "src\\test\\resources\\",
            ".\\src\\test\\resources",
            ".\\src\\test\\resources\\"
    })
    public void test_ResourcesPath_backSlash(final String pathString) {
        final Path path = Paths.get(pathString);
        assumeTrue(
                Files.exists(path),
                "File expected to exist (but doesn't) " +
                "\n - pathString:   " + pathString + "" +
                "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources",
            "src/test/resources/",
            "./src/test/resources",
            "./src/test/resources/"
    })
    public void test_resourcesPath_forwardSlash(final String pathString) {
        final Path path = Paths.get(pathString);
        assumeTrue(
                Files.exists(path),
                "File expected to exist (but doesn't) " +
                "\n - pathString:   " + pathString + "" +
                "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }

}
