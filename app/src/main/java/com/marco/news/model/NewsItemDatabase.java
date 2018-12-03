package com.marco.news.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.marco.news.utils.AsyncNewsQueryResponse;
import com.marco.news.utils.JSONUtils;
import com.marco.news.utils.NetworkUtils;
import com.marco.news.utils.NewsQueryTask;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsItemDatabase extends RoomDatabase {

    private static final String LOG_TAG = NewsItemDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATBASE_NAME = "news_database";
    private static NewsItemDatabase sIntance;

    public abstract  NewsItemDao newsItemDao();

    public static  NewsItemDatabase getDatabase(Context context){
        if (sIntance == null){
            synchronized (NewsItemDatabase.class){
            Log.d(LOG_TAG, "Creating new database instance");
                sIntance = Room.databaseBuilder(context.getApplicationContext(),
                        NewsItemDatabase.class, NewsItemDatabase.DATBASE_NAME)
                        .allowMainThreadQueries()
                        //.addCallback(roomCallback)
                        .build();
        }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        //new PopulateDbAsyncTask(sIntance).execute();
        return sIntance;
    }

    // CallBack on DB create
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(sIntance).execute();
        }
    };

    //Call API to populate DB
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> implements AsyncNewsQueryResponse {
        private NewsItemDao dao;

        private PopulateDbAsyncTask(NewsItemDatabase db) {
            dao = db.newsItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String newsApiResponse = null;
            try {
                newsApiResponse = NetworkUtils.getResponseFromHttpURL(NetworkUtils.buildUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<NewsItem> newsItemList = JSONUtils.parseNews(newsApiResponse);

            Log.d(LOG_TAG, "pre-loading news into database");
            dao.insert(newsItemList);


            return null;
        }

        @Override
        public void onNewsQueryFinish(String jsonResult) {

        }
    }



}
