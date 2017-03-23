package com.example.theme;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.core.base.BaseActivity;

/**
 * Created by chenhaohua on 23/3/2017.
 */

public class ThemesActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public int getLayout() {
        return R.layout.activity_empty;
    }
}
