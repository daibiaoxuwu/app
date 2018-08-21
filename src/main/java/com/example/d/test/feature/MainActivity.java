package com.example.d.test.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] data={"a","b","c","d","e","f","g"};

    private static List<NewsItem> newsItemList=new ArrayList<>();

    public static List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

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

        initNews();

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecycledNewsAdapter adapter=new RecycledNewsAdapter(MainActivity.this, newsItemList);
        recyclerView.setAdapter(adapter);
    }

    private void initNews(){
        for (int i = 0; i < 7; i++) {
            for(int j = 0; j <  7; j++) {
                String text="This is content"+data[j];
                NewsItem newsItem=new NewsItem(data[j],text,R.drawable.newspic1);
                newsItemList.add(newsItem);
            }
        }
    }
}
