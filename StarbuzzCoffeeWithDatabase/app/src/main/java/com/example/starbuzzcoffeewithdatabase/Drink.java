package com.example.starbuzzcoffeewithdatabase;

public class Drink extends Product {
    private static final Drink[] drinks = new Drink[]{
            new Drink("Latte", R.drawable.latte, R.string.latte_description),
            new Drink("Cappuccino", R.drawable.cappuccino, R.string.cappuccino_description),
            new Drink("Filter", R.drawable.filter, R.string.filter_description)
    };

    public Drink(String name, int drawable, int resourceDescription) {
        super(name, drawable, resourceDescription);
    }

    public static Drink[] getDrinks() {
        return drinks;
    }

    public static Drink getSelectedDrink(String drink) {
        for (int i = 0; i < drinks.length; i++) {
            if (drinks[i].getName().equalsIgnoreCase(drink)) {
                return drinks[i];
            }
        }
        return null;
    }
}
