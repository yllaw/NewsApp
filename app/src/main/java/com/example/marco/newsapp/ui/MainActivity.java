package com.example.marco.newsapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.marco.newsapp.R;
import com.example.marco.newsapp.model.NewsItem;
import com.example.marco.newsapp.utils.AsyncNewsQueryResponse;
import com.example.marco.newsapp.utils.JSONUtils;
import com.example.marco.newsapp.utils.NewsQueryTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncNewsQueryResponse {

    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<NewsItem> mNewsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pull news data
        new NewsQueryTask(this).execute();
        //inflate recyclerview
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

    //Menu Item (Refresh) -> Async NewsApi call
    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: refreshed");
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new NewsQueryTask(this).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    //Async Response
    @Override
    public void onNewsQueryFinish(String jsonResult) {
        Log.d(TAG, "onNewsQueryFinish: returned json result");
        this.mNewsItems = JSONUtils.parseNews(jsonResult);
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView newsRecyclerView = findViewById(R.id.news_recycler);
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(mNewsItems, this);
        newsRecyclerView.setAdapter(adapter);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
