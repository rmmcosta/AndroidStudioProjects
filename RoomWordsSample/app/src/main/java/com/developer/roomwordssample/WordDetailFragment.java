package com.developer.roomwordssample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class WordDetailFragment extends Fragment {

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
        Button saveButton = view.findViewById(R.id.btnSaveWord);
        saveButton.setOnClickListener(view1 -> addWord(view));
    }

    private void addWord(View mainView) {
        EditText etWord = mainView.findViewById(R.id.etWord);
        String word = String.valueOf(etWord.getText());
        /*WordEntity wordEntity = WordEntity.getInstance();
        wordEntity.addWord(new Word(word));*/
        //TODO logic to save words in the database
        showMessage(String.format("Word %s added.", word));
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}