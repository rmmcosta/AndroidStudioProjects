package com.example.starbuzzcoffeewithdatabase;

public class DrinkEntity {
    private final String name;
    private final String description;
    private final int drawableId;

    public DrinkEntity(String name, String description, int drawableId) {
        this.name = name;
        this.description = description;
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDrawableId() {
        return drawableId;
    }
}
