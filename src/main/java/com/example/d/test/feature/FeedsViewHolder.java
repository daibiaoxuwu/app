package com.example.d.test.feature;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedsViewHolder extends RecyclerView.ViewHolder {
    TextView Title,Description,Date;
    ImageView Thumbnail;
    CardView cardView;
    View newsView;
    boolean isRead = false;
    boolean isLiked = false;
    public FeedsViewHolder(View itemView) {
        super(itemView);
        newsView = itemView;
        Title= (TextView) itemView.findViewById(R.id.title_text);
        Description= (TextView) itemView.findViewById(R.id.description_text);
        Date= (TextView) itemView.findViewById(R.id.date_text);
        Thumbnail= (ImageView) itemView.findViewById(R.id.thumb_img);
        cardView= (CardView) itemView.findViewById(R.id.cardview);
    }
}
