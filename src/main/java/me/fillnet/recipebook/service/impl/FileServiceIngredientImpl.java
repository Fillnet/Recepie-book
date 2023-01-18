package me.fillnet.recipebook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.fillnet.recipebook.model.Ingredient;
import me.fillnet.recipebook.service.FileServiceIngredients;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
@Service
public class FileServiceIngredientImpl implements FileServiceIngredients {
    private FileServiceIngredients fileServiceIngredients;
    private Map<Long, Ingredient> ingredients = new HashMap<Long, Ingredient>();
    private Long counter = 0L;

    public FileServiceIngredientImpl(FileServiceIngredients fileServiceIngredients) {
        this.fileServiceIngredients = fileServiceIngredients;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    private void readFromFile() {
    }
    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(counter)) {
            throw new RuntimeException("Такой рецепт уже есть");
        } else {
            ingredients.put(this.counter++, ingredient);
            saveToFile();
        }
        return ingredient;
    }
    @Override
    public Ingredient editIngredient(String id, Ingredient ingredient) {
        Ingredient serviceIngredient = ingredients.get(id);
        if (serviceIngredient == null) {
            throw new RuntimeException("Нет такого рецепта");
        }
        serviceIngredient.setTitle(ingredient.getTitle());
        serviceIngredient.setTotalIngredients(ingredient.getTotalIngredients());
        saveToFile();
        return serviceIngredient;
    }
    public Ingredient removeIngredient(String id) {
        return ingredients.remove(id);
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileServiceIngredients.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
