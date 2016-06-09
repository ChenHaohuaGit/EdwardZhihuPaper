// Generated code from Butter Knife. Do not modify!
package com.example.edwardlucci.edwardzhihupaper.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TopicActivity$$ViewBinder<T extends com.example.edwardlucci.edwardzhihupaper.ui.TopicActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493008, "field 'viewPager'");
    target.viewPager = finder.castView(view, 2131493008, "field 'viewPager'");
  }

  @Override public void unbind(T target) {
    target.viewPager = null;
  }
}
