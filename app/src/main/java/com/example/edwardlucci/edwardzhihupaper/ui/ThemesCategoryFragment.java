package com.example.edwardlucci.edwardzhihupaper.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.core.base.BaseFragment;
import com.example.core.bean.Other;
import com.example.core.bean.Themes;
import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.adapter.ThemesRecyclerViewAdapter;
import com.example.edwardlucci.edwardzhihupaper.data.network.ThemesLoader;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by edward on 7/6/2016.
 */
public class ThemesCategoryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Themes>{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ThemesRecyclerViewAdapter themesRecyclerViewAdapter;

    @Override
    protected void init() {
        getActivity().getLoaderManager().initLoader(0,null,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getLayout() {
        return R.layout.recyclerview_layout;
    }

    @Override
    public Loader<Themes> onCreateLoader(int id, Bundle args) {
        return new ThemesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Themes> loader, Themes data) {
        themesRecyclerViewAdapter = new ThemesRecyclerViewAdapter(getActivity(), (ArrayList<Other>) data.getOthers());
        recyclerView.setAdapter(themesRecyclerViewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Themes> loader) {

    }
}
