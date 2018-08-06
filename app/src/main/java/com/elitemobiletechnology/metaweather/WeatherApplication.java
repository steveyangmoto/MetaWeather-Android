package com.elitemobiletechnology.metaweather;

import android.app.Application;
import android.content.Context;

public class WeatherApplication extends Application{
    private static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return applicationContext;
    }

}
