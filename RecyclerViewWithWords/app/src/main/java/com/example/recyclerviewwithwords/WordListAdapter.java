package com.example.recyclerviewwithwords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LinkedList<String> mWordList;
    private LayoutInflater mLayoutInflater;

    public WordListAdapter(Context context, LinkedList<String> mWordList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mWordList = mWordList;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String currentWord = mWordList.get(position);
        holder.tvWordItem.setText(currentWord);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView tvWordItem;
        final WordListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapter adapter) {
            super(itemView);
            tvWordItem = itemView.findViewById(R.id.tvWordListItem);
            itemView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String word = mWordList.get(mPosition);
            mWordList.set(mPosition, "Clicked " + word);
            mAdapter.notifyDataSetChanged();
        }
    }
}
