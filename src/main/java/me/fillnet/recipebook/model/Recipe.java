package me.fillnet.recipebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Recipe {
    private String name;
    private int timeCooking;
    private List<Ingredient> ingredients;
    private List<String> step;

}
