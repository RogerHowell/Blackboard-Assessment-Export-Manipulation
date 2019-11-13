package io.github.rogerhowell.model;

import java.nio.file.Path;

/**
 * A class to model and manipulate a
 */
public class BbExportZip {
    private static final boolean DEFAULT_VERIFY_FILE_EXISTS = true;

    private final Path path;


    public BbExportZip(final Path path) {
        this(path, BbExportZip.DEFAULT_VERIFY_FILE_EXISTS);
    }


    public BbExportZip(final Path path, final boolean verifyFileExists) {
        if (verifyFileExists) {
            if (!path.toFile().exists()) {
                throw new IllegalArgumentException("Flag to verify file existence set to true, and file not found at the given path: " + path.toString());
            }
        }
        this.path = path;
    }


    public Path getPath() {
        return this.path;
    }


}
