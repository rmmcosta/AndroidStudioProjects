package com.example.droidcafe_withoriginalbasicactivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecondFragment extends Fragment {

    private RecyclerView rvCart;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    private void showCartItems(Context context) {
        Set<Map.Entry<String, Integer>> cartItems = MainActivity.getCartItems();
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(cartItems);
        rvCart.setAdapter(cartItemsAdapter);
        rvCart.setLayoutManager(new LinearLayoutManager(context));
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

        rvCart = view.findViewById(R.id.rvCart);
        showCartItems(getContext());
    }
}