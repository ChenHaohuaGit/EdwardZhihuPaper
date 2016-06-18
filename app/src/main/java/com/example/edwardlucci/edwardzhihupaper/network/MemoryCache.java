package com.example.edwardlucci.edwardzhihupaper.network;

import android.support.v4.util.LruCache;

import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;

/**
 * Created by edward on 16/6/18.
 */
public class MemoryCache {
    private static MemoryCache ourInstance = new MemoryCache();

    public static MemoryCache getInstance() {
        return ourInstance;
    }

    private LruCache<String, DailyStories> dailyStoriesLruCache;

    public static final int SIZE = 4 * 1024 * 1024;

    private MemoryCache() {
        dailyStoriesLruCache = new LruCache<>(SIZE);
    }

    public void put(String key, DailyStories object) {
        synchronized (dailyStoriesLruCache) {
            if (dailyStoriesLruCache.get(key) == null) {
                dailyStoriesLruCache.put(key, object);
            }
        }
    }

    private DailyStories get(String key) {
        synchronized (dailyStoriesLruCache) {
            if (dailyStoriesLruCache.get(key) == null) {
                return dailyStoriesLruCache.get(key);
            } else {
                return null;
            }
        }
    }
}
