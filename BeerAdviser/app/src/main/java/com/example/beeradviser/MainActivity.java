package com.example.beeradviser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class BeerExpert
{
    List<String> getBrands(String beerType) {
        ArrayList<String> brands = new ArrayList<>();
        switch (beerType) {
            case "Ale":
                brands.add("India Pale Ale");
                brands.add("My Ale");
                break;
            case "Lager":
                brands.add("Mick Lager");
                break;
            case "Pilsner":
                brands.add("Letra A");
                brands.add("The Pilsner");
                break;
            case "Stout":
                brands.add("Super Bock Stout");
                brands.add("Letra S");
                brands.add("Stout Man");
                break;
            default:
                brands.add("Beer type not found...");
        }
        return brands;
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findBeerBrands(View view) {
        Spinner beerTypes = findViewById(R.id.beerTypeSpinnerId);
        BeerExpert beerExpert = new BeerExpert();
        String beerType = beerTypes.getSelectedItem().toString();
        List<String> brandsList = beerExpert.getBrands(beerType);
        TextView brands = findViewById(R.id.brandsId);
        StringBuilder brandsListJoin = new StringBuilder();
        for (String s : brandsList) {
            brandsListJoin.append(s).append("\n");
        }
        brands.setText(brandsListJoin);
    }
}
