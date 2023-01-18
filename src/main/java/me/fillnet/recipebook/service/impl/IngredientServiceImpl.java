package me.fillnet.recipebook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.fillnet.recipebook.model.Ingredient;
import me.fillnet.recipebook.service.FileServiceIngredients;
import me.fillnet.recipebook.service.IngredientService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private FileServiceIngredients fileServiceIngredient;

    private HashMap<Long, Ingredient> ingredients = new HashMap<>();

    public IngredientServiceImpl(FileServiceIngredients fileServiceIngredients) {
        this.fileServiceIngredient = fileServiceIngredients;
    }
    @PostConstruct
    private void init() {
        readFromFile();
    }
    public void readFromFile() {
    }
    @Override
    public Collection<Ingredient> getAllIngredient() {
        return ingredients.values();
    }
    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient.getTitle())) {
            throw new RuntimeException("такой ингридиент уже есть");
        } else {
            ingredients.put(Long.valueOf(ingredient.getTitle()),ingredient);
            saveToFile();
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
        saveToFile();
        return serviceIngredient;
    }

    @Override
    public Ingredient removeIngredientById(String id) {
        return ingredients.remove(id);
    }
    @Override
    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileServiceIngredient.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
//        @Override
// не могу понять что здесь не так, без этого кода запускается, с ним нет
//        public void readFromFile() {
//        String json = fileServiceIngredient.readFromFile();
//        try {
//            ingredients = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Ingredient>>() {
//
//            });
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
}

