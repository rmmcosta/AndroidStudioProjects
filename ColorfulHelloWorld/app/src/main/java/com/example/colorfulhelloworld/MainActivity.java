package com.example.colorfulhelloworld;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    class MyColor{
        private int colorId;
        private String colorName;

        MyColor(int colorId, String colorName) {
            this.colorId = colorId;
            this.colorName = colorName;
        }
    }

    private TextView tvHello, tvColorName;
    private int previousColor;
    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = findViewById(R.id.tvHello);
        tvColorName = findViewById(R.id.tvColorName);
        tvHello.setTextColor(getColorId(R.color.grey));
        tvColorName.setText("grey");
    }

    public void changeHelloColor(View view) {
        MyColor myColor = getRandomColor();
        tvHello.setTextColor(myColor.colorId);
        tvColorName.setText(myColor.colorName);
    }

    private MyColor getRandomColor() {
        Random random = new Random();
        int rInt = previousColor;
        while (rInt == previousColor)
            rInt = random.nextInt(mColorArray.length);
        previousColor = rInt;
        Log.d("MyLog", String.valueOf(rInt));
        String rColor = mColorArray[rInt];
        int colorResourceName = getColorResourceNameId(rColor);
        int colorId=getColorId(colorResourceName);
        return new MyColor(colorId, rColor);
    }

    private int getColorResourceNameId(String rColor) {
        return getResources().getIdentifier(rColor, "color", getApplicationContext().getOpPackageName());
    }

    private int getColorId(int colorResourceName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(colorResourceName, getApplicationContext().getTheme());
        } else {
            return ContextCompat.getColor(this, colorResourceName);
        }
    }
}