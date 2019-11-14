package exploratory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Exploratory tests examining whether the direction of slashes is relevant for getting paths across systems.
 */
public class PathTest {

    private static final String SYSTEM_PATH_SEPARATOR = File.separator;


    @Test
    public void printSystemSeparator() {
        System.out.println("File.separator = " + File.separator);
    }


    @ParameterizedTest
    @ValueSource(strings = { "./", ".\\" })
    public void testCurrentDirPath(final String pathString) {
        System.out.println();
        System.out.println("Paths.get(" + pathString + ") = " + Paths.get(pathString));
    }


//    @ParameterizedTest
//    @ValueSource(strings = {
//            "src\\test\\resources",
//            "src\\test\\resources\\",
//            ".\\src\\test\\resources",
//            ".\\src\\test\\resources\\"
//    })
//    public void testResourcesPathTest_backSlash(final String pathString) {
//        final Path path = Paths.get(pathString);
////        System.out.println("pathString = " + pathString);
////        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
//        assertTrue(
//                path.toFile().exists(),
//                "File expected to exist (but doesn't) " +
//                "\n - pathString:   " + pathString + "" +
//                "\n - absolutePath: " + path.toAbsolutePath().toString()
//        );
//    }


    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources",
            "src/test/resources/",
            "./src/test/resources",
            "./src/test/resources/"
    })
    public void testResourcesPathTest_forwardSlash(final String pathString) {
        final Path path = Paths.get(pathString);
//        System.out.println("pathString = " + pathString);
//        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(
                path.toFile().exists(),
                "File expected to exist (but doesn't) " +
                "\n - pathString:   " + pathString + "" +
                "\n - absolutePath: " + path.toAbsolutePath().toString()
        );
    }


    @ParameterizedTest
    @ValueSource(strings = { "/", "\\" })
    public void testRootPath(final String pathString) {
        System.out.println();
        System.out.println("Paths.get(\"" + pathString + "\") = " + Paths.get(pathString));
    }

}
