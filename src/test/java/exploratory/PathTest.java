package exploratory;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Exploratory tests examining whether the direction of slashes is relevant for getting paths across systems.
 */
public class PathTest {


    @Test
    public void printRootPath() {

        System.out.println("File.separator = " + File.separator);
        System.out.println("Paths.get(\"./\") = " + Paths.get("./"));
        System.out.println("Paths.get(\"/\") = " + Paths.get("/"));
        System.out.println("Paths.get(\"\\\\\") = " + Paths.get("\\"));
        System.out.println("Paths.get(\".\\\\\") = " + Paths.get(".\\"));
        System.out.println("Paths.get(File.separator) = " + Paths.get(File.separator));
        System.out.println("Paths.get(\".\" + File.separator) = " + Paths.get("." + File.separator));

        System.out.println();
        System.out.println("== .toAbsolutePath() ==");
        System.out.println("Paths.get(\"./\") = " + Paths.get("./").toAbsolutePath());
        System.out.println("Paths.get(\"/\") = " + Paths.get("/").toAbsolutePath());
        System.out.println("Paths.get(\"\\\\\") = " + Paths.get("\\").toAbsolutePath());
        System.out.println("Paths.get(\".\\\\\") = " + Paths.get(".\\").toAbsolutePath());
        System.out.println("Paths.get(File.separator) = " + Paths.get(File.separator).toAbsolutePath());
        System.out.println("Paths.get(\".\" + File.separator) = " + Paths.get("." + File.separator).toAbsolutePath());

    }


    @Test
    public void testResourcesPathTest() {

        Path path;

        System.out.println();
        System.out.println("== Forward slashes ==");
        path = Paths.get("src/test/resources");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());

        path = Paths.get("src/test/resources/");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());

        path = Paths.get("./src/test/resources");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());

        path = Paths.get("./src/test/resources/");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());


        System.out.println();
        System.out.println("== Backslashes ==");

        path = Paths.get("src\\test\\resources");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());

        path = Paths.get("src\\test\\resources\\");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());

        path = Paths.get(".\\src\\test\\resources");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());

        path = Paths.get(".\\src\\test\\resources\\");
        System.out.println("path.toFile().exists() :: " + path.toFile().exists());
        assertTrue(path.toFile().exists());


    }

}
