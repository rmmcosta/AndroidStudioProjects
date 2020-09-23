package com.example.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void orderDonut(View view) {
        showToast(R.string.donut_order_message);
    }

    public void orderIceCream(View view) {
        showToast(R.string.ice_cream_order_message);
    }

    public void orderFroyo(View view) {
        showToast(R.string.froyo_order_message);
    }

    private void showToast(int orderMessageId) {
        Toast toast = Toast.makeText(getApplicationContext(), orderMessageId, Toast.LENGTH_SHORT);
        toast.show();
    }
}