package com.example.d.test.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String[] data={"a","b","c","d","e","f","g"};

    private List<NewsItem> newsItemList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        initNews();

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecycledNewsAdapter adapter=new RecycledNewsAdapter(newsItemList);
        recyclerView.setAdapter(adapter);
    }

    private void initNews(){
        for (int i = 0; i < 7; i++) {
            for(int j = 0; j <  7; j++) {
                NewsItem newsItem=new NewsItem(data[j],R.drawable.newspic1);
                newsItemList.add(newsItem);
            }
        }
    }
}
