package com.example.simplerandomnumber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Random;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Integer count = SecondFragmentArgs.fromBundle(getArguments()).getCountArg();
        TextView headerText = view.findViewById(R.id.textview_header);
        String countText = getString(R.string.random_heading, count);
        headerText.setText(countText);

        Random r = new Random();
        TextView randomText = view.findViewById(R.id.textView_random);
        String randomNumberText = "0";
        if(count > 0)
            randomNumberText = String.valueOf(r.nextInt(count+1));
        randomText.setText(randomNumberText);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}