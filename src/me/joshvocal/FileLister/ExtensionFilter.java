package me.joshvocal.FileLister;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * ExtensionFilter class is a full class that implements the FileFilter interface
 * which allows the user to filter what type of files they want. If there are no
 * file extensions entered, FileLister will display all files in the directory.
 */

public class ExtensionFilter implements FileFilter {

    private static List<File> filteredExtensions = new ArrayList<>();

    @Override
    public boolean accept(File file) {
        for (String extension : FileLister.getFileExtensions()) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            } else if (file.isDirectory()) {
                return true;
            }
        }
        return false;
    }

    public static List<File> filterExtensions(String sourcePath) {
        File sourceFile = new File(sourcePath);
        File[] fileList;

        if (FileLister.getFileExtensions().size() == 0) {
            fileList = sourceFile.listFiles();
        } else {
            fileList = sourceFile.listFiles(new ExtensionFilter());
        }

        for (File file : fileList) {
            if (file.isDirectory()) {
                filterExtensions(file.getAbsolutePath());
            } else {
                filteredExtensions.add(file);
            }
        }
        return filteredExtensions;
    }
}
