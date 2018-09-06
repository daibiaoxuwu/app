package com.example.d.test.feature;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
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


        if(datas.size()==0)
            datas.addAll(MainActivity.sshUrlMap.values());
        mRecyclerView = findViewById(R.id.set_tab_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SetTabAdapter mAdapter = new SetTabAdapter(datas,SetTabActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.tabLayout.removeAllTabs();
        for (String string : MainActivity.setTabs) {
            TabLayout.Tab tempTab = MainActivity.tabLayout.newTab();
            tempTab.setText(string);
            MainActivity.tabLayout.addTab(tempTab);
            MainActivity.changeTab();
        }
    }
}
