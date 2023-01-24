package me.fillnet.recipebook.service;

import me.fillnet.recipebook.exception.ExceptionWithOperationFile;

import java.io.File;
import java.nio.file.Path;

public interface FileServiceRecipe {
    boolean saveToFile(String json);

    String readFromFile() throws ExceptionWithOperationFile;

    File getDataFile();

    boolean cleanDataFile();

    Path createTempFile(String suffix);


}
