// Generated code from Butter Knife. Do not modify!
package com.example.edwardlucci.edwardzhihupaper.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CommentAdapter$CommentViewHolder$$ViewBinder<T extends com.example.edwardlucci.edwardzhihupaper.adapter.CommentAdapter.CommentViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492971, "field 'avatarPicImageview'");
    target.avatarPicImageview = finder.castView(view, 2131492971, "field 'avatarPicImageview'");
    view = finder.findRequiredView(source, 2131492972, "field 'commentTextview'");
    target.commentTextview = finder.castView(view, 2131492972, "field 'commentTextview'");
    view = finder.findRequiredView(source, 2131492970, "field 'container'");
    target.container = finder.castView(view, 2131492970, "field 'container'");
  }

  @Override public void unbind(T target) {
    target.avatarPicImageview = null;
    target.commentTextview = null;
    target.container = null;
  }
}
