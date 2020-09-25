package com.example.droidcafe_withoriginalbasicactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Map;
import java.util.Set;

public class SecondFragment extends Fragment {

    private TextView tvCartItems;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    private void showCartItems() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, Integer>> cartItems = MainActivity.getCartItems();
        if (cartItems.isEmpty()) {
            tvCartItems.setText("No Items in the Cart.");
        }
        for (Map.Entry<String, Integer> item : cartItems) {
            stringBuilder.append(item.getKey() + " - " + item.getValue() + "\n");
        }
        tvCartItems.setText(stringBuilder.toString());
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(MainActivity.LOG, "created second fragment");
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        tvCartItems = view.findViewById(R.id.tvCartItems);
        showCartItems();
    }
}