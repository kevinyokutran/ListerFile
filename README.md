# FileLister

![FileLister Picture](https://github.com/joshvocal/FileLister/blob/master/example.png)

 FileLister provides a text interface display of all files of a directory
 and writes those files onto a text file. The user must enter a source path,
 a directory to search in, and a target path, where they want to write the files to,
 from the command line. The user may enter file extensions to filter certain files after
 the source path and target path are specified. If there are no file extensions entered,
 FileLister will display all files in the directory. If there are not enough command
 arguments entered, FileLister will display an error message to the user and exit.

## Run

```
cd path/to/file/FileLister/src
javac me/joshvocal/FileLister/FileLister.java
java me.joshvocal.FileLister.FileLister <source folder> <target file> [<extensions(s)>]
```

### source folder

The source folder is the directory path that you want to search for files.

### target file

Target file is the text file that you want to write the list of files in the directory to. If there is no text file avaliable, you can create one by adding a textfile.txt to the end of the directory path.

### extensions

You can search for files with various extentions. You may list no extensions if you want to find all files, or one to many if you want to filter your search.


```
java me.joshvocal.FileLister.FileLister <source folder> <target file> .pdf
```

or

```
java me.joshvocal.FileLister.FileLister <source folder> <target file> .pdf .html .mp4
```
