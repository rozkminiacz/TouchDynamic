package com.example.michalik.touchdynamic.utils;

import android.content.Context;

import com.example.michalik.touchdynamic.App;
import com.example.michalik.touchdynamic.objects.MeasureSettings;

/**
 * Created by michalik on 04.11.16
 */

public class SharedPrefHelper {
    public static MeasureSettings getCommonSettings(){
        return null;
    }
    public static void setDefaultMeasureSettings(MeasureSettings measureSettings, Context context){

    }
    public static void setUserId(){
        Context context = getContext();
    }

    private static Context getContext() {
        return App.getInstance().getApplicationContext();
    }
}
