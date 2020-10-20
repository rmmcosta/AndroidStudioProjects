package com.example.recipesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipesapp.recipes.Recipe;

import java.util.List;
import java.util.zip.Inflater;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    private List<Recipe> recipeList;
    private Context context;

    public RecipesAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        holder.tvTitle.setText(recipeList.get(position).getTitle());
        holder.tvDescription.setText(recipeList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvDescription = itemView.findViewById(R.id.tvRecipeDescription);
        }
    }
}
