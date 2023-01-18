package me.fillnet.recipebook.service;

public interface FileServiceRecipe {
    boolean saveToFile(String json);

    String readFromFile();
}
