package com.first.demo.models;

public class Article {
    private final String title;
    private final String link;
//    private final String imageUrl;
//    private final String date;
    private  String description;
    public Article(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description= description;
    }

    public String getTitle() { return title; }
    public String getLink() { return link; }
    public String getDescription() { return description; }
//    public String getDate() { return date; }
}
