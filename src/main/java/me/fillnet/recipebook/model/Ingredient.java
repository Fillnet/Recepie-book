package me.fillnet.recipebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Ingredient  {
    public String title;
    private int totalIngredients;
    private String unit;
}
