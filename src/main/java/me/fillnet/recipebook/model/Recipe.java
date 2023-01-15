package me.fillnet.recipebook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Setter
@Getter
public class Recipe {
    private String name;
    public int timeCooking;
    private List<Ingredient> ingredients;
    private List<String> step;
}
