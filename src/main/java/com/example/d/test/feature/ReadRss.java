package com.example.d.test.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rishabh on 31-01-2016.
 */
public class ReadRss extends AsyncTask<Void, Void, Void> {
    Context context;
    String address = "http://news.qq.com/society_index.shtml";
    ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    URL url;

    public ReadRss(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    //before fetching of rss statrs show progress to user
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }


    //This method will execute in background so in this method download rss feeds
    @Override
    protected Void doInBackground(Void... params) {
        //call process xml method to process document we downloaded from getData() method
        ProcessXml(Getdata());

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        FeedsAdapter adapter = new FeedsAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(20));
        recyclerView.setAdapter(adapter);

    }
    public void ProcessXml(Document doc) {
        try {
            feedItems = new ArrayList<>();
            if(doc == null) return;
            Elements newsHeadlines = doc.select(".Q-tpList");
            Pattern pattern = Pattern.compile("src=\"([^\"]+)\"");
            for (Element headline : newsHeadlines) {
                FeedItem item = new FeedItem();
                Elements elements = headline.select(".Q-tpWrap .text .linkto");
                if (elements == null) continue;
                if (elements.first() == null) continue;
                item.setTitle(elements.first().text());
                item.setUrl(new URL(elements.first().absUrl("abs:href")));

                elements = headline.select(".Q-tpWrap .pic img");
                if (elements == null) continue;
                if (elements.first() == null) continue;
                String string = elements.first().toString();
                Matcher matcher = pattern.matcher(string);
                if (matcher.find()){
                    String s=matcher.group(1);
                    if(s.charAt(0)!='h') s="http:"+s;
                    URL url = new URL(s);
                    item.getPics().add(url);
                }
                else
                    throw (new Exception());
                feedItems.add(item);
            }

            newsHeadlines = doc.select(".Q-pList");
            Pattern pattern2 = Pattern.compile("href=\"([^\"]+)\"");
            for (Element headline : newsHeadlines) {
                FeedItem item = new FeedItem();
                Element element = headline.getElementsByClass("content").first();
                if (element == null) continue;
                item.setTitle(element.text());
//                item.setUrl(new URL(elements.first().absUrl("abs:href")));
                String string = element.toString();
                Matcher matcher = pattern2.matcher(string);
                if (matcher.find()){
                    String s=matcher.group(1);
                    if(s.charAt(0)!='h') s="http:"+s;
                    URL url = new URL(s);
                    item.setUrl(url);
                }
                else
                    throw (new Exception());
                Elements elements = element.getElementsByTag("ul").first().getElementsByTag("li");
                for (Element element1 : elements) {
                    string = element1.getElementsByTag("a").first().getElementsByTag("img").first().toString();
                    matcher = pattern.matcher(string);
                    if (matcher.find()){
                        String s=matcher.group(1);
                        if(s.charAt(0)!='h') s="http:"+s;
                        URL url = new URL(s);
                        item.getPics().add(url);
                    }
                    else
                        throw (new Exception());
                }
                feedItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
/*
    // In this method we will process Rss feed  document we downloaded to parse useful information from it
        if (data != null) {
            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = cureentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node cureent = itemchilds.item(j);
                        if (cureent.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                            //this will return us thumbnail url
                            String url = cureent.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);
                        }
                    }
                    feedItems.add(item);


                }
            }
        }
    }*/

    //This method will download rss feed document from specified url
    private static final String TAG = "ReadRss";
    public Document Getdata() {
        try {
//            url = new URL(address);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            InputStream inputStream = connection.getInputStream();
//            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = builderFactory.newDocumentBuilder();
//            Document xmlDoc = builder.parse(inputStream);
            Document doc = Jsoup.connect(address).userAgent("Mozilla").timeout(5000).get();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Getdata: ");
            return null;
        }
    }
}
