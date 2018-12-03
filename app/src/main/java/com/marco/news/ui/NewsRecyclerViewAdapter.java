package com.marco.news.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marco.news.R;
import com.marco.news.model.NewsItem;
import com.marco.news.model.NewsItemViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsRecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "NewsRecyclerViewAdapter";

    private List<NewsItem> mNewsItems;
    private Context mContext;

    public NewsRecyclerViewAdapter(List<NewsItem> mArticles, Context mContext) {
        this.mNewsItems = mArticles;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_recycler_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        ViewHolder holder = (ViewHolder) viewHolder;

        Glide.with(mContext)
                .asBitmap()
                .load(mNewsItems.get(position).getmUrlToImage())
                .into(holder.image);

        holder.title.setText(mNewsItems.get(position).getmTitle());
        holder.description.setText(mNewsItems.get(position).getmDescription());
        holder.date.setText(mNewsItems.get(position).getmPublishedAt());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mNewsItems.get(position).getmUrl());
                Uri webPage = Uri.parse(mNewsItems.get(position).getmUrl());
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, webPage));


            }
        });
    }



    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public CircleImageView image;
        public TextView title;
        public TextView description;
        public TextView date;
        public RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.article_image);
            title = itemView.findViewById(R.id.article_title);
            description = itemView.findViewById(R.id.article_description);
            date = itemView.findViewById(R.id.article_date);
            parentLayout = itemView.findViewById(R.id.article_parent_layout);

        }
    }

    void setNewsItems(List<NewsItem> newsItems){
        mNewsItems = newsItems;
        notifyDataSetChanged();
    }

    public List<NewsItem> getmNewsItems(){
        return mNewsItems;
    }

    public void setmNewsItems(List<NewsItem> mNewsItems){
        this.mNewsItems = mNewsItems;
    }

}
