package com.marco.news.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "news_item")
public class NewsItem {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    String mAuthor;
    String mTitle;
    String mDescription;
    String mUrl;
    String mUrlToImage;
    @NonNull
    @ColumnInfo(name = "published")
    String mPublishedAt;

    @Ignore
    public NewsItem(String mAuthor, String mTitle, String mDescription, String mUrl, String mUrlToImage, String mPublishedAt) {
        this.mAuthor = mAuthor;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mUrl = mUrl;
        this.mUrlToImage = mUrlToImage;
        setmPublishedAt(mPublishedAt);
    }

    public NewsItem(int id, String mAuthor, String mTitle, String mDescription, String mUrl, String mUrlToImage, String mPublishedAt) {
        this.mAuthor = mAuthor;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mUrl = mUrl;
        this.mUrlToImage = mUrlToImage;
        setmPublishedAt(mPublishedAt);
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(@NonNull String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(@NonNull String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmUrlToImage() {
        return mUrlToImage;
    }

    public void setmUrlToImage(@NonNull String mUrlToImage) {
        this.mUrlToImage = mUrlToImage;
    }

    public String getmPublishedAt() {
        return mPublishedAt;
    }

    public void setmPublishedAt(@NonNull String mPublishedAt) { this.mPublishedAt = mPublishedAt.substring(0, 10); }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(@NonNull String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
