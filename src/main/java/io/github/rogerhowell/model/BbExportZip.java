package io.github.rogerhowell.model;

import java.nio.file.Path;

/**
 * A class to model and manipulate a
 */
public class BbExportZip {
    private final Path path;


    public BbExportZip(final Path path) {
        this.path = path;
    }


    public Path getPath() {
        return this.path;
    }


}
