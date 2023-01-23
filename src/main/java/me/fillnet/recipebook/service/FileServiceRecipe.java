package me.fillnet.recipebook.service;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

public interface FileServiceRecipe {
    @Value("${namer.of.data.file}")
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean cleanDataFile();
}
