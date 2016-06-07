package com.example.edwardlucci.edwardzhihupaper.ui;

import android.support.v7.widget.RecyclerView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by edward on 7/6/2016.
 */
public class ThemesCategoryFragment extends BaseFragment{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void init() {

    }

    @Override
    protected int getLayout() {
        return R.layout.recyclerview_layout;
    }
}
