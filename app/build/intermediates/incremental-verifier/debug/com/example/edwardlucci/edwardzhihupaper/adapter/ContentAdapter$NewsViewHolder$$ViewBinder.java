// Generated code from Butter Knife. Do not modify!
package com.example.edwardlucci.edwardzhihupaper.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContentAdapter$NewsViewHolder$$ViewBinder<T extends com.example.edwardlucci.edwardzhihupaper.adapter.ContentAdapter.NewsViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492970, "field 'container'");
    target.container = finder.castView(view, 2131492970, "field 'container'");
    view = finder.findRequiredView(source, 2131492989, "field 'news_imageview'");
    target.news_imageview = finder.castView(view, 2131492989, "field 'news_imageview'");
    view = finder.findRequiredView(source, 2131492990, "field 'news_title_textview'");
    target.news_title_textview = finder.castView(view, 2131492990, "field 'news_title_textview'");
  }

  @Override public void unbind(T target) {
    target.container = null;
    target.news_imageview = null;
    target.news_title_textview = null;
  }
}
