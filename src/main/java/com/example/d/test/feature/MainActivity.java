package com.example.d.test.feature;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String[] data={"a","b","c","d","e","f","g"};

    //only for testing, not memory
    private static List<NewsItem> newsItemList=new ArrayList<>();

    //this is in memory
    private static List<NewsItem> cachedNewsItemList=new ArrayList<>();
    private ReadRss readRss;

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

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
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
            Toast.makeText(this, "drawer", Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);


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

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
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
}
