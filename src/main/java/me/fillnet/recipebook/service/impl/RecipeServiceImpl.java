package me.fillnet.recipebook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.fillnet.recipebook.model.Recipe;
import me.fillnet.recipebook.service.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private FileServiceRecipeImpl fileServiceRecipe;
    private Map<Long, Recipe> recipes = new HashMap<>();
    private Long counter = 0L;


    public RecipeServiceImpl(FileServiceRecipeImpl fileServiceRecipe) {
        this.fileServiceRecipe = fileServiceRecipe;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }


    @Override
    public Collection<Recipe> getAllRecipe() {
        return recipes.values();
    }

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
        if (recipes.containsKey(counter)) {
            throw new RuntimeException("Такой рецепт уже есть");
        } else {
            recipes.put(this.counter++, recipe);
            saveToFile();
        }
        return recipe;
    }

    @Override
    public Recipe editRecipe(String id, Recipe recipe) {
        Recipe serviceRecipe = recipes.get(id);
        if (serviceRecipe == null) {
            throw new RuntimeException("Нет такого рецепта");
        }
        serviceRecipe.setName(recipe.getName());
        serviceRecipe.setIngredients(recipe.getIngredients());
        serviceRecipe.setTimeCooking(recipe.getTimeCooking());
        serviceRecipe.setStep(recipe.getStep());
        saveToFile();
        return serviceRecipe;
    }

    @Override
    public Recipe removeRecipe(String id) {
        return recipes.remove(id);
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileServiceRecipe.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


//    @Override
// не могу понять что здесь не так, без этого кода запускается, с ним нет
//    public void readFromFile() {
//        String json = fileServiceRecipe.readFromFile();
//        try {
//            recipes = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
//
//            });
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }


//    }

    @Override
    public void readFromFile() {

    }
}
