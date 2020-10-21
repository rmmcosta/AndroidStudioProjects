package com.example.recipesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.recipesapp.recipes.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private TextView tvTitle, tvDescription;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        recipe = (Recipe) getIntent().getSerializableExtra(MainActivity.RECIPE_INTENT);
        initializeTextViews();
        fillTextViewsWithRecipe();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.rvIngredients);
        recyclerView.setAdapter(new IngredientsAdapter(recipe.getIngredientList(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fillTextViewsWithRecipe() {
        tvTitle.setText(recipe.getTitle());
        tvDescription.setText(recipe.getDescription());
    }

    private void initializeTextViews() {
        tvTitle = findViewById(R.id.tvRecipeTitle);
        tvDescription = findViewById(R.id.tvRecipeDescription);
    }
}