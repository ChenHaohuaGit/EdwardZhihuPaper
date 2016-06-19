package com.example.edwardlucci.edwardzhihupaper.network;

import com.example.edwardlucci.edwardzhihupaper.base.MyApp;
import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edwardlucci on 16/4/23.
 */
public class ZhihuService {

    private static final String base_url = "http://news-at.zhihu.com/api/4/";

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Story.class, new JsonDeserializer<Story>() {
                @Override
                public Story deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    JsonObject object = json.getAsJsonObject();
                    if (object.get("images").isJsonArray()){
                        JsonArray jsonArray = (JsonArray) object.get("images");
                        jsonArray.get(0).getAsString();
                    }
                    return null;
                }
            })
            .create();


    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(base_url)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkClient.getInstance())
            .build();

    private ZhihuService() {
    }

    public static ZhihuApi getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Deprecated
    public static ZhihuApi createZhihuService() {
        return retrofit.create(ZhihuApi.class);
    }

    private static class SingletonHolder {
        static final ZhihuApi INSTANCE = retrofit.create(ZhihuApi.class);
    }
}
