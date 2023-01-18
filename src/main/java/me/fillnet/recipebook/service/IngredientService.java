package me.fillnet.recipebook.service;

import me.fillnet.recipebook.model.Ingredient;

import java.util.Collection;

public interface IngredientService {
    Collection<Ingredient> getAllIngredient();

    Ingredient addNewIngredient(Ingredient ingredient);

    Ingredient updateIngredient(String id, Ingredient ingredient);

    Ingredient removeIngredientById(String id);

    void saveToFile();

    // не могу понять что здесь не так, без этого кода запускается, с ним нет
    void readFromFile();
}
