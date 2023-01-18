package me.fillnet.recipebook.service;

import me.fillnet.recipebook.model.Recipe;

import java.util.Collection;

public interface RecipeService {
    void readFromFile();

    Collection<Recipe> getAllRecipe();

    Recipe addNewRecipe(Recipe recipe);

    Recipe editRecipe(String id, Recipe recipe);

    Recipe removeRecipe(String id);
}
