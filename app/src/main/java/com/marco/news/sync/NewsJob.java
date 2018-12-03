package com.marco.news.sync;

import android.os.AsyncTask;

import com.marco.news.model.NewsItemViewModel;
import com.marco.news.utils.NotificationUtils;
//firebase
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NewsJob extends JobService {
    static AsyncTask mBackgroundTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        final NewsItemViewModel newsItemViewModel = new NewsItemViewModel(getApplication());
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                NotificationUtils.notifyUser(getApplicationContext());
                newsItemViewModel.syncDatabase();
                return null;
            }
            @Override
            protected void onPreExecute() {}
        };
        mBackgroundTask.execute();
        return true;
    }
    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}