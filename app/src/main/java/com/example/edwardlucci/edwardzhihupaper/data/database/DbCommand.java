package com.example.edwardlucci.edwardzhihupaper.data.database;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by edwardlucci on 16/5/24.
 */
public abstract class DbCommand<T> {
    private static ExecutorService sDbEngine = Executors.newSingleThreadExecutor();

    private final static Handler sUIHandler = new Handler(Looper.getMainLooper());

    public final void execute(){
        sDbEngine.execute(() -> postResult(doInBackground()));
    }

    private void postResult(final T result){
        sUIHandler.post(() -> onPostExecute(result));
    }

    protected abstract T doInBackground();

    protected void onPostExecute(T result){}
}
