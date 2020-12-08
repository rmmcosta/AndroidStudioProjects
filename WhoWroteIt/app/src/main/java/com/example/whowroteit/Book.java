package com.example.whowroteit;

public class Book {
    private String title;
    private String subtitle;
    private String authors;

    public Book(String title, String subtitle, String authors) {
        this.title = title.isEmpty() ? "Not found" : title;
        this.subtitle = subtitle.isEmpty() ? "N/A" : subtitle;
        this.authors = authors.isEmpty() ? "Unknown" : authors;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
