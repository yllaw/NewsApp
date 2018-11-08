package com.example.marco.newsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marco.newsapp.R;
import com.example.marco.newsapp.model.NewsItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsRecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "NewsRecyclerViewAdapter";

    private ArrayList<NewsItem> mArticles = new ArrayList<>();
    private Context mContext;

    public NewsRecyclerViewAdapter(ArrayList<NewsItem> mArticles, Context mContext) {
        this.mArticles = mArticles;
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
                .load(mArticles.get(position).getmUrlToImage())
                .into(holder.image);

        holder.title.setText("Title: " + mArticles.get(position).getmTitle());
        holder.description.setText(mArticles.get(position).getmDescription());
        holder.date.setText(mArticles.get(position).getmPublishedAt());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mArticles.get(position).getmUrl());
                Uri webPage = Uri.parse(mArticles.get(position).getmUrl());
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, webPage));


            }
        });
    }



    @Override
    public int getItemCount() {
        return mArticles.size();
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
}
