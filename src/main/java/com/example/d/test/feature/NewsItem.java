package com.example.d.test.feature;

public class NewsItem {
    private String title;
    private String text;
    private int imageId;

    public NewsItem(String title, String text, int imageId) {
        this.title = title;
        this.text = text;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
