package com.example.edwardlucci.edwardzhihupaper.base;

import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by edward on 16/6/27.
 */
public class StoryDeserializer implements JsonDeserializer<Story> {

    static Gson gson = new Gson();
    @Override
    public Story deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Story story = new Story();
        JsonObject object = json.getAsJsonObject();
        String[] images = gson.fromJson(object.get("images").getAsJsonArray(), String[].class);
        story.setImage(images[0]);
        story.setTitle(object.get("title").getAsString());
        story.setId(object.get("id").getAsInt());
        return story;
    }
}
