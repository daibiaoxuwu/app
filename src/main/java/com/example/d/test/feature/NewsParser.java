package com.example.d.test.feature;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedList;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;

public class NewsParser {
    private Connection c;
    private Statement stmt;

    private void work(String url, String channel) {
        System.out.println("URL:"+url + " CHANNEL:"+channel + " ");
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com").get();
            readDoc3(doc, channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    NewsParser() {

        c = null;
        stmt = null;
    }
    public LinkedList<NewsItem> parse(){
        try {
//            while(true) {
//            arrayList = new LinkedList<>();
//            arrayMap = new HashMap<>();
            for (String string : MainActivity.sshUrlMap.keySet()) {
                work(string, MainActivity.sshUrlMap.get(string));
            }
                /*
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection(Xmldb.databaseUrl);
                c.setAutoCommit(false);
//                System.out.println("Opened database successfully");
                try{
                    stmt = c.createStatement();
                    int add = 0, tot = arrayList.size();
                    for (NewsItem newsItem : arrayList) {
//                if(newsItem.getTitle().contains("布尔加斯国际民俗节"))
//                    System.out.println(newsItem);
                        try {
                            String sql = "INSERT INTO NEWS (TITLE,LINK,AUTHOR,PUBDATE,CATEGORY,COMMENTS,DESCRIPTION, CHANNEL, PICS)" +
                                    newsItem.dbString();
                            stmt.executeUpdate(sql);
                            add++;
//                    System.out.println("ADD: " + newsItem.getTitle());
                        } catch (org.sqlite.SQLiteException e) {
//                    System.out.println("Exc: " + newsItem);
//                    e.printStackTrace();
                        }
                    }
                    System.out.println("added: "+add+" of: "+tot+ " time:"+new Date().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

                c.commit();
                c.close();
                Thread.sleep(1000 * 60 * 60);
                */
//            }
            return MainActivity.arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return MainActivity.arrayList;
        }
    }

//    private static final String TAG = "NewsParser";
    private void readDoc3(Document doc, String channel) {
        Elements elements = doc.select("item");
        for (int i = elements.size() - 1; i >= 0; i--) {
            Element element = elements.get(i);
            NewsItem newsItem = new NewsItem();
            String title = element.getElementsByTag("title").text();
            if(MainActivity.arrayMap.containsKey(title)){
                MainActivity.arrayMap.get(title).addChannel(channel);
//                Log.d(TAG, "readDoc3: "+arrayMap.get(title));
            } else{
                newsItem.setTitle(title);
                newsItem.setLink(element.getElementsByTag("link").text());
                newsItem.setAuthor(element.getElementsByTag("author").text());
                newsItem.setPubdate(element.getElementsByTag("pubdate").text());
                newsItem.setCategory(element.getElementsByTag("category").text());
                newsItem.setComments(element.getElementsByTag("comments").text());
                newsItem.setChannel(channel);
                String description = element.getElementsByTag("description").text();
                Document document = Jsoup.parse(description);

                newsItem.setDescription(document.text());
                Elements imgs = document.getElementsByTag("img");
                for (Element img : imgs) {
                    String src=img.attr("src");
                    if(!src.startsWith("http")){
                        src = "http://www.people.com.cn" + src;
                    }
                    try {
                        new URL(src);
                    } catch (MalformedURLException e){
                        System.out.println("error: pic url malformed: " + newsItem);
                        System.out.println(src);
                    }
                    newsItem.setPics(src);
                }

                MainActivity.arrayList.addFirst(newsItem);
                MainActivity.arrayMap.put(newsItem.getTitle(),newsItem);
                MainActivity.isRead.put(newsItem.getTitle(), false);
                MainActivity.isSaved.put(newsItem.getTitle(), false);
            }
        }
    }
}
