package com.example.edwardlucci.edwardzhihupaper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by edwardlucci on 16/4/24.
 */
public class FadedBorderImageView extends ImageView{

    public FadedBorderImageView(Context context) {
        super(context);
    }

    public FadedBorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadedBorderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float FadeStrength = 1.0f;


    @Override
    protected float getBottomFadingEdgeStrength() {
        return FadeStrength;
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return FadeStrength;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return FadeStrength;
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return FadeStrength;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = width;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
