package me.fillnet.recipebook.controller;

import me.fillnet.recipebook.model.Ingredient;
import me.fillnet.recipebook.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//@AllArgsConstructor
//@Getter
//@Setter
@RestController
@RequestMapping("/ingredient")

public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return this.ingredientService.addNewIngredient(ingredient);
    }
    @GetMapping
    public Collection<Ingredient> getAll() {
        return this.ingredientService.getAll();
    }

    @PostMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable("id") String id, @RequestBody Ingredient ingredient) {
       return this.ingredientService.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    public Ingredient removeIngredientById(@PathVariable("id") String id) {
        return this.ingredientService.removeIngredientById(id);
    }
}
