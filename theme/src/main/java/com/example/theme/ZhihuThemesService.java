package com.example.theme;

import com.example.core.api.RetrofitInstance;

/**
 * Created by chenhaohua on 23/3/2017.
 */

public class ZhihuThemesService {
    private static final ThemesApi ourInstance = RetrofitInstance.getInstance().create(ThemesApi.class);

    public static ThemesApi getInstance() {
        return ourInstance;
    }

    private ZhihuThemesService() {
    }
}
