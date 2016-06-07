// Generated code from Butter Knife. Do not modify!
package com.example.edwardlucci.edwardzhihupaper.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CommentActivity$$ViewBinder<T extends com.example.edwardlucci.edwardzhihupaper.ui.CommentActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493004, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131493004, "field 'recyclerView'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
  }
}
