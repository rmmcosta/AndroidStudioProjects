package com.example.recipesapp.recipes;

import android.media.Image;

import androidx.annotation.NonNull;

import java.util.List;

enum Unit {g, lt}

public class Recipe {
    private Image image;
    private List<Ingredient> ingredientList;
    private String title, description;

    public Recipe(Image image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void addIngredient(int quantity, Unit unit, String name) {
        ingredientList.add(new Ingredient(quantity, name, unit));
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public class Ingredient {
        private int quantity;
        private String name;
        private Unit unit;

        public Ingredient(int quantity, String name, Unit unit) {
            this.quantity = quantity;
            this.name = name;
            this.unit = unit;
        }

        @NonNull
        @Override
        public String toString() {
            return quantity + " " + unit + " " + name;
        }
    }
}
