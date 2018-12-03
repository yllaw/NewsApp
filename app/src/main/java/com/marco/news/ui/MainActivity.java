package com.marco.news.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.marco.news.R;
import com.marco.news.model.NewsItem;
import com.marco.news.model.NewsItemViewModel;
import com.marco.news.utils.AsyncNewsQueryResponse;
import com.marco.news.utils.JSONUtils;
import com.marco.news.utils.ScheduleUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //view model
    private NewsItemViewModel mNewsItemViewModel;

    //vars
    private List<NewsItem> mNewsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);



        ScheduleUtils.scheduleRefresh(this);
        if (mNewsItemViewModel.getmAllNewsItems().getValue() != null) {
            mNewsItems = mNewsItemViewModel.getmAllNewsItems().getValue();
        }
        initRecyclerView();
    }



    //Inflate Menu (Refresh)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.d(TAG, "onCreateOptionsMenu: started");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    //Menu Item (Refresh)
    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: refreshed");
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mNewsItemViewModel.syncDatabase();
                initRecyclerView();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    //TODO: change this
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView newsRecyclerView = findViewById(R.id.news_recycler);
        final NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(mNewsItems, this);
        newsRecyclerView.setAdapter(adapter);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setHasFixedSize(true);

        mNewsItemViewModel.getmAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                adapter.setmNewsItems(newsItems);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onChanged Observer list of newsItems");
            }
        });


    }



}
