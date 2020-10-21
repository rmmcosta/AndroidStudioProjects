package com.example.recipesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.recipesapp.recipes.Recipe;
import com.example.recipesapp.recipes.Unit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeItemClickListener {

    private List<Recipe> recipes;
    private RecyclerView rvRecipes;
    public static final String RECIPE_INTENT = "RECIPEINTENT";

    public void showRecipeDetail(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_INTENT, recipe);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvRecipes = findViewById(R.id.rvRecipes);
        initializeRecipes();
        RecipesAdapter recipesAdapter = new RecipesAdapter(recipes, this, this);
        rvRecipes.setAdapter(recipesAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeRecipes() {
        recipes = new ArrayList<>();
        addBacalhauRecipe();
        addAçadoRecipe();
        addEmpadaoRecipe();
    }

    private void addEmpadaoRecipe() {
        Recipe empadao = new Recipe("Empadão de bacalhau", "Puré de batata a envolver bacalhau desfiado com azeite e cebola numa camada de couve coração");
        empadao.addIngredient(200, Unit.g, "Batatas");
        empadao.addIngredient(100, Unit.g, "Bacalhau");
        empadao.addIngredient(1, Unit.unit, "Ovo");
        empadao.addIngredient(100, Unit.g, "Coraçãozinho");
        empadao.addIngredient(1, Unit.unit, "Cebola");
        empadao.addIngredient(50, Unit.ml, "Azeite");
        recipes.add(empadao);
    }

    private void addAçadoRecipe() {
        Recipe açado = new Recipe("Assado", "Vitela assada com batatinhas");
        açado.addIngredient(500, Unit.g, "Vitela");
        açado.addIngredient(10, Unit.unit, "Batata");
        açado.addIngredient(1, Unit.unit, "Cebola");
        açado.addIngredient(50, Unit.ml, "Vinho branco");
        recipes.add(açado);
    }

    private void addBacalhauRecipe() {
        Recipe bacalhau = new Recipe("Bacalhau com natas", "Empadão de bacalhau com natas e um pouco de noz moscada");
        bacalhau.addIngredient(200, Unit.g, "Batatas");
        bacalhau.addIngredient(100, Unit.g, "Bacalhau");
        bacalhau.addIngredient(1, Unit.unit, "Pacote de Natas");
        bacalhau.addIngredient(1, Unit.pitada, "Noz moscada");
        recipes.add(bacalhau);
    }

    @Override
    public void onClickRecipeItem(Recipe recipe) {
        showRecipeDetail(recipe);
    }
}