package com.example.d.test.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class SetTabActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private final ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tab);
        for(String string : MainActivity.sshUrlMap.values()){
            datas.add(string);
        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter mAdapter = new MyAdapter(datas);
        mRecyclerView.setAdapter(mAdapter);
    }
}
