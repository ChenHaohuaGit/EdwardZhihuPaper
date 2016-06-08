package com.example.edwardlucci.edwardzhihupaper.network;

import android.content.Context;

import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.bean.Themes;

/**
 * Created by edward on 8/6/2016.
 */
public class ThemesLoader extends AsyncLoader<Themes>{
    public ThemesLoader(Context context) {
        super(context);
    }

    @Override
    public Themes loadInBackground() {
        return null;
    }
}
