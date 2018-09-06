package com.example.d.test.feature;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

class FeedsAdapter extends MyBaseAdapter<NewsItem> {
    Context context;

    public FeedsAdapter(List<NewsItem> newsItems,  Context context) {
        super(newsItems);
        this.context = context;
    }


    @Override
    public FeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_recycler, parent, false);
//        return new ViewHolder(view);

        View view= LayoutInflater.from(context).inflate(R.layout.custum_row_news_item,parent,false);
        Log.d(TAG, "onCreateViewHolder: "+view);
        final FeedsViewHolder holder=new FeedsViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: "+holder);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickView) {
                int position = holder.getAdapterPosition();
//                    holder.Title.setTextColor(0x66CCFF);
                holder.Title.setTextColor(0xFF000000);
                holder.isRead = true;
                NewsItem currentNewsItem = newsItems.get(position);
                currentNewsItem.setRead();
                MainActivity.feedsAdapter.notifyDiff();
                Intent intent = new Intent(context, NewsView.class);
                intent.putExtra("Title", currentNewsItem.getTitle());
                context.startActivity(intent);
            }
        });
        return holder;

    }

    private static final String TAG = "FeedsAdapter";
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder0, int position) {
//        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        FeedsViewHolder holder=(FeedsViewHolder)holder0;
        NewsItem current=newsItems.get(position);
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getChannel() + " " + current.getPubdate());
        holder.isRead=current.isRead();
        holder.isLiked=current.isLiked();
        if(holder.isRead){
            holder.Title.setTextColor(0xFF000000);
        } else {
            holder.Title.setTextColor(0xFFFFFFFF);
        }
        holder.Title.setText(current.getTitle());
        if(current.getPics().size() > 0)
            Picasso.with(context).load(current.getPics().get(0)).into(holder.Thumbnail);
        else
            Picasso.with(context).load("http://rss.people.com.cn/img/2014peoplelogo/rss_logo.gif").into(holder.Thumbnail);

    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.setData(newsItems.get(position));
//    }
    @Override
    public boolean areItemsTheSame(NewsItem oldItem, NewsItem newItem) {
        return (oldItem.getTitle().equals(newItem.getTitle()) && oldItem.isLiked() == newItem.isLiked());
    }
    @Override
    public boolean areContentsTheSame(NewsItem oldItem, NewsItem newItem) {
        return oldItem.getPics().equals(newItem.getPics());
    }
    //TODO:what's contents?
//    class ViewHolder extends RecyclerView.ViewHolder {
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//        public void setData(NewsItem newsItem) {
//            TextView textView = (TextView) this.itemView.findViewById(R.id.text);
//            textView.setText(newsItem.getTitle());
//        }
//    }


}
