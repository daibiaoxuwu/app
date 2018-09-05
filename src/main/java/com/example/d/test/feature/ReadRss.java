package com.example.d.test.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rishabh on 31-01-2016.
 */
public class ReadRss extends AsyncTask<Void, Void, Void> {
    Context context;
    ProgressDialog progressDialog;
    LinkedList<NewsItem> newsItems;
    RecyclerView recyclerView;
    URL url;
    private NewsParser newsParser;
    private boolean firstrun;

    public ReadRss(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.newsParser = new NewsParser();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    //before fetching of rss statrs show progress to user
    @Override
    protected void onPreExecute() {
        if(firstrun)
            progressDialog.show();
        super.onPreExecute();
    }


    //This method will execute in background so in this method download rss feeds
    @Override
    protected Void doInBackground(Void... params) {
        //call process xml method to process document we downloaded from getData() method
        ProcessXml();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(firstrun) {
            progressDialog.dismiss();
            firstrun = false;
        }
        FeedsAdapter adapter = new FeedsAdapter(context, newsItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(20));
        recyclerView.setAdapter(adapter);
        MainActivity.getSwipeRefreshLayout().setRefreshing(false);
    }

    public void ProcessXml() {
        try {
            newsItems = newsParser.parse();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //This method will download rss feed document from specified url
    private static final String TAG = "ReadRss";
}

