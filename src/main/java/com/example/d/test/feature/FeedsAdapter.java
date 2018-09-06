package com.example.d.test.feature;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

class FeedsAdapter extends BaseAdapter<NewsItem> {
    Context context;

    public FeedsAdapter(List<NewsItem> newsItems,  Context context) {
        super(newsItems);
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_recycler, parent, false);
//        return new ViewHolder(view);

        View view= LayoutInflater.from(context).inflate(R.layout.custum_row_news_item,parent,false);
        final MyViewHolder holder=new MyViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickView) {
                int position = holder.getAdapterPosition();
//                    holder.Title.setTextColor(0x66CCFF);
                newsItems.get(position).setDescription("123");
                Intent intent = new Intent(context, NewsView.class);
                intent.putExtra("Title", newsItems.get(position).getTitle());
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

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.setData(newsItems.get(position));
//    }
    @Override
    public boolean areItemsTheSame(NewsItem oldItem, NewsItem newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
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
