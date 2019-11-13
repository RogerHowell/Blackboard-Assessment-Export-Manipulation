package io.github.rogerhowell.model;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BbExportZipTest {


    @Test
    public void constructorTest_basicPath() {
        final Path        path     = Paths.get("/");
        final BbExportZip bbExport = new BbExportZip(path);

        assertEquals(path, bbExport.getPath());
    }


    @Test
    public void constructorTest_null() {
        final BbExportZip bbExport = new BbExportZip(null);
        assertNull(bbExport.getPath());
    }
}
