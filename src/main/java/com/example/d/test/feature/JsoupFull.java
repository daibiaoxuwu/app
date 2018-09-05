package com.example.d.test.feature;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;

        import java.io.InputStream;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.Random;



public class JsoupFull {
    static ArrayList<NewsItem> arrayList = new ArrayList<>();
    static Map<String, NewsItem> arrayMap = new HashMap<>();


    static void readDoc3(Document doc, String channel) {
        for (Element element : doc.select("item")) {
            NewsItem newsItem = new NewsItem();
            String title = element.getElementsByTag("title").text();
            if(arrayMap.containsKey(title)){
                arrayMap.get(title).addChannel(channel);
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

                arrayList.add(newsItem);
                arrayMap.put(newsItem.getTitle(),newsItem);
            }
//            if(Math.random()>0.9){
//                System.out.println(newsItem);
//            }
        }
    }
}
