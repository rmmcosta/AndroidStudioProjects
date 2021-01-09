package com.example.starbuzzcoffeewithdatabase;

import java.io.Serializable;

public class Product implements Serializable {
    private final String name;
    private final int drawable;
    private final int resourceDescription;

    public Product(String name, int drawable, int resourceDescription) {
        this.name = name;
        this.drawable = drawable;
        this.resourceDescription = resourceDescription;

    }

    public int getDrawable() {
        return drawable;
    }

    public int getResourceDescription() {
        return resourceDescription;
    }

    public String getName() {
        return name;
    }
}
