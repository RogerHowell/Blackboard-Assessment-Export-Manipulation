package exploratory;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

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

}
