// Generated code from Butter Knife. Do not modify!
package com.example.edwardlucci.edwardzhihupaper.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class StoryActivity$$ViewBinder<T extends com.example.edwardlucci.edwardzhihupaper.ui.StoryActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492973, "field 'webContainer'");
    target.webContainer = finder.castView(view, 2131492973, "field 'webContainer'");
    view = finder.findRequiredView(source, 2131492986, "field 'collapsingBgImageView'");
    target.collapsingBgImageView = finder.castView(view, 2131492986, "field 'collapsingBgImageView'");
    view = finder.findRequiredView(source, 2131492985, "field 'collapsingToolbar'");
    target.collapsingToolbar = finder.castView(view, 2131492985, "field 'collapsingToolbar'");
    view = finder.findRequiredView(source, 2131492987, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131492987, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131492988, "method 'toCommentActivity'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.toCommentActivity();
        }
      });
  }

  @Override public void unbind(T target) {
    target.webContainer = null;
    target.collapsingBgImageView = null;
    target.collapsingToolbar = null;
    target.toolbar = null;
  }
}
