package com.developer.roomwordssample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

        RecyclerView rvWords = view.findViewById(R.id.rvWords);

        assert (getActivity() != null);
        WordViewModel wordViewModel = new WordViewModel(getActivity().getApplication());
        List<Word> wordList = wordViewModel.getAllWords().getValue();
        
        rvWords.setAdapter(new WordsAdapter(getContext()));
        rvWords.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}