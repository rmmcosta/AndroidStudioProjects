package com.developer.roomwordssample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WordsFragment extends Fragment {

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
        WordViewModel wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(getActivity(), words -> {
            RecyclerView rvWords = view.findViewById(R.id.rvWords);
            WordsAdapter wordsAdapter = new WordsAdapter(getContext());
            wordsAdapter.setWords(words);
            rvWords.setAdapter(wordsAdapter);
            rvWords.setLayoutManager(new LinearLayoutManager(view.getContext()));
        });
    }

}