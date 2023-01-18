package me.fillnet.recipebook.service;

import me.fillnet.recipebook.model.Ingredient;

public interface IngredientService {
    Ingredient addNewIngredient(Ingredient ingredient);

    Ingredient updateIngredient(String id, Ingredient ingredient);

    Ingredient removeIngredientById(String id);
}
