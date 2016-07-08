package com.example.edwardlucci.edwardzhihupaper.base;

import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by edward on 16/6/26.
 */
public class DailyStoriesDeserializer implements JsonDeserializer<DailyStories> {
    @Override
    public DailyStories deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = (JsonObject) json;

        DailyStories dailyStories = new DailyStories();

        dailyStories.setDate(jsonObject.get("date").getAsString());

        List<Story> stories = context.deserialize(((JsonObject) json).get("stories"), new TypeToken<List<Story>>() {
        }.getType());

        RealmList<Story> storyRealmList = new RealmList<>();

        storyRealmList.addAll(stories);

        dailyStories.setStories(storyRealmList);

        return dailyStories;
    }
}
