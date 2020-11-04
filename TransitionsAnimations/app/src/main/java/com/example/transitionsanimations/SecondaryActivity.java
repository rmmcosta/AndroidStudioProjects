package com.example.transitionsanimations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

public class SecondaryActivity extends AppCompatActivity {
    private ImageView ivAndroid, ivCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        ivAndroid = findViewById(R.id.ivAndroid);
        ivCircle = findViewById(R.id.ivCircle);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        Pair<View, String> animatedAndroid = Pair.create(ivAndroid, "swap");
        Pair<View, String> animatedCircle = Pair.create(ivCircle, "swap");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, animatedAndroid, animatedCircle);
        startActivity(intent, activityOptions.toBundle());
    }
}