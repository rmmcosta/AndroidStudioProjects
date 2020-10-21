package com.example.recipesapp.recipes;

import android.media.Image;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
    private Image image;
    private List<Ingredient> ingredientList;
    private String title, description;

    public Recipe(Image image, String title, String description) {
        this.image = image;
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

    public Image getImage() {
        return image;
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
            return quantity + " " + (unit != unit ? unit : "") + " " + name;
        }
    }
}
