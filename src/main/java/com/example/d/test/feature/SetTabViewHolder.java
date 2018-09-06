package com.example.d.test.feature;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SetTabViewHolder extends RecyclerView.ViewHolder{
    private static final String TAG="SetTabViewHolder";
    TextView textView;
    View tabView;
    public SetTabViewHolder(View view){
        super(view);
        tabView = view;
        textView = view.findViewById(R.id.set_tab_text);
        Log.d(TAG, "SetTabViewHolder: "+textView);
    }
}
