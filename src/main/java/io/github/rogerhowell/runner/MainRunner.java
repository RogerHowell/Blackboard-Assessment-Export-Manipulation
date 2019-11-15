package io.github.rogerhowell.runner;

import io.github.rogerhowell.model.BbExportZip;
import io.github.rogerhowell.validation.ParameterValidationFailException;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.rogerhowell.validation.Validation.DISALLOW_NULL;
import static io.github.rogerhowell.validation.Validation.FILE_MUST_EXIST;
import static io.github.rogerhowell.validation.Validation.FILE_MUST_NOT_EXIST;

public class MainRunner {


    public static void doProgram(final Path pathToZip, final Path dirToExportTo) {
        DISALLOW_NULL.validate(pathToZip);
        DISALLOW_NULL.validate(dirToExportTo);

        FILE_MUST_EXIST.validate(pathToZip, "Path to the zip file must exist.");

        // TODO: Make this overridable by a flag (currently playing safe to prevent accidental deletion/overwriting)
        FILE_MUST_NOT_EXIST.validate(pathToZip, "Export dir already exists -- exiting to prevent changing/deletion of data.");


        // Flags
        final boolean unzip_createDirAndParentDirsIfNotExists      = true;
        final boolean arrangeFiles_alsoGroupSubmissionsBySubmitter = true;
        final boolean arrangeFiles_skipOnError                     = false;


        // Unzip the exported zip folder
        final BbExportZip bbExportZip = new BbExportZip(pathToZip);
//        final SubmissionsDir submissionsDir = bbExportZip.unzipTo(dirToExportTo, unzip_createDirAndParentDirsIfNotExists);
//
//        // Rearrange the submitted files to be one directory per submission (optionally nest multiple submissions by same submitter)
//        submissionsDir.doRearrrangeOfFilesOnDisk(arrangeFiles_alsoGroupSubmissionsBySubmitter, arrangeFiles_skipOnError);
//
//
//        // Do stuff (system related)
//        // -- The above simply does moving of files -- this section manipulates/changes/creates files
//        final List<SubmissionDir> submissionDirs = submissionsDir.getSubmissionDirs();
//        submissionDirs.foreach(submissionDir -> {
//            submissionDir.applyTransform(Transforms::undoBlackboardFileRenaming); // e.g. remove prefixes, and undo encoding (e.g. spaces being encoded as `20` -- a `%20` with the `%` stripped)
//            submissionDir.applyTransform(Transforms::recursiveUnzipAll); // If the submission contains zip folders, unzip those
//            submissionDir.applyTransform(Transforms::generateMetadataFiles); // e.g. a machine readable `.json` or `.xml` file representing this submission -- an extension beyond the blackboard-provided `.txt` file
//        });
//
//
//        // Do stuff (user related)
//        // -- This is where the "user" (of this code) will be given a collection of submissions
//        // -- ... which will then be the "fun stuff" in the codebase of whatever actually uses this project
//        final List<Submission> submissions = submissionsDirs
//                .stream()
//                .map(submissionDir -> submissionDir.getSubmission())
//                .collect(Collectors.toList());
//
//        submissions.foreach(submission -> {
//            submission.applyTransform(Transforms::transpilePdes); // "Processing" has `.pde` files that get collated into inner classes -- transpile into a single `.java` file
//            submission.applyTransform(Transforms::analyseJavaFiles); // Perhaps extract the AST and
//            submission.applyTransform(Transforms::extractJavaFilesToDatabase); // Perhaps extract the AST and upload it to a database
//        });

    }


    public static void main(final String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("No command line arguments found.");
            throw new ParameterValidationFailException("No command line arguments found.");
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            MainRunner.showHelp();
        }

        // TODO: Allow broader range of options (e.g. flags to allow overwriting of existing files)
        if (args.length == 2) {
            final String pathToZip     = args[0];
            final String dirToExportTo = args[1];
            MainRunner.runner(pathToZip, dirToExportTo);
        }
    }


    /**
     * TODO: Have this method just take in the args array
     *
     * @param pathToZipString
     * @param dirToExportToString
     */
    public static void runner(final String pathToZipString, final String dirToExportToString) {
        DISALLOW_NULL.validate(pathToZipString, "Path to the zip file must not be omitted.");
        DISALLOW_NULL.validate(dirToExportToString, "Path to the export directory must not be omitted. // TODO: Make this optional."); // TODO: Make this optional

        final Path pathToZip     = Paths.get(pathToZipString);
        final Path dirToExportTo = Paths.get(dirToExportToString);

        MainRunner.doProgram(pathToZip, dirToExportTo);

    }


    public static void showHelp() {
        final String helpText = "";

        System.out.println(helpText);
    }
}
