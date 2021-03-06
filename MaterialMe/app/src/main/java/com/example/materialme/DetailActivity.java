package com.example.materialme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.transition.CircularPropagation;
import android.transition.Explode;
import android.transition.SidePropagation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView tvSportsTitle, tvSportsInfo;
    private ImageView ivSportsBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvSportsTitle = findViewById(R.id.tvSportsTitle_Detail);
        ivSportsBanner = findViewById(R.id.ivSportsBanner_Detail);
        tvSportsInfo = findViewById(R.id.tvSportsInfo_Detail);
        loadData(getIntent());
    }

    private void loadData(Intent intent) {
        Bundle data = intent.getExtras();
        Sport sport = data.getParcelable(MainActivity.SELECTED_SPORT_ITEM);
        tvSportsTitle.setText(sport.getSportsTitle());
        tvSportsInfo.setText(sport.getSportsInfo());
        Glide.with(this).load(sport.getBanner()).into(ivSportsBanner);
    }
}