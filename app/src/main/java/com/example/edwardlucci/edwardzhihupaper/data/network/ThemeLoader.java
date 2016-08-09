package com.example.edwardlucci.edwardzhihupaper.data.network;

import android.content.Context;

import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;

import java.util.List;

import rx.Observable;

/**
 * Created by edwardlucci on 16/6/9.
 */
@Deprecated
public class ThemeLoader extends AsyncLoader<List<Story>>{

    int id;

    public ThemeLoader(Context context,int id) {
        super(context);
        this.id = id;
    }

    @Override
    public List<Story> loadInBackground() {
        return ZhihuService.getInstance()
                .getThemeStories(id)
                .flatMap(theme -> Observable.from(theme.getStories()))
                .toList()
                .toBlocking()
                .first();
    }
}
