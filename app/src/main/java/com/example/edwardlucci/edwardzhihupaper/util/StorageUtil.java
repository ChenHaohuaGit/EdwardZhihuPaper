package com.example.edwardlucci.edwardzhihupaper.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

/**
 * Created by edwardlucci on 16/6/5.
 */
public class StorageUtil {

    static String getAppPath(Context context) {
        ContextWrapper c = new ContextWrapper(context.getApplicationContext());
        return c.getFilesDir().getPath();
    }
}