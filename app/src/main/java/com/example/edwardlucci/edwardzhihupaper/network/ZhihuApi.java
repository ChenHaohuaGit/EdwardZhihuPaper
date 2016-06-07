package com.example.edwardlucci.edwardzhihupaper.network;

import com.example.edwardlucci.edwardzhihupaper.bean.LatestStories;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.bean.SplashResponse;
import com.example.edwardlucci.edwardzhihupaper.bean.StoryDetail;
import com.example.edwardlucci.edwardzhihupaper.bean.Themes;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by edwardlucci on 16/4/23.
 */
public interface ZhihuApi {
    @GET("start-image/{width}*{height}")
    Observable<SplashResponse> getSplashImg(@Path("width")int width, @Path("height")int height);

    @GET("news/latest")
    Observable<LatestStories> getLatestStories();

    @GET("news/{id}")
    Observable<StoryDetail> getStoryDetail(@Path("id")int id);

    @GET("story/{id}/long-comments")
    Observable<CommentResponse> getLongComment(@Path("id")int id);

    @GET("story/{id}/short-comments")
    Observable<CommentResponse> getShortComment(@Path("id")int id);

    @GET("themes")
    Observable<Themes> getThemes();

    @GET("news/before/{date}")
    Observable<LatestStories> getPastStories(@Path("date")String date);
}
