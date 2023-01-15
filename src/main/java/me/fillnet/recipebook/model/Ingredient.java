package me.fillnet.recipebook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Ingredient {
    public String title;
    public int totalIngredients;
    private final String unit;
}
