package com.example.starbuzzcoffee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbuzzcoffee.R;

public class OptionsListAdapter extends RecyclerView.Adapter<OptionsListAdapter.OptionsListViewHolder> {

    private final Context context;
    private final String[] optionsList;

    public OptionsListAdapter(Context context, String[] optionsList) {
        this.context = context;
        this.optionsList = optionsList;
    }

    @NonNull
    @Override
    public OptionsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.options_item, parent, false);
        return new OptionsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsListViewHolder holder, int position) {
        holder.tvOption.setText(optionsList[position]);
    }

    @Override
    public int getItemCount() {
        return optionsList.length;
    }

    public class OptionsListViewHolder extends RecyclerView.ViewHolder {
        TextView tvOption;

        public OptionsListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOption = itemView.findViewById(R.id.tvOptionItem);
        }
    }
}
