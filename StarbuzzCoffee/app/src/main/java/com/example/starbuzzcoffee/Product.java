package com.example.starbuzzcoffee;

import java.io.Serializable;

public class Product implements Serializable {
    private int drawable;
    private int resourceDescription;

    public int getDrawable() {
        return drawable;
    }

    public int getResourceDescription() {
        return resourceDescription;
    }

    public Product(int drawable, int resourceDescription) {
        this.drawable = drawable;
        this.resourceDescription = resourceDescription;
    }
}
