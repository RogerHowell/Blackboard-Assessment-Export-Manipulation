package io.github.rogerhowell.model;

import io.github.rogerhowell.Jsonable;
import io.github.rogerhowell.exceptions.ParameterValidationFailException;
import io.github.rogerhowell.util.BbExtractionUtil;
import io.github.rogerhowell.util.FileUtil;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;

/**
 * A class to model and manipulate a
 */
public class BbExportZip implements Jsonable {
    private static final boolean DEFAULT_VERIFY_FILE_EXISTS = true;

    private final Path          path;
    private final String        moduleName;
    private final String        cohortYear;
    private final String        taskName;
    private final ZonedDateTime exportTimestamp;
    private       boolean       isFileExistenceChecked;


    public BbExportZip(final Path path) {
        this(path, BbExportZip.DEFAULT_VERIFY_FILE_EXISTS);
    }


    public BbExportZip(final Path path, final boolean verifyFileExists) {
        if (path == null) {
            throw new ParameterValidationFailException("Path must not be null.");
        }

        this.isFileExistenceChecked = false;
        if (verifyFileExists) {
            if (!Files.exists(path)) {
                throw new ParameterValidationFailException("Flag to verify file existence set to true, and file not found at the given path: " + path.toString());
            } else {
                this.isFileExistenceChecked = true;
            }
        }


        final String filename = path.getFileName().toString();

        this.path = path;
        this.moduleName = BbExtractionUtil.bbExportFile_moduleCode(filename);
        this.cohortYear = BbExtractionUtil.bbExportFile_cohortYear(filename);
        this.taskName = BbExtractionUtil.bbExportFile_taskName(filename);
        this.exportTimestamp = BbExtractionUtil.bbExportFile_TimestampToZonedDateTime(filename);

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
