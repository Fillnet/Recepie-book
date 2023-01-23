package me.fillnet.recipebook.service;

import org.springframework.beans.factory.annotation.Value;

public interface FileServiceRecipe {
    @Value("${nameR.of.data.file}")
    boolean saveToFile(String json);

    String readFromFile();
}
