package me.fillnet.recipebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.fillnet.recipebook.model.Recipe;
import me.fillnet.recipebook.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/recipe")
@Tag(name = ("Рецепт"), description = "операции с рецептами")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @Operation(
            summary = "Получение списка рецептов",
            description = "получение списка рецептов"
    )
    public Collection<Recipe> getAllRecipe() {
        return this.recipeService.getAllRecipe();
    }

    @PutMapping
    @Operation(
            summary = "Добавление нового рецепта",
            description = "добавление нового рецепта"
    )
    public Recipe addNewRecipe(@RequestBody Recipe recipe) {
        return this.recipeService.addNewRecipe(recipe);
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Изменение рецепта",
            description = "изменение рецепта по ID"
    )
    public Recipe editRecipe(@PathVariable("id") String id, Recipe recipe) {
        return this.recipeService.editRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта",
            description = "удаление рецепта по ID"
    )
    public Recipe removeRecipe(@PathVariable("id") String id) {
        return this.recipeService.removeRecipe(id);
    }
}
