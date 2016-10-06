package com.example.edwardlucci.edwardzhihupaper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by edward on 2016/10/6.
 */

public class IconTextView extends LinearLayout {

//    @Bind(R.id.iconTextView_text)
    TextView textView;

//    @Bind(R.id.iconTextView_image)
    ImageView imageView;

    String text;
    Drawable resId;

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.icon_text_view_layout, this);

//        ButterKnife.bind(this,view);

        imageView = (ImageView) view.findViewById(R.id.iconTextView_image);
        textView = (TextView) view.findViewById(R.id.iconTextView_text);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconTextView);

        text = ta.getString(R.styleable.IconTextView_text);
        resId = ta.getDrawable(R.styleable.IconTextView_src);

        ta.recycle();

        init();
    }

    private void init() {
        textView.setText(text);

        imageView.setImageDrawable(resId);
    }
}
