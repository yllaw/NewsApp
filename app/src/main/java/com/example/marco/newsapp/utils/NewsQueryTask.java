package com.example.marco.newsapp.utils;

import android.os.AsyncTask;

import com.example.marco.newsapp.utils.AsyncNewsQueryResponse;
import com.example.marco.newsapp.utils.NetworkUtils;

public class NewsQueryTask extends AsyncTask<String, Void, String> {

    public AsyncNewsQueryResponse delegate;

    public NewsQueryTask(AsyncNewsQueryResponse delegate){
        this.delegate = delegate;
    }


    public NewsQueryTask() {
    }

    @Override
    protected String doInBackground(String... params) {
        String newsApiResponse = null;
        try {
            newsApiResponse = NetworkUtils.getResponseFromHttpURL(NetworkUtils.buildUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsApiResponse;
    }

    @Override
    protected void onPostExecute(String jsonResult){
        delegate.onNewsQueryFinish(jsonResult);
    }
}



