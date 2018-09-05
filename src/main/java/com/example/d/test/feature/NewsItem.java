package com.example.d.test.feature;

import android.text.TextUtils;

import java.util.ArrayList;

public class NewsItem {
    private String title;
    private String link ;
    private String author ;
    private String pubdate ;
    private String category ;
    private String comments;
    private String description;
    private ArrayList<String> pics = new ArrayList<>();

    public void setPics(String pic) {
        this.pics.add(pic);
    }

    public ArrayList<String> getPics() {
        return pics;
    }


    public String getChannel() {
        return channel;
    }

    private     String channel;

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", category='" + category + '\'' +
                ", comments='" + comments + '\'' +
                ", description='" + description + '\'' +
                ", pics=" + pics +
                ", channel='" + channel + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
    public void addChannel(String channel) {
        this.channel += ";";
        this.channel += channel;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getCategory() {
        return category;
    }

    public String getComments() {
        return comments;
    }

    public String getDescription() {
        return description;
    }

    public String dbString(){
        return "VALUES ('" + title + "', '" + link + "', '"+ author + "', '"
                + pubdate + "', '"+ category + "', '"+ comments + "', '"
                + description+"', '"+channel + "', '" + TextUtils.join(";",pics) + "');";
    }
}
