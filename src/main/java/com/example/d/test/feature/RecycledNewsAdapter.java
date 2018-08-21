package com.example.d.test.feature;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RecycledNewsAdapter extends Adapter<RecycledNewsAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder{
        View newsView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view){
            super(view);
            newsView=view;
            textView=view.findViewById(R.id.textView);
            imageView=view.findViewById(R.id.imageView);
        }
    }
    private List<NewsItem> newsItemList;
    private Activity activity;

    public RecycledNewsAdapter(Activity activity, List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickView) {
                int position=holder.getAdapterPosition();
//                NewsItem newsItem=newsItemList.get(position);
//                Toast.makeText(clickView.getContext(),newsItem.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(activity, NewsView.class);
                intent.putExtra("NewsPosition", position);
                activity.startActivity(intent);
            }
        });
        return holder;
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
