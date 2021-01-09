package com.developer.roomwordssample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WordsFragment extends Fragment {
    public static WordViewModel wordViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(viewFab -> NavHostFragment.findNavController(WordsFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));

        assert (getActivity() != null);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(getActivity(), words -> {
            RecyclerView rvWords = view.findViewById(R.id.rvWords);
            WordsAdapter wordsAdapter = new WordsAdapter(getContext());
            wordsAdapter.setWords(words);
            rvWords.setAdapter(wordsAdapter);
            rvWords.setLayoutManager(new LinearLayoutManager(view.getContext()));
            ItemTouchHelper.SimpleCallback simpleCallbackSwipeRight = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    wordViewModel.deleteWord(wordsAdapter.getWord(position));
                    wordsAdapter.notifyItemRemoved(position);
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallbackSwipeRight);
            itemTouchHelper.attachToRecyclerView(rvWords);
        });
    }

}