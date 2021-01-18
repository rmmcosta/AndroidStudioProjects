package com.example.starbuzzcoffeewithdatabase;

public class DrinkEntity {
    private final String name;
    private final int descriptionId;
    private final int drawableId;
    private final int id;
    private final boolean favorite;

    public DrinkEntity(int id, String name, int descriptionId, int drawableId, boolean favorite) {
        this.id = id;
        this.name = name;
        this.descriptionId = descriptionId;
        this.drawableId = drawableId;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
