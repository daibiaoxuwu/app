package com.example.d.test.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;

public class SetTabActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    public static final LinkedList<String> datas = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tab);
        datas.addAll(MainActivity.sshUrlMap.values());
        mRecyclerView = findViewById(R.id.set_tab_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SetTabAdapter mAdapter = new SetTabAdapter(datas,SetTabActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
