package com.developer.sqlitedatabasesimplewritedemo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
    private List<Simple> simpleList;
    private View.OnClickListener onClickListener;

    public SimpleAdapter(List<Simple> simpleList, View.OnClickListener onClickListener) {
        this.simpleList = simpleList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_detail, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        Simple simple = simpleList.get(position);
        holder.tvId.setText(String.valueOf(simple.getId()));
        holder.tvName.setText(simple.getName());
        holder.tvDescription.setText(simple.getDescription());
        holder.tvTimestamp.setText(simple.getTimestamp());
        if (simple.isFavorite()) {
            holder.tvName.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount() {
        return simpleList.size();
    }

    public void addSimple(Simple simple) {
        simpleList.add(simple);
        notifyDataSetChanged();
    }

    public void updateSimpleFavorite(int id, boolean newFavorite) {
        int position = -1;
        for (int i = 0; i < simpleList.size(); i++) {
            if (simpleList.get(i).getId() == id) {
                position = i;
                break;
            }
        }
        Simple simple = simpleList.get(position);
        simple.setFavorite(newFavorite);
        simpleList.remove(position);
        simpleList.add(position, simple);
        notifyItemChanged(position);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvTimestamp;

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(onClickListener);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        }
    }
}
