package me.fillnet.recipebook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.fillnet.recipebook.model.Ingredient;
import me.fillnet.recipebook.model.Recipe;
import me.fillnet.recipebook.service.FileServiceRecipe;
import me.fillnet.recipebook.service.IngredientService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final Map<String, Ingredient> ingredients = new HashMap<>();

    public Collection<Ingredient> getAll() {
        return ingredients.values();
    }
    private final

    @PostConstruct
    private void init() {
        readFromFile();
    }

    private void readFromFile() {
    }

    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient.getTitle())) {
            throw new RuntimeException("такой ингридиент уже есть");
        } else {
            ingredients.put(ingredient.getTitle(), ingredient);
        }
        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(String id, Ingredient ingredient) {
        Ingredient serviceIngredient = ingredients.get(id);
        if (serviceIngredient == null) {
            throw new RuntimeException("Нет такого ингредиента");
        }
        serviceIngredient.setTitle(ingredient.getTitle());
        serviceIngredient.setTotalIngredients(ingredient.getTotalIngredients());
        return serviceIngredient;
    }

    @Override
    public Ingredient removeIngredientById(String id) {
        return ingredients.remove(id);
    }
    @Override

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            FileServiceRecipe fileServiceIngredient;
            fileServiceIngredient.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

