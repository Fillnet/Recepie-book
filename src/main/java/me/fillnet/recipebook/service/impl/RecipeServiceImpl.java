package me.fillnet.recipebook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.fillnet.recipebook.exception.ExceptionWithOperationFile;
import me.fillnet.recipebook.model.Recipe;
import me.fillnet.recipebook.service.RecipeService;
import me.fillnet.recipebook.exception.ExceptionWithChekingRecipes;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

//import static me.fillnet.recipebook.service.impl.FileServiceRecipeImpl.getPath;

@Service
public class RecipeServiceImpl implements RecipeService {
    private FileServiceRecipeImpl fileServiceRecipe;
    private Map<String, Recipe> recipes = new HashMap<String, Recipe>();
    private Long counter = 0L;


    public RecipeServiceImpl(FileServiceRecipeImpl fileServiceRecipe) {
        this.fileServiceRecipe = fileServiceRecipe;
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
    public Collection<Recipe> getAllRecipe() {
        return recipes.values();
    }

    @Override
    public Recipe addNewRecipe(Recipe recipe) throws ExceptionWithChekingRecipes {
        if (recipes.containsKey(counter)) {
            throw new ExceptionWithChekingRecipes("Такой рецепт уже есть");
        } else {
            recipes.put(String.valueOf(this.counter++), recipe);
            saveToFile();
        }
        return recipe;
    }

    @Override
    public Recipe editRecipe(String id, Recipe recipe) throws ExceptionWithChekingRecipes {
        Recipe serviceRecipe = recipes.get(id);
        if (serviceRecipe == null) {
            throw new ExceptionWithChekingRecipes("Нет такого рецепта");
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


    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileServiceRecipe.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }


    public void readFromFile() throws ExceptionWithOperationFile {
        try {
            String json = fileServiceRecipe.readFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<>() {

            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка чтения из файла");
        }


    }

    @Override
    public Path createRecipeFile() throws IOException {
        Path path = fileServiceRecipe.createTempFile("recipes");
        for (Recipe recipe : recipes.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(("Название рецепта: " + recipe.getName() + '\n' + '\n' +
                        "Время приготовления: " + recipe.getTimeCooking() + '\n' + '\n' +
                        "Ингредиенты: " + recipe.getIngredients() + '\n' + '\n' +
                        "Инструкция приготовления: " + '\n' + '\n' + recipe.getStep() + '\n'));
            }
        }
        return path;
    }

}


