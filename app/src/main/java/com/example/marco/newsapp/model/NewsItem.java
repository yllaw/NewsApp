package com.example.marco.newsapp.model;

public class NewsItem {
    String mAuthor;
    String mTitle;
    String mDescription;
    String mUrl;
    String mUrlToImage;
    String mPublishedAt;

    public NewsItem(String mAuthor, String mTitle, String mDescription, String mUrl, String mUrlToImage, String mPublishedAt) {
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

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmUrlToImage() {
        return mUrlToImage;
    }

    public void setmUrlToImage(String mUrlToImage) {
        this.mUrlToImage = mUrlToImage;
    }

    public String getmPublishedAt() {
        return mPublishedAt;
    }

    public void setmPublishedAt(String mPublishedAt) { this.mPublishedAt = mPublishedAt.substring(0, 10); }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
}
