package com.example.d.test.feature;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class RecycledNewsAdapter extends Adapter<RecycledNewsAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view){
            super(view);
            textView=view.findViewById(R.id.textView);
            imageView=view.findViewById(R.id.imageView);
        }
    }
    private List<NewsItem> newsItemList;

    public RecycledNewsAdapter(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsItem newsItem=newsItemList.get(position);
        holder.imageView.setImageResource(newsItem.getImageId());
        holder.textView.setText(newsItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }
}
