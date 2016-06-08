package com.example.edwardlucci.edwardzhihupaper.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.base.BaseFragment;
import com.example.edwardlucci.edwardzhihupaper.bean.Other;
import com.example.edwardlucci.edwardzhihupaper.bean.Themes;
import com.example.edwardlucci.edwardzhihupaper.network.ThemesLoader;
import com.orhanobut.logger.Logger;

import butterknife.Bind;

/**
 * Created by edward on 7/6/2016.
 */
public class ThemesCategoryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Themes>{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void init() {
        getActivity().getLoaderManager().initLoader(0,null,this);
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
        for (Other other : data.getOthers()) {
            Logger.i(other.getName());
        }
    }

    @Override
    public void onLoaderReset(Loader<Themes> loader) {

    }
}
