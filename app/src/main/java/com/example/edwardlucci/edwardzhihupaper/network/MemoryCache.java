package com.example.edwardlucci.edwardzhihupaper.network;

import android.support.v4.util.LruCache;

import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;

import rx.Observable;

/**
 * Created by edward on 16/6/18.
 */
public class MemoryCache {
    private static MemoryCache ourInstance = new MemoryCache();

    public static MemoryCache getInstance() {
        return ourInstance;
    }

    private final LruCache<String, DailyStories> dailyStoriesLruCache;
    private final LruCache<String, Story> storyLruCache;

    public static final int SIZE = 4 * 1024 * 1024;

    private MemoryCache() {
        storyLruCache = new LruCache<>(SIZE);
        dailyStoriesLruCache = new LruCache<>(SIZE/2);
    }

    public void putDailyStories(String key, DailyStories object) {
        synchronized (dailyStoriesLruCache) {
            if (dailyStoriesLruCache.get(key) == null) {
                dailyStoriesLruCache.put(key, object);
            }
        }
    }

//    public Observable<DailyStories> getDailyStories(String key) {
//        synchronized (dailyStoriesLruCache) {
//            if (dailyStoriesLruCache.get(key) != null) {
//                return Observable.just(dailyStoriesLruCache.get(key));
//            } else {
//                return null;
//            }
//        }
//    }

    public DailyStories getDailyStories(String key) {
        synchronized (dailyStoriesLruCache) {
            if (dailyStoriesLruCache.get(key) != null) {
                return dailyStoriesLruCache.get(key);
            } else {
                return null;
            }
        }
    }

    public void putStory(String key, Story object) {
        synchronized (storyLruCache) {
            if (storyLruCache.get(key) == null) {
                storyLruCache.put(key, object);
            }
        }
    }

    public Story getStory(String key) {
        synchronized (storyLruCache) {
            if (storyLruCache.get(key) != null) {
                return storyLruCache.get(key);
            } else {
                return null;
            }
        }
    }
}
