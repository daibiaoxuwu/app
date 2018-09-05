package com.example.d.test.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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

        WebSettings settings = webView.getSettings();
        settings.setUserAgentString("app/XXX");//添加UA,  “app/XXX”：是与h5商量好的标识，h5确认UA为app/XXX就认为该请求的终端为App
        settings.setJavaScriptEnabled(true);

        //设置参数
        settings.setBuiltInZoomControls(true);
        settings.setAppCacheEnabled(true);// 设置缓存

        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(new WebViewClient());

//        webView.loadUrl(loadurl);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setUserAgentString(ua+"; 自定义标记");
//        webView.setWebViewClient(new WebViewClient());
//        Toast.makeText(NewsView.this,url,Toast.LENGTH_SHORT).show();
        webView.loadUrl(url);
    }
}
