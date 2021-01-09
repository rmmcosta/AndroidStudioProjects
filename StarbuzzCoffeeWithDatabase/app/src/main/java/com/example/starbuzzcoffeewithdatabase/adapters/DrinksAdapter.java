package com.example.starbuzzcoffeewithdatabase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbuzzcoffeewithdatabase.Drink;
import com.example.starbuzzcoffeewithdatabase.R;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.DrinksViewHolder> {
    private final Context context;
    private final Drink[] drinkOptions;
    private final View.OnClickListener onDrinkClick;

    public DrinksAdapter(Context context, Drink[] drinkOptions, View.OnClickListener onDrinkClick) {
        this.context = context;
        this.drinkOptions = drinkOptions;
        this.onDrinkClick = onDrinkClick;
    }

    @NonNull
    @Override
    public DrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.drinks_item, parent, false);
        itemView.setOnClickListener(onDrinkClick);
        return new DrinksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksViewHolder holder, int position) {
        holder.tvDrinkItem.setText(drinkOptions[position].getName());
    }

    @Override
    public int getItemCount() {
        return drinkOptions.length;
    }

    public class DrinksViewHolder extends RecyclerView.ViewHolder {
        TextView tvDrinkItem;

        public DrinksViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDrinkItem = itemView.findViewById(R.id.tvDrinksItem);
        }
    }
}
