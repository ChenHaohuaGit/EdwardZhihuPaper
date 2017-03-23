package com.example.edwardlucci.edwardzhihupaper.data.network;

import com.example.core.api.RetrofitInstance;

/**
 * Created by chenhaohua on 23/3/2017.
 */

public class ZhihuService {
    private static final ZhihuApi ourInstance = RetrofitInstance.getInstance().create(ZhihuApi.class);

    public static ZhihuApi getInstance() {
        return ourInstance;
    }

    private ZhihuService() {
    }
}
