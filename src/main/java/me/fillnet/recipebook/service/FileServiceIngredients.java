package me.fillnet.recipebook.service;

import me.fillnet.recipebook.exception.ExceptionWithOperationFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

public interface FileServiceIngredients {
    boolean saveToFile(String json);

    String readFromFile() throws ExceptionWithOperationFile;

    File getDataFile();

    boolean cleanDataFile();
}
