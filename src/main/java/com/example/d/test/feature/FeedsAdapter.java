
package com.example.d.test.feature;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rishabh on 26-02-2016.
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {
    List<NewsItem> newsItems;
    Context context;

    public FeedsAdapter(List<NewsItem> newsItems,  Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    private static final String TAG = "FeedsAdapter";
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custum_row_news_item,parent,false);
        final MyViewHolder holder=new MyViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickView) {
                    int position = holder.getAdapterPosition();
                    Intent intent = new Intent(context, NewsView.class);
                    intent.putExtra("Url", newsItems.get(position).getLink());
                    context.startActivity(intent);
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        NewsItem current=newsItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getChannel() + " " + current.getPubdate());
        if(current.getPics().size() > 0)
            Picasso.with(context).load(current.getPics().get(0)).into(holder.Thumbnail);
        else
            Picasso.with(context).load("http://rss.people.com.cn/img/2014peoplelogo/rss_logo.gif").into(holder.Thumbnail);

    }



    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Description,Date;
        ImageView Thumbnail;
        CardView cardView;
        View newsView;
        public MyViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
            Title= (TextView) itemView.findViewById(R.id.title_text);
            Description= (TextView) itemView.findViewById(R.id.description_text);
            Date= (TextView) itemView.findViewById(R.id.date_text);
            Thumbnail= (ImageView) itemView.findViewById(R.id.thumb_img);
            cardView= (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}
