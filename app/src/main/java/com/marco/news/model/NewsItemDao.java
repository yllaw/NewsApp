package com.marco.news.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsItemDao {

    @Insert
    void insert(List<NewsItem> items);

    @Delete
    void delete(NewsItem newsItem);

    @Query("DELETE FROM news_item")
    void deleteAll();

    @Query("SELECT * FROM news_item")
    LiveData<List<NewsItem>> getAllNewsItems();

}
