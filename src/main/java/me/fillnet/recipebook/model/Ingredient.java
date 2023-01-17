package me.fillnet.recipebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Ingredient  {
    public String title;
    private int totalIngredients;
    private final String unit;
}
