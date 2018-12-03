package com.marco.news.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.marco.news.utils.AsyncNewsQueryResponse;
import com.marco.news.utils.JSONUtils;
import com.marco.news.utils.NetworkUtils;
import com.marco.news.utils.NewsQueryTask;

import java.io.IOException;
import java.util.List;

public class NewsItemRepository {
    private static final String TAG = "NewsItemRepository";


    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemRepository(Application application) {
        NewsItemDatabase db = NewsItemDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mAllNewsItems = mNewsItemDao.getAllNewsItems();
    }

    public void insert(List<NewsItem> newsItems) {
        new insertAsyncTask(mNewsItemDao).execute(newsItems);
    }

    public void delete(NewsItem newsItem) {
        new deleteAsyncTask(mNewsItemDao).execute(newsItem);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mNewsItemDao).execute();
    }

    public void syncDatabase() {
        new syncDatabaseAsyncTask(mNewsItemDao).execute();
    }

    public LiveData<List<NewsItem>> getmAllNewsItems() {
        return mAllNewsItems;
    }

    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;

        insertAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<NewsItem>... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<NewsItem, Void, Void> {
        private NewsItemDao mAsyncTaskDao;

        deleteAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NewsItem... params) {
            Log.d(TAG, "deleting news item: " + params[0].getmTitle());
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;

        deleteAllAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "deleting all news items");
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class syncDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        String newsApiResponse = null;
        List<NewsItem> newsItems;
        syncDatabaseAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mAsyncTaskDao.deleteAll();
                newsApiResponse = NetworkUtils.getResponseFromHttpURL(NetworkUtils.buildUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }

            newsItems = JSONUtils.parseNews(newsApiResponse);
            mAsyncTaskDao.insert(newsItems);
            return null;
        }
    }
}
