package com.developer.roomwordssample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

public class WordDetailFragment extends Fragment {
    private WordViewModel wordViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_word_detail, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wordViewModel = WordsFragment.wordViewModel;
        Button saveButton = view.findViewById(R.id.btnSaveWord);
        saveButton.setOnClickListener(view1 -> addWord(view));
        Button deleteButton = view.findViewById(R.id.btnDeleteAll);
        deleteButton.setOnClickListener(view12 -> deleteAllWords());
    }

    private void deleteAllWords() {
        wordViewModel.deleteAll();
        showMessage("All Words have been deleted!");
        NavHostFragment.findNavController(WordDetailFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    private void addWord(View mainView) {
        EditText etWord = mainView.findViewById(R.id.etWord);
        if (getContext() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mainView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        String word = String.valueOf(etWord.getText());

        if (word.isEmpty()) {
            showMessage("You must specify a word!");
            return;
        }

        assert (getActivity() != null);
        if (wordViewModel.getWord(word) != null) {
            showMessage("The Word already exists in the database!");
            return;
        }
        wordViewModel.insert(new Word(word));
        showMessage(String.format("Word %s added.", word));
        NavHostFragment.findNavController(WordDetailFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}