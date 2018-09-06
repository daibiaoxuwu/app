package com.example.d.test.feature;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

class SetTabAdapter extends RecyclerView.Adapter<SetTabAdapter.ViewHolder> {
    LinkedList<String> datas;
    Context context;
    public SetTabAdapter(LinkedList<String> datas, Context context) {
        this.datas=datas;
        this.context=context;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.set_tab_text);
            Log.d(TAG, "ViewHolder: "+textView);
        }
    }

    private static final String TAG = "SetTabAdapter";
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_item_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position<0)
                    Log.d(TAG, "onClick: position: "+position);
                String string = datas.get(position);
                if(MainActivity.setTabs.contains(string)){
                    MainActivity.setTabs.remove(position);
                    holder.textView.setTextColor(0xFF9E9E9E);
                } else {
                    MainActivity.setTabs.add(string);
                    holder.textView.setTextColor(0xFF000000);
                }
            }
        });




        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String string = datas.get(position);
        holder.textView.setText(string);
        if(!MainActivity.setTabs.contains(string)){
            holder.textView.setTextColor(0xFF9E9E9E);
        } else {
            holder.textView.setTextColor(0xFF000000);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
