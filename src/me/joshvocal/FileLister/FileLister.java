package me.joshvocal.FileLister;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * FileLister class provides a text interface display of all files of a directory
 * and writes those files onto a file. The user must enter a source path,
 * a directory to search in, and a target path, where they want to write the files to
 * from the command line. The user may enter file extensions to filter certain files after
 * the source path and target path are specified. If there are no file extensions entered,
 * FileLister will display all files in the directory. If there are not enough command
 * arguments entered, FileLister will display an error message to the user and exit.
 *
 * @author Josh Vocal
 */


public class FileLister {

    private static final int MIN_ARGS_NEEDED = 2;
    private static final int SOURCE_PATH_INDEX = 0;
    private static final int TARGET_PATH_INDEX = 1;
    private static final double BYTES_TO_MEBIBYTE = Math.pow(2, 20);

    private static final String END_HELP_MESSAGE = "Error: Requires at least " + MIN_ARGS_NEEDED + " arguments";
    private static final String FILE_STATS_HEADER = "Statistics on Files Found:";
    private static final String WRITING_FILES_MESSAGE = "Writing file list to output file:";
    private static final String FILES_HEADER = "Files:";

    private static List<File> filteredExtensions = new ArrayList<>();
    private static List<String> fileExtensions = new ArrayList<>();
    private static String sourcePath;
    private static String targetPath;
    private static int numFilesFound;

    public static void main(String[] args) {

        checkSufficientArguments(args);
        setFilteredExtensions();
        writeToFile();
        printFileStats();
        printFiles();
    }

    private static void checkSufficientArguments(String[] args) {
        if (args.length < MIN_ARGS_NEEDED) {
            System.out.println(END_HELP_MESSAGE);
            System.exit(0);
        } else {
            storeExtensions(args, fileExtensions);
        }
    }

    private static void storeExtensions(String[] args, List<String> fileExtensions) {
        sourcePath = args[SOURCE_PATH_INDEX];
        targetPath = args[TARGET_PATH_INDEX];

        for (int i = MIN_ARGS_NEEDED; i < args.length; i++) {
            fileExtensions.add(args[i].toLowerCase());
        }
    }

    private static void setFilteredExtensions() {
        filteredExtensions = ExtensionFilter.filterExtensions(sourcePath);
    }

    private static void writeToFile() {
        File targetFile = new File(targetPath);

        try {
            PrintWriter writer = new PrintWriter(targetFile);
            for (File file : filteredExtensions ) {
                writer.println(file);
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printFileStats() {
        System.out.println(FILE_STATS_HEADER);
        printHeaderUnderline(FILE_STATS_HEADER);

        System.out.printf("%-12s %s%n", "Source Path:", sourcePath);
        System.out.printf("%-12s %s%n", "Target Path:", targetPath);
        System.out.printf("%-12s %s%n", "Extensions:", printExtensions(fileExtensions));
        System.out.printf("%-12s %d%n", "Files Found:", getFileCount());
        System.out.printf("%-12s %.2f MiB (%,.0f bytes)%n", "Total Size:",
                convertBytesToMebibyte(getTotalFileSize()), getTotalFileSize());
        System.out.println();
    }

    private static void printFiles() {
        System.out.println(FILES_HEADER);
        printHeaderUnderline(FILES_HEADER);

        for (File file : filteredExtensions) {
            System.out.println(file);
        }

        System.out.printf("%n%s %s%n", WRITING_FILES_MESSAGE, targetPath);
    }

    private static void printHeaderUnderline(String header) {
        for (int i = 0; i < header.length(); i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    private static String printExtensions(List<String> fileExtensions) {
        return String.join(" ", fileExtensions);
    }

    public static List<String> getFileExtensions() {
        return fileExtensions;
    }

    private static void incrementFilesFound() {
        numFilesFound++;
    }

    private static int getFileCount() {
        for (File file : filteredExtensions) {
            incrementFilesFound();
        }
        return numFilesFound;
    }

    private static double getTotalFileSize() {
        double totalFileSize = 0;
        for (File fileSize : filteredExtensions) {
            totalFileSize += fileSize.length();
        }
        return totalFileSize;
    }

    private static double convertBytesToMebibyte(double totalFileSize) {
        return totalFileSize/BYTES_TO_MEBIBYTE;
    }

}
