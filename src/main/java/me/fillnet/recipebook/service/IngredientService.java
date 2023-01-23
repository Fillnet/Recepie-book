package me.fillnet.recipebook.service;

import me.fillnet.recipebook.model.Ingredient;
import me.fillnet.recipebook.exception.ExceptionWithChekinIngredients;

import java.util.Collection;

public interface IngredientService {
    Collection<Ingredient> getAllIngredient();

    Ingredient addNewIngredient(Ingredient ingredient) throws ExceptionWithChekinIngredients;

    Ingredient updateIngredient(String id, Ingredient ingredient) throws  ExceptionWithChekinIngredients;

    Ingredient removeIngredientById(String id);

    void saveToFile();

    void readFromFile();
}
