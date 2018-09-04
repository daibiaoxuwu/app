package com.example.d.test.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class NewsView extends AppCompatActivity {

    private static final String TAG = "NewsView";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        Intent intent=getIntent();
        String url = intent.getStringExtra("Url");
//        Log.d(TAG, "onCreate: " + url);

        WebView webView=findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
//        Toast.makeText(NewsView.this,url,Toast.LENGTH_SHORT).show();
        webView.loadUrl(url);
    }
}
