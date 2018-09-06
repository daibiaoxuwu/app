package com.example.d.test.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsView extends AppCompatActivity {

    private static final String TAG = "NewsView";
    private WebView webView;
    private String url;
    private String title;
    private NewsItem newsItem;

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.toolbar2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_share) {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(newsItem.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("哇这个666啊\n"+newsItem.getTitle());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        if(newsItem.getPics().size()>0)
            oks.setImageUrl(newsItem.getPics().get(0));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("来自我的安卓大作业");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("people.com.cn");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(this);

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
        title = intent.getStringExtra("Title");
        newsItem = MainActivity.arrayMap.get(title);
        url = newsItem.getLink();
//        Log.d(TAG, "onCreate: " + url);
        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        webView = findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + " APP_TAG/5.0.1");
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setAppCacheEnabled(true);// 设置缓存
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);


        //以上关于webView的设置引用自:
        //作者：HolenZhou
        //链接：https://www.jianshu.com/p/979c99820f76
        //來源：简书
        //简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
    }
}
