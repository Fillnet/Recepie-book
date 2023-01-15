package me.fillnet.recipebook.service;

import me.fillnet.recipebook.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientService {
    private final Map<String, Ingredient> ingredients = new HashMap<>();

    public Collection<Ingredient> getAll() {
        return ingredients.values();
    }

    public Ingredient addNewIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient.getTitle())) {
            throw new RuntimeException("такой ингридиент уже есть");
        } else {
            ingredients.put(ingredient.getTitle(), ingredient);
        }
        return ingredient;
    }

    public Ingredient updateIngredient(String id, Ingredient ingredient) {
        Ingredient serviceIngredient = ingredients.get(id);
        if (serviceIngredient == null) {
            throw new RuntimeException("Нет такого ингредиента");
        }
        serviceIngredient.setTitle(ingredient.getTitle());
        serviceIngredient.setTotalIngredients(ingredient.getTotalIngredients());
        return serviceIngredient;
    }

    public Ingredient removeIngredientById(String id) {
        return ingredients.remove(id);
    }
}

