# Blackboard Assessment Export Manipulation

## Overview
### The "problem"
- The output from a Blackboard assessment file export is very "flat" - a single directory containing the files as submitted - and with files renamed.
- This is fine if e.g. it is a single PDF or similar for an essay, but is not appropriate/helpful where multiple files have been uploaded (e.g. a coding project containing multiple source files, where the naming of files is very important).
- A common workaround is to upload a container (e.g. a zip file) to avoid Blackboard renaming the files and allowing the inclusion of a directory structure (both critical when it comes to programming / source code).

### The "solution"
- This project allows you to pass it the path to a zip folder containing the export of submitted assessment responses, and it will create a directory structure based on the data it infers from the submitted files (e.g. usernames, export dates, submission times).
- This project then uses the file naming and the content of Blackboard-generated files (i.e. the inferred data mentioned above), and unzips/moves/rearranges the submitted work into a logical directory structure based on this inferred data (restoring original filenames). 
- See below for details on what this looks like. 


## Output Directory Structure
This project will take zip file, unzip it, and then rearrange the contents into the following file structure: 
 
```
\ Output container directory
|-\ Module Code
|-|-\ Task Name
|-|-|-\ Student ID
|-|-|-|-\ Submission Info (e.g. timestamp)

|-|-|-|-|-\ Metadata
|-|-|-|-|-| - <Submisison description file (as provided by Blackboard)>.txt
|-|-|-|-|-| - <Submisison description file (generated using this project)>.json
|-|-|-|-|-| - <Submisison field (if present in the description file)>.txt
|-|-|-|-|-| - <Submisison comments (if present in the description file)>.txt

|-|-|-|-|-\ Submitted Files
|-|-|-|-|-| - <placeholder>.java
|-|-|-|-|-| - <placeholder>.pde
|-|-|-|-|-| - <placeholder>.pdf
|-|-|-|-|-| - <placeholder>.txt

```
 
## Usage
Pass the path to the export directory, the path to the output directory as arguments, and whether we should allow overwriting of any destination files (or throw an exception in the case of the directory/files already existing).

- The output directory path is optional, defaulting to the name of the zip file (with the `.zip` suffix removed).
- Overwriting destination files defaults to false (meaning that the destination directory must not already exist / contain any files)


// TODO: Insert here some example command line arguments as a standalone project

// TODO: Insert here notes about use of this as a module/dependency in code


## Changelog



## Development

- The branch `master` is marked as protected
    - Development should be done on the branch `dev` or similar, then merged into master via a pull request
    - This workflow will help to ensure branch `master` remains always "deployable"    
 

### Conventions

This section defines the conventions used within this project.

Updates will be added on an ongoing/rolling basis as and when they are deliberately considered. 

This represents an "ideal" goal, and does not necessarily represent the conventions _actually_ used within this codebase. 
Any deviances should be corrected during normal development/refactoring.

#### Naming

- JSON keys are **always** 
    - In lowercase `snake_case` (as opposed to `camelCase`)
   
- Variable names used within code 
    - Should be `camelCase`.
   
   
#### Tests

- Each model object must have unit tests for 
    - all constructors 
    - all simple getters 

- Avoid directly using `Paths.get` or similar in tests
    - `TestUtil.testResourcePath` exists as a strongly suggested alternative

- Always use forward slash (`/`) as a directory separator 
    - Backslash (`\`) is NOT cross-OS compatible (doesn't work on Linux systems - maybe includes MacOS?) 

- Test cases will normally have the following components, separated by an underscore (`_`)
    - First - the type of `@Test` to be rune
        - `test` (if the test performs assertions), 
        - `info` (if purely informational -- i.e. just print to the console, or `assumeTrue`/`assumeFalse` or similar)
    
    - Middle - the component
        - The component/element under test 
        - Examples: `constructor`, or `toJson`
        
    - Last - differentiation of inputs/scenarios
        - Detail about the input/scenario
        - Examples: `nullParameter(s)`, or `existingFile`, or `edgeOfBounds`, or `outOfBounds`
