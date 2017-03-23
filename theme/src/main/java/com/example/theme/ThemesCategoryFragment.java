package com.example.theme;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.core.base.BaseFragment;
import com.example.core.bean.Other;
import com.example.core.bean.Themes;

import java.util.ArrayList;

public class ThemesCategoryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Themes>{

    RecyclerView recyclerView;

    ThemesRecyclerViewAdapter themesRecyclerViewAdapter;

    @Override
    protected void init() {
        getActivity().getLoaderManager().initLoader(0,null,this);
        recyclerView = (RecyclerView) layoutView.findViewById(R.id.recyclerView);
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
