package com.developer.sqlitedatabasesimplewritedemo;

public class Simple {
    private final long id;
    private final String name;

    private String description;

    private final String timestamp;
    private boolean favorite;
    public Simple(long id, String name, String description, String timestamp, boolean favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
