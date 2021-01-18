package com.developer.sqlitedatabasesimplewritedemo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder> {

    public MyListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        Simple simple = Simple.fromCursor(cursor);
        holder.tvId.setText(String.valueOf(simple.getId()));
        holder.tvName.setText(simple.getName());
        holder.tvDescription.setText(simple.getDescription());
        holder.tvTimestamp.setText(simple.getTimestamp());
        holder.tvFavorite.setText(simple.isFavorite() ? "true" : "false");
        if (simple.isFavorite()) {
            holder.tvName.setTextColor(Color.BLUE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId;
        private final TextView tvName;
        private final TextView tvDescription;
        private final TextView tvTimestamp;
        private final TextView tvFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvFavorite = itemView.findViewById(R.id.tvFavorite);
        }
    }
}
