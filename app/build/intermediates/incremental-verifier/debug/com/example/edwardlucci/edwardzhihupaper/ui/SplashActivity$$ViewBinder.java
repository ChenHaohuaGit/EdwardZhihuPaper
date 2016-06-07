// Generated code from Butter Knife. Do not modify!
package com.example.edwardlucci.edwardzhihupaper.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SplashActivity$$ViewBinder<T extends com.example.edwardlucci.edwardzhihupaper.ui.SplashActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493005, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131493005, "field 'swipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131493004, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131493004, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131492987, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131492987, "field 'toolbar'");
  }

  @Override public void unbind(T target) {
    target.swipeRefreshLayout = null;
    target.recyclerView = null;
    target.toolbar = null;
  }
}
