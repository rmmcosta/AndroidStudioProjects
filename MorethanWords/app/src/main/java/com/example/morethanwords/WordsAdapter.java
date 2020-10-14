package com.example.morethanwords;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsViewHolder> {

    private List<String> mWords;
    private Context mContext;

    public WordsAdapter(List<String> words, Context context) {
        this.mWords = words;
        this.mContext = context;
    }

    @NonNull
    @Override
    public WordsAdapter.WordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.activity_words_view, parent, false);
        return new WordsViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsViewHolder holder, int position) {
        holder.mTvWord.setText(mWords.get(position));
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public class WordsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvWord;
        private WordsAdapter mWordsAdapter;

        public WordsViewHolder(@NonNull View itemView, WordsAdapter wordsAdapter) {
            super(itemView);
            mTvWord = itemView.findViewById(R.id.tvWord);
            this.mWordsAdapter = wordsAdapter;
            mTvWord.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Log.d("MyLog", String.valueOf(mPosition));
            String newWord = "Clicked " + mWords.get(mPosition);
            mWords.set(mPosition, newWord);
            mWordsAdapter.notifyItemChanged(mPosition);
        }
    }
}
