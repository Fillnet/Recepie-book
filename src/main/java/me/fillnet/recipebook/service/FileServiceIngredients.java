package me.fillnet.recipebook.service;

import org.springframework.beans.factory.annotation.Value;

public interface FileServiceIngredients{
    @Value("${nameI.of.data.file}")
    boolean saveToFile(String json);

    String readFromFile();
}
