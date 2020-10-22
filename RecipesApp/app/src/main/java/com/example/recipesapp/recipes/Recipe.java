package com.example.recipesapp.recipes;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
    private int drawable;
    private List<Ingredient> ingredientList;
    private String title, description;

    public Recipe(int drawable, String title, String description) {
        this.drawable = drawable;
        this.title = title;
        this.description = description;
        ingredientList = new ArrayList<>();
    }

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
        ingredientList = new ArrayList<>();
    }

    public void addIngredient(double quantity, Unit unit, String name) {
        ingredientList.add(new Ingredient(quantity, name, unit));
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDrawable() {
        return drawable;
    }

    public class Ingredient implements Serializable {
        private final double quantity;
        private final String name;
        private final Unit unit;

        public Ingredient(double quantity, String name, Unit unit) {
            this.quantity = quantity;
            this.name = name;
            this.unit = unit;
        }

        @NonNull
        @Override
        public String toString() {
            return quantity + " " + getUnitDescription(unit) + " " + name;
        }

        private String getUnitDescription(Unit unit) {
            switch (unit) {
                case g:
                    return "g";
                case lt:
                    return "lt";
                case ml:
                    return "ml";
                case pitada:
                    return "pitada";
                default:
                    return "";
            }
        }
    }
}
