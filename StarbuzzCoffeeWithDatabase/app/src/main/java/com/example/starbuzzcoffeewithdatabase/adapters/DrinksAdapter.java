package com.example.starbuzzcoffeewithdatabase.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbuzzcoffeewithdatabase.DrinkActivity;
import com.example.starbuzzcoffeewithdatabase.DrinkEntity;
import com.example.starbuzzcoffeewithdatabase.R;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.DrinksViewHolder> {
    private final Context context;
    private final DrinkEntity[] drinkOptions;
    public static final String SELECTED_DRINK_ID = "SELECTED_DRINK_ID";

    public DrinksAdapter(Context context, DrinkEntity[] drinkOptions) {
        this.context = context;
        this.drinkOptions = drinkOptions;
    }

    @NonNull
    @Override
    public DrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.drinks_item, parent, false);
        DrinksViewHolder drinksViewHolder = new DrinksViewHolder(itemView);
        itemView.setOnClickListener(drinksViewHolder.new DrinksVHOnClickListener());
        return drinksViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksViewHolder holder, int position) {
        holder.tvDrinkItem.setText(drinkOptions[position].getName());
        holder._id = drinkOptions[position].getId();
    }

    @Override
    public int getItemCount() {
        return drinkOptions.length;
    }

    public class DrinksViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDrinkItem;
        private int _id;

        public DrinksViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDrinkItem = itemView.findViewById(R.id.tvDrinksItem);
        }

        class DrinksVHOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DrinkActivity.class);
                intent.putExtra(SELECTED_DRINK_ID, _id);
                context.startActivity(intent);
            }
        }

    }
}
