package com.example.edwardlucci.edwardzhihupaper.listener;

import com.example.edwardlucci.edwardzhihupaper.bean.Story;

import java.util.ArrayList;

/**
 * Created by edwardlucci on 16/5/9.
 */
public interface OnGetLatestDataListener {
    void onGetLatestStories(ArrayList<Story> stories);
}
