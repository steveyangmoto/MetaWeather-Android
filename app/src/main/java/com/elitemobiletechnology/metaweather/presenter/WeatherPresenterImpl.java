package com.elitemobiletechnology.metaweather.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.elitemobiletechnology.metaweather.MwConstants;
import com.elitemobiletechnology.metaweather.model.DailyWeather;
import com.elitemobiletechnology.metaweather.model.WeatherApi;
import com.elitemobiletechnology.metaweather.model.MetaWeatherApiImpl;
import com.elitemobiletechnology.metaweather.view.WeatherView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WeatherPresenterImpl implements WeatherPresenter{
    private static final String TAG = "WeatherPresenterImpl";
    private WeatherView weatherView;
    private WeatherApi metaWeatherApi;
    private Handler weatherApiHandler;
    private WeakReference<Activity> activityWeakReference;

    public WeatherPresenterImpl(Activity activity,WeatherView weatherView){
        this.activityWeakReference = new WeakReference<>(activity);
        this.weatherView = weatherView;
        this.metaWeatherApi = new MetaWeatherApiImpl();
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        weatherApiHandler = new Handler(looper);
    }
    @Override
    public void onActivityCreate(Intent intent) {
        final String locationId = intent.getStringExtra(MwConstants.LOCATION_ID);
        if(locationId!=null){
            weatherApiHandler.post(new Runnable() {
                @Override
                public void run() {
                    final DailyWeather[] weatherList = metaWeatherApi.getFiveDayForcast(locationId);
                    if(activityWeakReference.get()!=null){
                        activityWeakReference.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(weatherView!=null) {
                                    if (weatherList != null) {
                                        List<DailyWeather> fiveDayWeatherList = new ArrayList<>();
                                        for(int i=0;i<weatherList.length && i < 5;i++){
                                            fiveDayWeatherList.add(weatherList[i]);
                                        }
                                        weatherView.onWeatherUpdate(fiveDayWeatherList);
                                    } else {
                                        weatherView.onWeatherUpdate(new ArrayList<DailyWeather>());
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        weatherView = null;
        weatherApiHandler.removeCallbacksAndMessages(null);
    }
}
