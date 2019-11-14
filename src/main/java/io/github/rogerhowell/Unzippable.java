package io.github.rogerhowell;

import java.nio.file.Path;

/**
 * This interface describes an entity that can be unzipped to disk.
 */
public interface Unzippable {

    /**
     * Method to trigger unzipping of this entity into a directory.
     *
     * Defaults to:
     * - No recursive unzip
     * - Deny overwriting
     *
     * @param destinationDirectory The directory to which the contents of this entity can be unzipped into.
     */
    void unzipTo(Path destinationDirectory);


    /**
     * Method to trigger unzipping of this entity into a directory.
     *
     * @param destinationDirectory The directory to which the contents of this entity can be unzipped into.
     * @param recursiveUnzip       True if nested zip files should also be unzipped, else false.
     * @param allowOverwriting     True if we allow overwriting of any files inside the destination directory.
     */
    void unzipTo(Path destinationDirectory, boolean recursiveUnzip, boolean allowOverwriting);

}
