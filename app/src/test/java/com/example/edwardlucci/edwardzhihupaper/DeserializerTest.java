package com.example.edwardlucci.edwardzhihupaper;

import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.data.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.data.network.ZhihuService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by edward on 16/6/26.
 */
public class DeserializerTest {

    @BeforeClass
    static public void setup(){}

    @Test
    public void testStoryDeserializer(){
        ZhihuApi zhihuApi = ZhihuService.getInstance();
        DailyStories dailyStories = zhihuApi.getLatestStories().toBlocking().single();

        System.out.println(dailyStories.toString());

        Assert.assertNotNull(dailyStories.getStories());

        List<Story> stories = dailyStories.getStories();

    }
}
