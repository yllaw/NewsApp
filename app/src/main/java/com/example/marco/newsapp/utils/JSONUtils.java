package com.example.marco.newsapp.utils;

import com.example.marco.newsapp.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    public static ArrayList<NewsItem> parseNews (String jsonString) {
        ArrayList<NewsItem> newsList = null;
        try {
            JSONObject mainJSONObject = new JSONObject(jsonString);
            newsList = parseNews(mainJSONObject);

        } catch (JSONException e) {
            e.printStackTrace();

        }

        return newsList;
    }



        public static ArrayList<NewsItem> parseNews (JSONObject jObject) {
            ArrayList<NewsItem> newsList = new ArrayList<>();
            try {
            JSONArray articles = jObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                newsList.add(new NewsItem(article.getString("author"),
                        article.getString("title"),
                        article.getString("description"),
                        article.getString("url"),
                        article.getString("urlToImage"),
                        article.getString("publishedAt")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
