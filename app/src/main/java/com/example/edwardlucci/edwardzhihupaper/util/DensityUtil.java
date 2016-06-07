package com.example.edwardlucci.edwardzhihupaper.util;

import android.content.res.Resources;

/**
 * Created by edwardlucci on 16/5/8.
 */
public class DensityUtil {

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
