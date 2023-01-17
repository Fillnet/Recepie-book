package me.fillnet.recipebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.fillnet.recipebook.model.Ingredient;
import me.fillnet.recipebook.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ingredient")
@Tag(name = ("Ингридиенты"), description = "оперции с ингридиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @Operation(
            summary = "Получение списка ингридиентов",
            description = "получение списка ингридиентов"
    )
    public Collection<Ingredient> getAll() {
        return this.ingredientService.getAll();
    }

    @PostMapping
    @Operation(
            summary = "Добавление ингридиента",
            description = "добавление ингридиента"
    )
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return this.ingredientService.addNewIngredient(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление ингридиента",
            description = "обновление ингридиента по ID"
    )
    public Ingredient updateIngredient(@PathVariable("id") String id, @RequestBody Ingredient ingredient) {
        return this.ingredientService.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингридиента",
            description = "удаление ингридиента по ID"
    )
    public Ingredient removeIngredientById(@PathVariable("id") String id) {
        return this.ingredientService.removeIngredientById(id);
    }
}
