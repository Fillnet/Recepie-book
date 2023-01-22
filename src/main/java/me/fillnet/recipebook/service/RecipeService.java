package me.fillnet.recipebook.service;

import me.fillnet.recipebook.model.Recipe;
import me.fillnet.recipebook.service.exception.ExceptionWithChekingRecipes;

import java.util.Collection;

public interface RecipeService {
    void readFromFile();
    void saveToFile();

    Collection<Recipe> getAllRecipe();

    Recipe addNewRecipe(Recipe recipe) throws ExceptionWithChekingRecipes;

    Recipe editRecipe(String id, Recipe recipe) throws ExceptionWithChekingRecipes;

    Recipe removeRecipe(String id);
}
