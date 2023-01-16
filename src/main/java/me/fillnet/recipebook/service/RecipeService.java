package me.fillnet.recipebook.service;

import me.fillnet.recipebook.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeService {
    private final Map<String, Recipe> recipes = new HashMap<>();
    public Collection<Recipe> getAllRecipe() {
        return recipes.values();
    }

    public Recipe addNewRecipe(Recipe recipe) {
        if (recipes.containsKey(recipe.getName())) {
            throw new RuntimeException("Такой рецепт уже есть");
        } else {
            recipes.put(recipe.getName(), recipe);
        }
        return recipe;
    }

//    Recipe getRecipeById(int idRecipe);

    public Recipe editRecipe(String id, Recipe recipe) {
        Recipe serviceRecipe = recipes.get(id);
        if (serviceRecipe == null) {
            throw new RuntimeException("Нет такого рецепта");
        } serviceRecipe.setName(recipe.getName());
        serviceRecipe.setIngredients(recipe.getIngredients());
        serviceRecipe.setTimeCooking(recipe.getTimeCooking());
        serviceRecipe.setStep(recipe.getStep());
        return serviceRecipe;
    }

    public Recipe removeRecipe(String id) {
        return recipes.remove(id);
    }

}
