package com.example.edwardlucci.edwardzhihupaper.data;

import com.example.edwardlucci.edwardzhihupaper.data.network.ZhihuApi;

/**
 * Created by edward on 16/8/9.
 */
public class DataManager {
    private ZhihuApi zhihuApi;
    private MemoryCache memoryCache;

    public DataManager(ZhihuApi zhihuApi, MemoryCache memoryCache) {
        this.zhihuApi = zhihuApi;
        this.memoryCache = memoryCache;
    }

    public ZhihuApi getZhihuApi() {
        return zhihuApi;
    }

    public void setZhihuApi(ZhihuApi zhihuApi) {
        this.zhihuApi = zhihuApi;
    }

    public MemoryCache getMemoryCache() {
        return memoryCache;
    }

    public void setMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }
}
