package me.fillnet.recipebook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.fillnet.recipebook.exception.ExceptionWithOperationFile;
import me.fillnet.recipebook.model.Ingredient;
import me.fillnet.recipebook.service.FileServiceIngredients;
import me.fillnet.recipebook.service.IngredientService;
import me.fillnet.recipebook.exception.ExceptionWithChekinIngredients;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private FileServiceIngredients fileServiceIngredient;

    private Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

    public IngredientServiceImpl(FileServiceIngredients fileServiceIngredients) {
        this.fileServiceIngredient = fileServiceIngredients;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Ingredient> getAllIngredient() {
        return ingredients.values();
    }

    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) throws ExceptionWithChekinIngredients {
        if (ingredients.containsKey(ingredient.getTitle())) {
            throw new ExceptionWithChekinIngredients("такой ингридиент уже есть");
        } else {
            ingredients.put(ingredient.getTitle(), ingredient);
            saveToFile();
        }
        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(String id, Ingredient ingredient) throws ExceptionWithChekinIngredients {
        Ingredient serviceIngredient = ingredients.get(id);
        if (serviceIngredient == null) {

            throw new ExceptionWithChekinIngredients("Нет такого ингредиента");
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

    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileServiceIngredient.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }


    public void readFromFile() {
        try {
            String json = fileServiceIngredient.readFromFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<>() {

            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка чтения из файла");
        }
    }
}

