package com.example.d.test.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;

public class SetTabActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private final LinkedList<String> datas = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tab);
        for(String string : MainActivity.sshUrlMap.values()){
            datas.add(string);
        }
        mRecyclerView = findViewById(R.id.set_tab_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SetTabAdapter mAdapter = new SetTabAdapter(datas);
        mRecyclerView.setAdapter(mAdapter);
    }
}
