package com.example.edwardlucci.edwardzhihupaper;

import com.example.edwardlucci.edwardzhihupaper.data.network.DataComponent;

/**
 * Created by edward on 2016/10/23.
 */

public class ComponentHolder {

    private static DataComponent sDataComponent;

    public static void setDataComponent(DataComponent dataComponent){
        sDataComponent = dataComponent;
    }

    public static DataComponent getDataComponent(){
        return sDataComponent;
    }
}
