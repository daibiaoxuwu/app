package com.example.d.test.feature;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

class SetTabAdapter extends MyBaseAdapter<String>{
//    LinkedList<String> datas;
    Context context;
    public SetTabAdapter(LinkedList<String> datas, Context context) {
        super(datas);
//        this.datas=datas;
        this.context=context;
    }


    private static final String TAG = "SetTabAdapter";
    @Override
    public SetTabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.simple_item_layout, parent, false);
        Log.d(TAG, "onCreateViewHolder: "+view);
        final SetTabViewHolder holder = new SetTabViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: "+holder);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position<0) {
                    Log.e(TAG, "onClick: position: " + position);
                    Log.e(TAG, "onClick: position" + holder.getLayoutPosition() );
                    Log.e(TAG, "onClick: position" + holder);
                }
                else {
                    String string = SetTabActivity.datas.get(position);
//                    if (MainActivity.setTabs.contains(string)) {
//                        MainActivity.setTabs.remove(position);
//                        holder.textView.setTextColor(0xFF9E9E9E);
//                    } else {
//                        MainActivity.setTabs.add(string);
//                        holder.textView.setTextColor(0xFF000000);
//                    }
                }
            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder0, int position) {
        SetTabViewHolder holder = (SetTabViewHolder) holder0;
        String string = SetTabActivity.datas.get(position);
        holder.textView.setText(string);
//        if(!MainActivity.setTabs.contains(string)){
//            holder.textView.setTextColor(0xFF9E9E9E);
//        } else {
//            holder.textView.setTextColor(0xFF000000);
//        }
    }

    @Override
    public int getItemCount() {
//        return SetTabActivity.datas.size();
        return 4;
    }


    @Override
    public boolean areItemsTheSame(String oldItem, String newItem) {
        return (oldItem.equals(newItem));
    }
    @Override
    public boolean areContentsTheSame(String oldItem, String newItem) {
        return (oldItem.equals(newItem));
    }
}
