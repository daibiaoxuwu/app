package com.example.d.test.feature;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;


import java.net.URL;

/**
 * Created by rishabh on 31-01-2016.
 */
public class ReadRss extends AsyncTask<Void, Void, Void> {
    MainActivity context;
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    URL url;
    private NewsParser newsParser;
    private boolean firstrun = true;
    private String oldSelectedChannel;

    public ReadRss(MainActivity context, RecyclerView recyclerView) {
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
        context.changeTab();

//        FeedsAdapter adapter = new FeedsAdapter(newsItems, context);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.addItemDecoration(new VerticalSpace(20));
//        recyclerView.setAdapter(adapter);
        MainActivity.getSwipeRefreshLayout().setRefreshing(false);
    }

    public void ProcessXml() {
        try {
            MainActivity.newsItems = newsParser.parse();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //This method will download rss feed document from specified url
    private static final String TAG = "ReadRss";
}

