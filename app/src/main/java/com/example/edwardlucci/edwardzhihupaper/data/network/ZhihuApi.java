package com.example.edwardlucci.edwardzhihupaper.data.network;

import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.SplashResponse;
import com.example.edwardlucci.edwardzhihupaper.bean.StoryDetail;
import com.example.edwardlucci.edwardzhihupaper.bean.Theme;
import com.example.edwardlucci.edwardzhihupaper.bean.Themes;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by edwardlucci on 16/4/23.
 */
public interface ZhihuApi {

    int PAST_STORIES_LOADER_ID = 0;

    @GET("start-image/{width}*{height}")
    Observable<SplashResponse> getSplashImg(@Path("width") int width, @Path("height") int height);

    @GET("news/latest")
    Observable<DailyStories> getLatestStories();

    @GET("news/{id}")
    Observable<StoryDetail> getStoryDetail(@Path("id") int id);

    @GET("story/{id}/long-comments")
    Observable<CommentResponse> getLongComment(@Path("id") int id);

    @GET("story/{id}/short-comments")
    Observable<CommentResponse> getShortComment(@Path("id") int id);

    @GET("themes")
    Observable<Themes> getThemes();

    @GET("news/before/{date}")
    Observable<DailyStories> getPastStories(@Path("date") String date);

    @GET("theme/{id}")
    Observable<Theme> getThemeStories(@Path("id") int id);
}
