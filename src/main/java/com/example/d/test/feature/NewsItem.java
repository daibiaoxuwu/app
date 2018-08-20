package com.example.d.test.feature;

public class NewsItem {
    private String title;
    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public NewsItem(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }
}
