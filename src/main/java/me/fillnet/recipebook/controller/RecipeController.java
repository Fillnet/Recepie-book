package me.fillnet.recipebook.controller;

import me.fillnet.recipebook.model.Recipe;
import me.fillnet.recipebook.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public Collection<Recipe> getAllRecipe() {
        return this.recipeService.getAllRecipe();
    }

    @PostMapping
    public Recipe addNewRecipe(@RequestBody Recipe recipe) {
        return this.recipeService.addNewRecipe(recipe);
    }

    @PutMapping("/{id}")
    public Recipe editRecipe(@PathVariable("id") String id, Recipe recipe) {
        return this.recipeService.editRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    public Recipe removeRecipe(@PathVariable("id") String id) {
        return this.recipeService.removeRecipe(id);
    }
}
