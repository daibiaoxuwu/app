package com.example.d.test.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        Intent intent=getIntent();
        String newsTitle=intent.getStringExtra("NewsTitle");

        NewsItem newsItem=MainActivity.getNewsItemByTitle(newsTitle);


        TextView textView=findViewById(R.id.titleView);
        textView.setText(newsItem.getTitle());
        textView= findViewById(R.id.innerTextView);
        textView.setText(newsItem.getText());
    }
}
