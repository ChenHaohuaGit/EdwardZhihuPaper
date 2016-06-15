package com.example.edwardlucci.edwardzhihupaper;

import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by edward on 16/6/15.
 */
public class LoaderTest {



    @Test
    public void checkLongCommentLoader(){
        Assert.assertEquals(ZhihuService.getInstance().getLongComment(4232852).toBlocking().single().getComments().size(),14);
    }
}
