package io.github.rogerhowell.model;

import io.github.rogerhowell.Jsonable;
import io.github.rogerhowell.util.FileUtil;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A class to model and manipulate a
 */
public class BbExportZip implements Jsonable {
    private static final boolean DEFAULT_VERIFY_FILE_EXISTS = true;

    private final Path    path;
    private       boolean isFileExistenceChecked;


    public BbExportZip(final Path path) {
        this(path, BbExportZip.DEFAULT_VERIFY_FILE_EXISTS);
    }


    public BbExportZip(final Path path, final boolean verifyFileExists) {
        this.isFileExistenceChecked = false;
        if (verifyFileExists) {
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Flag to verify file existence set to true, and file not found at the given path: " + path.toString());
            } else {
                this.isFileExistenceChecked = true;
            }
        }
        this.path = path;
    }


    public boolean fileExistsOnDisk() {
        return Files.exists(this.path);
    }


    public Path getPath() {
        return this.path;
    }


    @Override
    public Schema getSchema() {
        final JSONObject schemaJson = FileUtil.jsonObjectFromResourcePath("/json_schema/BbExportZip.schema.json");
        return SchemaLoader.load(schemaJson);
    }


    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("is_file_existence_checked", this.isFileExistenceChecked());
        jsonObject.put("file_exists", this.fileExistsOnDisk());
        jsonObject.put("path", FileUtil.pathToNormalisedString(this.getPath()));

        return jsonObject;
    }


    public boolean isFileExistenceChecked() {
        return this.isFileExistenceChecked;
    }
}
