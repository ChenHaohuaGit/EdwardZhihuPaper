package com.example.theme;

import com.example.core.bean.Themes;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by chenhaohua on 23/3/2017.
 */

public interface ThemesApi {

    @GET("themes")
    Observable<Themes> getThemes();
}
