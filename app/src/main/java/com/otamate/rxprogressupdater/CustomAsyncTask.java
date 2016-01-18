package com.otamate.rxprogressupdater;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.lang.ref.WeakReference;

public class CustomAsyncTask extends AsyncTask<Void, Integer, Void> {
    private static final String TAG = CustomAsyncTask.class.getSimpleName();
    private WeakReference<MainActivity> mActivity;
    private boolean mCompleted = false;

    public void setActivity(MainActivity activity) {
        mActivity = new WeakReference<> (activity);
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 1; i < MainActivity.MAX_PROGRESS + 1; i++) {
            SystemClock.sleep(MainActivity.EMIT_DELAY_MS);

            publishProgress(i);

            Log.d(TAG, "count: " + i);
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        mActivity.get().mProgressBar.setProgress((progress[0]));
        mActivity.get().mTextView.setText("Progress " + progress[0]);
    }

    protected void onPreExecute() {
        mActivity.get().mTextView.setText("Starting Async Task...");
        mCompleted = false;
    }

    protected void onPostExecute(Void result) {
        mCompleted = true;
        mActivity.get().setBusy(false);
    }
}
