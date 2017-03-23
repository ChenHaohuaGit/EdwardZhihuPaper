package com.example.theme;

import android.content.Context;

import com.example.core.AsyncLoader;
import com.example.core.RxUtil;
import com.example.core.bean.Themes;

/**
 * Created by edward on 8/6/2016.
 */
public class ThemesLoader extends AsyncLoader<Themes> {

    public ThemesLoader(Context context) {
        super(context);
    }

    @Override
    public Themes loadInBackground() {

        return (Themes) ZhihuThemesService
                .getInstance()
                .getThemes()
                .compose(RxUtil.fromIOtoMainThread())
                .toBlocking()
                .single();
    }
}
