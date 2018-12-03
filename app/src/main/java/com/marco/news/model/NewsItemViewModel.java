package com.marco.news.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemRepository mNewsItemRepository;

    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemViewModel(@NonNull Application application){
        super(application);
        mNewsItemRepository = new NewsItemRepository(application);
        mAllNewsItems = mNewsItemRepository.getmAllNewsItems();
    }

    public LiveData<List<NewsItem>> getmAllNewsItems() {
        return mAllNewsItems;
    }

    public void syncDatabase(){
        mNewsItemRepository.syncDatabase();
    }

    public void insert(List<NewsItem> newsItems) {
        mNewsItemRepository.insert(newsItems);
    }


    public void delete(NewsItem newsItem) {
        mNewsItemRepository.delete(newsItem);
    }
}
