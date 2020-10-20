package com.example.recipesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.recipesapp.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Recipe> recipes;
    private RecyclerView rvRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvRecipes = findViewById(R.id.RVRecipes);
        initializeRecipes();
        RecipesAdapter recipesAdapter = new RecipesAdapter(recipes, this);
        rvRecipes.setAdapter(recipesAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeRecipes() {
        recipes = new ArrayList<>();
        recipes.add(new Recipe("Bacalhau com natas", "Empadão de bacalhau com natas e um pouco de noz moscada"));
        recipes.add(new Recipe("Assado", "Vitela assada com batatinhas"));
        recipes.add(new Recipe("Empadão de bacalhau", "Puré de batata a envolver bacalhau desfiado com azeite e cebola numa camada de couve coração"));
    }
}