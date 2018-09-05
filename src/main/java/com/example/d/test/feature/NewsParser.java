package com.example.d.test.feature;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewsParser {
    private static Connection c;
    private static Statement stmt;
    private static Map<String, String> map = new HashMap<>();
    public static void work(String url, String channel) {
        System.out.println("URL:"+url + " CHANNEL:"+channel + " ");
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com").get();
            JsoupFull.readDoc3(doc, channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NewsParser() {
        if(map.size() == 0){
        map.put("http://www.people.com.cn/rss/politics.xml","国内新闻");
        map.put("http://www.people.com.cn/rss/world.xml","国际新闻");
        map.put("http://www.people.com.cn/rss/finance.xml","经济新闻");
        map.put("http://www.people.com.cn/rss/sports.xml","体育新闻");
        map.put("http://www.people.com.cn/rss/haixia.xml","台湾新闻");
        map.put("http://www.people.com.cn/rss/edu.xml","教育新闻");
        map.put("http://www.people.com.cn/rss/bbs.xml","强国论坛");
        map.put("http://www.people.com.cn/rss/game.xml","游戏新闻");
        map.put("http://www.people.com.cn/rss/opml.xml","中文新闻");}
        c = null;
        stmt = null;
        try {
//            while(true) {
                JsoupFull.arrayList = new ArrayList<>();
                JsoupFull.arrayMap = new HashMap<>();
                for (String string : map.keySet()) {
                    work(string, map.get(string));
                }
                /*
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection(Xmldb.databaseUrl);
                c.setAutoCommit(false);
//                System.out.println("Opened database successfully");
                try{
                    stmt = c.createStatement();
                    int add = 0, tot = JsoupFull.arrayList.size();
                    for (NewsItem newsItem : JsoupFull.arrayList) {
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
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
