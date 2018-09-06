package com.example.d.test.feature;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String[] data={"a","b","c","d","e","f","g"};
    public static Map<String, String> sshUrlMap = new HashMap<>();
    public static LinkedList<NewsItem> arrayList = new LinkedList<>();
    public static Map<String, NewsItem> arrayMap = new HashMap<>();
    public static Map<String, Boolean> isRead = new HashMap<>();
    public static Map<String, Boolean> isSaved = new HashMap<>();

    //only for testing, not memory
    private static List<NewsItem> newsItemList=new ArrayList<>();

    //this is in memory
    private static List<NewsItem> cachedNewsItemList=new ArrayList<>();
    static LinkedList<NewsItem> newsItems;
    private ReadRss readRss;
    private TabLayout tabLayout;
    private static String selectedChannel = "首页";
    private static final ArrayList<NewsItem> newsItemOfCategory = new ArrayList<>();

    public static List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

//    private static MyDatabaseHelper myDatabaseHelper;


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView mRecyclerView;
    private WebView webView;

    private static SwipeRefreshLayout swipeRefreshLayout;
    //for testing recyclerview
    private static int newsPointer=5;

    private static final int listSize=20;

    public static LinkedList<NewsItem> getNewsItems() {
        return newsItems;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        sshUrlMap.put("http://www.people.com.cn/rss/politics.xml", "国内新闻");
        sshUrlMap.put("http://www.people.com.cn/rss/world.xml", "国际新闻");
        sshUrlMap.put("http://www.people.com.cn/rss/finance.xml", "经济新闻");
        sshUrlMap.put("http://www.people.com.cn/rss/sports.xml", "体育新闻");
        sshUrlMap.put("http://www.people.com.cn/rss/haixia.xml", "台湾新闻");
        sshUrlMap.put("http://www.people.com.cn/rss/edu.xml", "教育新闻");
//        sshUrlMap.put("http://www.people.com.cn/rss/bbs.xml", "强国论坛");
        sshUrlMap.put("http://www.people.com.cn/rss/game.xml", "游戏新闻");
        sshUrlMap.put("http://www.people.com.cn/rss/opml.xml", "中文新闻");

        //toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        readRss = new ReadRss(this, mRecyclerView);
        readRss.execute();
//        myDatabaseHelper=new MyDatabaseHelper(this,"BookStore.db",null,1);
//        initNews();//mRecyclerView.setAdapter(adapter);


        navigationView=findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });

        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        //借鉴<android第一行代码>第11章

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
//        swipeRefreshLayout.setRefreshing(true);


        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("首页");
        tabLayout.addTab(firstTab);
        for (String string : sshUrlMap.values()) {
            TabLayout.Tab tempTab = tabLayout.newTab();
            tempTab.setText(string);
            tabLayout.addTab(tempTab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedChannel = tab.getText().toString();
                if(!swipeRefreshLayout.isRefreshing()){
                    changeTab();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

    }
    private final static int MAX_NEWS_ITEM_SIZE = 25;
    public void changeTab(){
//                Toast.makeText(MainActivity.this,selectedChannel,Toast.LENGTH_SHORT).show();
        if(!selectedChannel.equals("首页")) {
            newsItemOfCategory.clear();
            for(NewsItem newsItem: getNewsItems()){
                if(newsItem.getChannel().contains(selectedChannel)){
                    newsItemOfCategory.add(newsItem);
                    if(newsItemOfCategory.size()> MAX_NEWS_ITEM_SIZE) break;
                }
            }
            FeedsAdapter adapter = new FeedsAdapter(newsItemOfCategory, MainActivity.this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mRecyclerView.addItemDecoration(new VerticalSpace(20));
            mRecyclerView.setAdapter(adapter);
        } else {
            FeedsAdapter adapter = new FeedsAdapter(getNewsItems(), MainActivity.this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mRecyclerView.addItemDecoration(new VerticalSpace(20));
            mRecyclerView.setAdapter(adapter);
        }
    }

    public static String getSelectedChannel() {
        return selectedChannel;
    }

    public static ArrayList<NewsItem> getNewsItemOfCategory() {
        return newsItemOfCategory;
    }

    public static SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    private void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        readRss = new ReadRss(MainActivity.this, mRecyclerView);
                        readRss.execute();
                    }
                });
            }
        }).start();
    }

    /*private void initNews() {
        SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                String text = "This is content" + data[j] + i;
                NewsItem newsItem = new NewsItem(data[j] + i, text, R.drawable.newspic1);
                newsItemList.add(newsItem);

                //database
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", data[j] + i);
                contentValues.put("contents", text);
                contentValues.put("src", "0");
                database.insert("Book", null, contentValues);
            }
        }
        loadNews();
    }*/
   /* private void loadNews(){
        cachedNewsItemList = new ArrayList<>();
        for(int i=newsPointer;i<newsPointer + listSize;++i) {
            //only get title.find contents by itself.
            String newsTitle = newsItemList.get(i).getTitle();

            NewsItem newsItem = getNewsItemByTitle(newsTitle);
            if(newsItem != null){
                cachedNewsItemList.add(newsItem);
            } else {//TODO:download new newsItems
                System.out.println("SQL error"+newsItemList.get(i).getText());
                cachedNewsItemList.add(newsItemList.get(i));
            }

        }
        Log.d(TAG, "loadNews: "+cachedNewsItemList.size());
        RecycledNewsAdapter adapter=new RecycledNewsAdapter(MainActivity.this, cachedNewsItemList);
        mRecyclerView.setAdapter(adapter);
    }*/
    /*static NewsItem getNewsItemByTitle(String newsTitle){
        SQLiteDatabase database=myDatabaseHelper.getWritableDatabase();
//        Cursor cursor=database.query("Book",null,null,null,null,null,null);
        Cursor cursor;
        cursor = database.query("Book", new String[] { "id",
                        "title","contents","src" }, "title=?",
                new String[] { newsTitle }, null, null, null, null);
        if (cursor.moveToFirst()){//cached
            NewsItem newsItem = new NewsItem(newsTitle,cursor.getString(2),Integer.parseInt(cursor.getString(3)));
            cursor.close();
            return newsItem;
        } else {//not cached
            cursor.close();
            return null;
        }
    }*/

    public void onekeyShare(View view) {
        showShare();
    }

    public void goThirdPartyLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
