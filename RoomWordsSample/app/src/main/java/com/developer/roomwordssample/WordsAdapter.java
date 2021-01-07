package com.developer.roomwordssample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsViewHolder> {
    private List<Word> words;
    private final Context context;

    public WordsAdapter(Context context) {
        this.context = context;
    }

    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.word_layout, parent, false);
        return new WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsViewHolder holder, int position) {
        holder.tvWord.setText(words.get(position).toString());
    }

    @Override
    public int getItemCount() {
        if (words != null)
            return words.size();
        else
            return 0;
    }

    class WordsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWord;

        public WordsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
        }
    }

}
