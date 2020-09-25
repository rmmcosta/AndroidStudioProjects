package com.example.droidcafe_withoriginalbasicactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    View.OnClickListener onImageClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(MainActivity.LOG, "clicked item");
            View parent = (View) view.getParent();
            Log.d(MainActivity.LOG, "parent");
            ImageView imageView = parent.findViewById(view.getId());
            Log.d(MainActivity.LOG, "image view");
            String item = (String) imageView.getContentDescription();
            Log.d(MainActivity.LOG, "item: " + item);
            MainActivity.addToCart(item);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.ivDonut).setOnClickListener(onImageClick);
        view.findViewById(R.id.ivFroyo).setOnClickListener(onImageClick);
        view.findViewById(R.id.ivIceCream).setOnClickListener(onImageClick);
    }
}