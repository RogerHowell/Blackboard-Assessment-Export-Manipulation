package io.github.rogerhowell.model;

import io.github.rogerhowell.Jsonable;
import io.github.rogerhowell.util.BbExtractionUtil;
import io.github.rogerhowell.util.FileUtil;
import io.github.rogerhowell.validation.Validation;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;

import static io.github.rogerhowell.util.BbExtractionUtil.ISO_INSTANT_MILLIS;

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
        Validation.DISALLOW_NULL.validate(path);
        Validation.DISALLOW_NULL.validate(verifyFileExists);

        if (verifyFileExists) {
            Validation.FILE_MUST_EXIST.validate(path);
            this.isFileExistenceChecked = true;
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


    private String getCohortYear() {
        return this.cohortYear;
    }


    private ZonedDateTime getExportTimestamp() {
        return this.exportTimestamp;
    }


    private String getModuleName() {
        return this.moduleName;
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
        jsonObject.put("module_name", this.getModuleName());
        jsonObject.put("cohort_year", this.getCohortYear());
        jsonObject.put("task_name", this.getTaskName());
        jsonObject.put("export_timestamp", ISO_INSTANT_MILLIS.format(this.getExportTimestamp()));
        jsonObject.put("export_timestamp_epoch_millis", this.getExportTimestamp().toInstant().toEpochMilli());

        return jsonObject;
    }


    private String getTaskName() {
        return this.taskName;
    }


    public boolean isFileExistenceChecked() {
        return this.isFileExistenceChecked;
    }
}
