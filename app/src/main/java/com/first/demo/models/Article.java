package com.first.demo.models;

public class Article {
    private final String title;
    private final String link;
    private final String imageUrl;
    private final String description;

    public Article(String title, String link, String description, String imageUrl) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
