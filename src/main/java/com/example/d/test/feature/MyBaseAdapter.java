package com.example.d.test.feature;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    protected final List<T> temp; // 用于保存修改之前的数据源的副本
    protected final List<T> newsItems; // 数据源
    public MyBaseAdapter(List<T> newsItems) {
        this.newsItems = newsItems;
        temp = new ArrayList<>(newsItems);
    }
    protected abstract boolean areItemsTheSame(T oldItem, T newItem);
    protected abstract boolean areContentsTheSame(T oldItem, T newItem);
    @Override
    public int getItemCount() {
        return newsItems.size();
    }
    public void notifyDiff() {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return temp.size();
            }
            @Override
            public int getNewListSize() {
                return newsItems.size();
            }
            // 判断是否是同一个 item
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return MyBaseAdapter.this.areItemsTheSame(temp.get(oldItemPosition), newsItems.get(newItemPosition));
            }
            // 如果是同一个 item 判断内容是否相同
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return MyBaseAdapter.this.areContentsTheSame(temp.get(oldItemPosition), newsItems.get(newItemPosition));
            }
        });
        diffResult.dispatchUpdatesTo(this);
        // 通知刷新了之后，要更新副本数据到最新
        temp.clear();
        temp.addAll(newsItems);
    }
}
