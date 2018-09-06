package com.example.d.test.feature;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class NewsView extends AppCompatActivity {

    private static final String TAG = "NewsView";
    private WebView webView;

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.toolbar2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_backup) {
            Toast.makeText(this, "backup", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.menu_delete) {
            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.menu_settings) {
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        } else if (i == android.R.id.home) {
            finish();//结束activity
        }
        return true;
    }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack(); return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        Intent intent=getIntent();
        String url = intent.getStringExtra("Url");
//        Log.d(TAG, "onCreate: " + url);
        //toolbar
//        setSupportActionBar(toolbar);


        webView = findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + " APP_TAG/5.0.1");
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setAppCacheEnabled(true);// 设置缓存
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);


        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        //以上关于webView的设置引用自:
        //作者：HolenZhou
        //链接：https://www.jianshu.com/p/979c99820f76
        //來源：简书
        //简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
    }
}
